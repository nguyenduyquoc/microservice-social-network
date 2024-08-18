package com.hdq.identity_service.mappers;

import com.hdq.identity_service.core.BaseDTO;
import com.hdq.identity_service.core.BaseMapper;
import com.hdq.identity_service.dtos.AccountDTO;
import com.hdq.identity_service.dtos.RoleDTO;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.utils.SetUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountMapper implements BaseMapper<AccountEntity> {

    ModelMapper modelMapper;
    RoleMapper roleMapper;

    @Override
    public BaseDTO toDTO(AccountEntity accountEntity) {
        AccountDTO accountDTO =  modelMapper.map(accountEntity, AccountDTO.class);
        if (SetUtil.isNotEmpty(accountEntity.getRoles())) {
            List<RoleDTO> roleDTOS = accountEntity.getRoles().stream().map(
                    roleEntity -> (RoleDTO) roleMapper.toDTO(roleEntity)
            ).toList();

            accountDTO.setRoles(roleDTOS);
        }

        return accountDTO;
    }

    @Override
    public AccountEntity toEntity(BaseDTO dto) {
        return modelMapper.map(dto, AccountEntity.class);
    }

    @Override
    public AccountEntity merge(AccountEntity source, AccountEntity target) {
        modelMapper.map(source, target);
        return target;
    }
}
