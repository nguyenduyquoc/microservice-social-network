package com.hdq.identity_service.services;

import com.hdq.identity_service.controllers.auth.LoginFormRequest;
import com.hdq.identity_service.dtos.requests.IntrospectRequest;
import com.hdq.identity_service.dtos.requests.LogoutRequest;
import com.hdq.identity_service.dtos.requests.RefreshTokenRequest;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.entities.TokenEntity;
import com.hdq.identity_service.exception.CustomException;
import com.hdq.identity_service.repositories.AccountRepositoryImpl;
import com.hdq.identity_service.repositories.TokenRepositoryImpl;
import com.hdq.identity_service.utils.SetUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    AccountRepositoryImpl accountRepository;
    TokenRepositoryImpl tokenRepository;
    MessageSource messageSource;

    @Value("${jwt.signerKey}")
    @NonFinal
    protected String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    @NonFinal
    protected long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    @NonFinal
    protected long REFRESH_DURATION;



    public String authentication(LoginFormRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var user = accountRepository
                .findByPhone(request.getPhone())
                .orElseThrow(() -> new CustomException(messageSource, "accounts.db.phone-or-password-incorrect"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new CustomException(messageSource, "accounts.db.phone-or-password-incorrect");

        return generateToken(user);
    }


    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            // verify token
            // Tức là thời gian tồn tại của token đã hết nhưng thời gian refresh thì vẫn có thể còn
            // Vậy nên nếu thời gian refresh vẫn còn thì vẫn phải lưu token đó vào database
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            TokenEntity token = TokenEntity
                    .builder()
                    .id(jit)
                    .expiry_date(expiryTime)
                    .build();
            tokenRepository.save(token);

        } catch (CustomException exception) {
            log.info("token already expired");
        }

    }

    public boolean introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (CustomException e) {
            isValid = false;
        }
        return isValid;

    }

    public String refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        TokenEntity token = TokenEntity
                .builder()
                .id(jti)
                .expiry_date(expiryTime)
                .build();
        tokenRepository.save(token);

        var phone = signedJWT.getJWTClaimsSet().getSubject();

        var user = accountRepository.findByPhone(phone).orElseThrow(() -> new CustomException(messageSource, "accounts.db.token.failed"));

        return generateToken(user);
    }


    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        // Tạo mới một đối tượng JWSVerifier
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        // Token được parse thành một đối tượng SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Xác định thời gian hết hạn của token
        Date expirationTime = (isRefresh) ?
                // refresh token : thời gian phát hành + REFRESH_DURATION
                new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                        .toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                :
                // authentication or introspect : // thời gian hết hạn
                signedJWT.getJWTClaimsSet().getExpirationTime();

        // Kiểm tra thời gian hết hạn của token
        if (expirationTime != null && expirationTime.before(new Date())) {
            throw new CustomException(messageSource, "accounts.db.token.expired");
        }

        // Xác minh token
        boolean verified = signedJWT.verify(verifier);

        if (!verified)
            throw new CustomException(messageSource, "");

        // Kiểm tra token có tồn tại trong token table chưa
        if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new CustomException(messageSource, "");

        return signedJWT;
    }

    private String generateToken(AccountEntity account) {

        // create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // create payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getPhone())
                .issuer("nguyenduyquoc.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                                Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS)
                                        .toEpochMilli()
                        )
                )
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            // sign token
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new CustomException(messageSource, "");
        }
    }

    // get user scope
    private String buildScope(AccountEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (SetUtil.isNotEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (SetUtil.isNotEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission ->
                            stringJoiner.add(permission.getName())
                    );
                }
            });
        }
        return stringJoiner.toString();
    }

}
