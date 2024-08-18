package com.hdq.identity_service.controllers.roles;

import com.hdq.identity_service.core.BaseDTO;
import lombok.Getter;

@Getter
public class RoleFormRequest extends BaseDTO {

    String name;
    String description;
}
