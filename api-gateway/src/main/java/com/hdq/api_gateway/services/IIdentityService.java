package com.hdq.api_gateway.services;

import com.hdq.api_gateway.core.BaseResponse;
import reactor.core.publisher.Mono;

public interface IIdentityService {
    Mono<BaseResponse> introspect(String token);

}
