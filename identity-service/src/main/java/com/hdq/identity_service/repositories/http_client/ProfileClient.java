package com.hdq.identity_service.repositories.http_client;

import com.hdq.identity_service.configs.AuthenticationRequestInterceptor;
import com.hdq.identity_service.core.BaseResponse;
import com.hdq.identity_service.dtos.requests.ProfileFormRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}", configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {

    @PostMapping(value = "/api/v1/internal/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile(@RequestBody ProfileFormRequest request);

    @GetMapping(value = "/api/v1/internal/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getProfile(@PathVariable(name = "accountId") Long id);

}
