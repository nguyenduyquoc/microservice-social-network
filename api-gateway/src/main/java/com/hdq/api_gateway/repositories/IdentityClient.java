package com.hdq.api_gateway.repositories;

import com.hdq.api_gateway.core.BaseResponse;
import com.hdq.api_gateway.dtos.requests.IntrospectRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {

    @PostExchange(url = "/api/v1/auth/check-token", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<BaseResponse> introspect(@RequestBody IntrospectRequest request);

}
