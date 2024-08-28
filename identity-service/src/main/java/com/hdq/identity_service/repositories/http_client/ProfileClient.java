package com.hdq.identity_service.repositories.http_client;

import com.hdq.identity_service.configs.AuthenticationRequestInterceptor;
import com.hdq.identity_service.dtos.requests.ProfileFormRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}", configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {

    @PostMapping(value = "/api/internal/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile(@RequestBody ProfileFormRequest request);

}
