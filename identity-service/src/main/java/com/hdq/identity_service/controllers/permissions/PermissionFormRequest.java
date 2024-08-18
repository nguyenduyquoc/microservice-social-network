package com.hdq.identity_service.controllers.permissions;

import com.hdq.identity_service.core.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PermissionFormRequest extends BaseDTO {

    @NotBlank(message = "Tên của Quyền hạn không được để trống")
    String name;

    String description;
}
