package com.hdq.identity_service.services;

import com.hdq.identity_service.core.BaseService;
import com.hdq.identity_service.dtos.PermissionDTO;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.exception.NotFoundEntityException;

import java.util.List;
import java.util.Set;

public interface IRoleService extends BaseService<RoleEntity> {

    Set<RoleEntity> checkIds(List<Long> ids) throws NotFoundEntityException;

    List<PermissionDTO> getPermissionAvailable(Long roleId) throws NotFoundEntityException;

    RoleEntity attachPermissionAvailable(Long roleId, Long permissionId) throws NotFoundEntityException;

    RoleEntity detachPermissionAvailable(Long roleId, Long permissionId) throws NotFoundEntityException;
}
