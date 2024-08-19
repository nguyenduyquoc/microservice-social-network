package com.hdq.api_gateway.services;

import com.hdq.api_gateway.core.BaseResponse;
import com.hdq.api_gateway.dtos.requests.IntrospectRequest;
import com.hdq.api_gateway.repositories.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IdentityService {

    IdentityClient identityClient;

    public Mono<BaseResponse> introspect(String token) {
        return identityClient.introspect(
                IntrospectRequest.builder().token(token).build()
        );
    }
}
