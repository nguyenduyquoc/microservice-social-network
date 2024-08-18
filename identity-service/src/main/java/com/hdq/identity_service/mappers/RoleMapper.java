package com.hdq.identity_service.mappers;

import com.hdq.identity_service.core.BaseDTO;
import com.hdq.identity_service.core.BaseMapper;
import com.hdq.identity_service.dtos.PermissionDTO;
import com.hdq.identity_service.dtos.RoleDTO;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.utils.SetUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RoleMapper implements BaseMapper<RoleEntity> {

    ModelMapper modelMapper;
    PermissionMapper permissionMapper;
    @Override
    public BaseDTO toDTO(RoleEntity roleEntity) {

        RoleDTO roleDTO = modelMapper.map(roleEntity, RoleDTO.class);

        if (SetUtil.isNotEmpty(roleEntity.getPermissions())) {
            List<PermissionDTO> permissionDTOS = roleEntity.getPermissions().stream().map(
                    permissionEntity -> (PermissionDTO) permissionMapper.toDTO(permissionEntity)
            ).toList();

            roleDTO.setPermissions(permissionDTOS);
        }

        return roleDTO;
    }

    @Override
    public RoleEntity toEntity(BaseDTO dto) {
        return modelMapper.map(dto, RoleEntity.class);
    }

    @Override
    public RoleEntity merge(RoleEntity source, RoleEntity target) {
        modelMapper.map(source, target);
        return target;
    }
}
