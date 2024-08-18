package com.hdq.identity_service.dtos;

import com.hdq.identity_service.core.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO extends BaseDTO {

    Long id;
    String name;
    String description;
}
