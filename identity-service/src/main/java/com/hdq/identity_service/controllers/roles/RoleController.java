package com.hdq.identity_service.controllers.roles;

import com.hdq.identity_service.core.*;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.exception.NotFoundEntityException;
import com.hdq.identity_service.mappers.RoleMapper;
import com.hdq.identity_service.services.IRoleService;
import com.hdq.identity_service.utils.Constants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/roles")
public class RoleController implements BaseRestController<RoleEntity> {

    IRoleService service;
    RoleMapper mapper;

    @Override
    public IRoleService getService() {
        return service;
    }

    @Override
    public RoleMapper getMapper() {
        return mapper;
    }

    @GetMapping(path = "")
    public BaseResponse index(BaseRequest request) {
        return BaseRestController.super.index(request);
    }

    @GetMapping(path = "/{id}")
    public BaseResponse show(@PathVariable("id") Long id) {
        return BaseRestController.super.show(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse create(@Valid @RequestBody RoleFormRequest request) {
        return BaseRestController.super.create(request);
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@Valid @RequestBody RoleFormRequest request, Long id) {
        return BaseRestController.super.update(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteById(@PathVariable("id") Long id) {
        return BaseRestController.super.deleteById(id);
    }


    @GetMapping(path = "{roleId}/permission-available")
    public BaseResponse showPermissionAvailable(@PathVariable("roleId") Long roleId) {
        try {
            return BaseResponse.success(service.getPermissionAvailable(roleId));
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

    @PostMapping(path = "{roleId}/attach/{permissionId}")
    public BaseResponse attachPermissionAvailable(@PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId) {
        try {
            return BaseResponse.success(
                    mapper.toDTO(service.attachPermissionAvailable(roleId, permissionId))
            );
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

    @PostMapping(path = "{roleId}/detach/{permissionId}")
    public BaseResponse detachPermissionAvailable(@PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId) {
        try {
            return BaseResponse.success(
                    mapper.toDTO(service.detachPermissionAvailable(roleId, permissionId))
            );
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

}
