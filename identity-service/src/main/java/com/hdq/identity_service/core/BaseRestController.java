package com.hdq.identity_service.core;

import com.hdq.identity_service.exception.NotFoundEntityException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

public interface BaseRestController<E extends BaseEntity> {

    BaseService<E> getService();

    BaseMapper<E> getMapper();


    default BaseResponse index(BaseRequest request) {
        if (request.isPaginationRequest()) {
            request.setPage(BigDecimal.valueOf(request.getPage()));
            Page<BaseDTO> page = getService().paginate(request.getFilters(), request.getPageable()).map(
                    e -> getMapper().toDTO(e)
            );
            return BaseResponse.success(new BasePagination<>(page));
        } else {
            return BaseResponse.success(
                    getService().get(request.getFilters()).stream().map(
                            e -> getMapper().toDTO(e)).toList()
            );
        }
    }

    default BaseResponse show(Long id) {
        try {
            E e = getService().getById(id);
            return BaseResponse.success(getMapper().toDTO(e));
        } catch (NotFoundEntityException ex) {
            return BaseResponse.throwException(ex);
        } catch (Exception ex) {
            return BaseResponse.throwException(ex);
        }
    }


    default BaseResponse create(BaseDTO request) {
        E e = getMapper().toEntity(request);
        return BaseResponse.created(
                getMapper().toDTO(getService().create(e))
        );
    }


    default BaseResponse update(BaseDTO request, Long id) {
        try {
            E entity = getService().getById(id);
            entity = getMapper().merge(getMapper().toEntity(request), entity);
            entity = getService().update(entity);
            return BaseResponse.created(
                    getMapper().toDTO(entity)
            );
        } catch (NotFoundEntityException ex) {
            return BaseResponse.throwException(ex);
        }
    }

    default BaseResponse deleteById(@PathVariable(value = "id") Long id) {
        try {
            getService().deleteById(id);
            return BaseResponse.noContent();
        } catch (NotFoundEntityException ex) {
            return BaseResponse.throwException(ex);
        }
    }

}
