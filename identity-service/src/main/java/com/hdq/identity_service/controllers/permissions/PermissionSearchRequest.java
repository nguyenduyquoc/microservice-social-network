package com.hdq.identity_service.controllers.permissions;

import com.hdq.identity_service.core.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionSearchRequest extends BaseRequest {
    String searchName;
}
