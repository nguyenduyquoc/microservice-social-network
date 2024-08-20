package com.hdq.identity_service.services;

import com.hdq.identity_service.controllers.accounts.RegisterFormRequest;
import com.hdq.identity_service.core.BaseService;
import com.hdq.identity_service.dtos.AccountDTO;
import com.hdq.identity_service.dtos.RoleDTO;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.exception.NotFoundEntityException;

import java.util.List;

public interface IAccountService extends BaseService<AccountEntity> {

    AccountDTO createAdmin(RegisterFormRequest request);
    AccountDTO createUser(RegisterFormRequest request);

    List<RoleDTO> getRolesAvailable(Long accountId) throws NotFoundEntityException;

    AccountEntity attachRolesAvailable(Long accountId, Long roleId) throws NotFoundEntityException;

    AccountEntity detachRolesAvailable(Long accountId, Long roleId) throws NotFoundEntityException;

    Object getMyInfo() throws NotFoundEntityException;

}
