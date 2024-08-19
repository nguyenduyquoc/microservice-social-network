package com.hdq.identity_service.controllers.accounts;

import com.hdq.identity_service.core.BaseRequest;
import com.hdq.identity_service.core.BaseResponse;
import com.hdq.identity_service.core.BaseRestController;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.exception.NotFoundEntityException;
import com.hdq.identity_service.mappers.AccountMapper;
import com.hdq.identity_service.services.IAccountService;
import com.hdq.identity_service.utils.Constants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/accounts")
public class AccountController implements BaseRestController<AccountEntity> {

    IAccountService service;
    AccountMapper mapper;

    @Override
    public IAccountService getService() {
        return service;
    }

    @Override
    public AccountMapper getMapper() {
        return mapper;
    }

    // admin
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "")
    public BaseResponse index(BaseRequest request) {
        return BaseRestController.super.index(request);
    }

    // admin
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "/{id}")
    public BaseResponse show(@PathVariable("id") Long id) {
        return BaseRestController.super.show(id);
    }


    @PostMapping(path = "/sign-up-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createUser(@Valid @RequestBody RegisterFormRequest request) {
        return BaseResponse.created(service.createUser(request));
    }

    @PostMapping(value = "/sign-up-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createAdmin(@Valid @RequestBody RegisterFormRequest request){

        return BaseResponse.created(service.createAdmin(request));
    }


    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@Valid @RequestBody RegisterFormRequest request, Long id) {
        return BaseRestController.super.update(request, id);
    }


    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteById(@PathVariable("id") Long id) {
        return BaseRestController.super.deleteById(id);
    }

    @GetMapping(path = "{accountId}/roles-available")
    public BaseResponse showRolesAvailable(@PathVariable("accountId") Long accountId) {
        try {
            return BaseResponse.success(service.getRolesAvailable(accountId));
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

    @PostMapping(path = "{accountId}/attach/{roleId}")
    public BaseResponse attachRolesAvailable(@PathVariable("accountId") Long accountId, @PathVariable("roleId") Long roleId) {
        try {
            return BaseResponse.success(
                    mapper.toDTO(service.attachRolesAvailable(accountId, roleId))
            );
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

    @PostMapping(path = "{accountId}/detach/{roleId}")
    public BaseResponse detachRolesAvailable(@PathVariable("accountId") Long accountId, @PathVariable("roleId") Long roleId) {
        try {
            return BaseResponse.success(
                    mapper.toDTO(service.detachRolesAvailable(accountId, roleId))
            );
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }
}
