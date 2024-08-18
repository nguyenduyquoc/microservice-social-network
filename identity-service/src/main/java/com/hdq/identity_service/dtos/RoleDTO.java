package com.hdq.identity_service.dtos;

import com.hdq.identity_service.core.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO extends BaseDTO {

    Long id;
    String name;
    String description;
    List<PermissionDTO> permissions;

}
