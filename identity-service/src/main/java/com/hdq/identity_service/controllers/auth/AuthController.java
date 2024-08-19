package com.hdq.identity_service.controllers.auth;

import com.hdq.identity_service.core.BaseResponse;
import com.hdq.identity_service.dtos.requests.*;
import com.hdq.identity_service.services.AuthenticationService;
import com.hdq.identity_service.utils.Constants;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public BaseResponse login(@Valid @RequestBody LoginFormRequest userLogin){
        var token = authenticationService.authentication(userLogin);
        return BaseResponse.success("Login successfully", token);
    }

    @PostMapping("/check-token")
    public BaseResponse checkToken(@RequestBody IntrospectRequest token) throws ParseException, JOSEException {
        var isValid = authenticationService.introspect(token);
        return BaseResponse.success("Check token successfully", isValid);
    }

    @PostMapping(value = "/log-out", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse logOut(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return BaseResponse.noContent();
    }

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        var newToken =  authenticationService.refreshToken(request);
        return BaseResponse.success("Refresh token successfully", newToken);
    }

}
