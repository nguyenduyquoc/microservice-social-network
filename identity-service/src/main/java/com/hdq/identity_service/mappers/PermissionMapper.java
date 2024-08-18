package com.hdq.identity_service.mappers;

import com.hdq.identity_service.core.BaseDTO;
import com.hdq.identity_service.core.BaseMapper;
import com.hdq.identity_service.dtos.PermissionDTO;
import com.hdq.identity_service.entities.PermissionEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class PermissionMapper implements BaseMapper<PermissionEntity> {

    ModelMapper modelMapper;
    @Override
    public BaseDTO toDTO(PermissionEntity permissionEntity) {
        return modelMapper.map(permissionEntity, PermissionDTO.class);
    }

    @Override
    public PermissionEntity toEntity(BaseDTO dto) {

        return modelMapper.map(dto, PermissionEntity.class);
    }

    @Override
    public PermissionEntity merge(PermissionEntity source, PermissionEntity target) {
        modelMapper.map(source, target);
        return target;
    }
}
