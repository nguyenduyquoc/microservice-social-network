package com.hdq.identity_service.core;

public interface BaseMapper<E extends BaseEntity> {

    BaseDTO toDTO(E e);

    E toEntity(BaseDTO dto);

    E merge(E source,E target);
}
