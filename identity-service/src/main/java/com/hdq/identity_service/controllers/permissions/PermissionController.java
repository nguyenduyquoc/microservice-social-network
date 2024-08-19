package com.hdq.identity_service.controllers.permissions;

import com.hdq.identity_service.common.Filter;
import com.hdq.identity_service.core.*;
import com.hdq.identity_service.entities.PermissionEntity;
import com.hdq.identity_service.mappers.PermissionMapper;
import com.hdq.identity_service.services.IPermissionService;
import com.hdq.identity_service.utils.Constants;
import com.hdq.identity_service.utils.StringUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionController implements BaseRestController<PermissionEntity> {

    IPermissionService service;
    PermissionMapper mapper;

    @Override
    public IPermissionService getService() {
        return service;
    }

    @Override
    public PermissionMapper getMapper() {
        return mapper;
    }

    @GetMapping(path = "")
    public BaseResponse index(PermissionSearchRequest request) {
        if (StringUtil.isNotEmpty(request.getSearchName()))
            request.addFilter(Filter.queryLike("name", request.getSearchName()));
        return BaseRestController.super.index(request);
    }

    @GetMapping(path = "/{id}")
    public BaseResponse show(@PathVariable("id") Long id) {
        return BaseRestController.super.show(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse create(@Valid @RequestBody PermissionFormRequest request) {
        return BaseRestController.super.create(request);
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@Valid @RequestBody PermissionFormRequest request, Long id) {
        return BaseRestController.super.update(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteById(@PathVariable("id") Long id) {
        return BaseRestController.super.deleteById(id);
    }

}
