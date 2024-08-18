package com.hdq.identity_service.dtos;

import com.hdq.identity_service.core.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountDTO extends BaseDTO {

    Long id;
    String phone;
    List<RoleDTO> roles;
}
