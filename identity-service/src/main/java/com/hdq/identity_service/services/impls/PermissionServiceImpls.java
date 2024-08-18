package com.hdq.identity_service.services.impls;

import com.hdq.identity_service.core.BaseMapper;
import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.entities.PermissionEntity;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.exception.CustomException;
import com.hdq.identity_service.exception.NotFoundEntityException;
import com.hdq.identity_service.mappers.PermissionMapper;
import com.hdq.identity_service.repositories.PermissionRepositoryImpl;
import com.hdq.identity_service.repositories.RoleRepositoryImpl;
import com.hdq.identity_service.services.IPermissionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionServiceImpls implements IPermissionService {

    PermissionRepositoryImpl repository;
    RoleRepositoryImpl roleRepository;
    PermissionMapper mapper;
    MessageSource messageSource;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BaseRepository<PermissionEntity> getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<PermissionEntity> getMapper() {
        return mapper;
    }

    @Override
    public PermissionEntity create(PermissionEntity permissionCreate) {
        checkPermissionName(permissionCreate.getName());
        return repository.save(permissionCreate);
    }

    @Override
    @Transactional
    public PermissionEntity update(PermissionEntity permissionUpdate) throws NotFoundEntityException {
        entityManager.detach(permissionUpdate);
        PermissionEntity permissionRoot = getFreshPermissionEntity(permissionUpdate.getId());

        if (!permissionRoot.getName().equals(permissionUpdate.getName()))
            checkPermissionName(permissionUpdate.getName());

        return repository.save(permissionUpdate);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws NotFoundEntityException {
        PermissionEntity permissionDelete = findPermissionById(id);
        RoleEntity roleEntity = roleRepository.findFirstByPermissions_Id(id);
        if (roleEntity != null)
            throw new CustomException(messageSource, "permission.db.general.cannot-delete", permissionDelete.getName(), roleEntity.getName());
        repository.deleteById(id);
    }


    private void checkPermissionName(String name) {
        if (repository.existsByNameIgnoreCase(name))
            throw new CustomException(messageSource, "permission.db.name.existed");
    }

    private PermissionEntity findPermissionById(Long id) throws NotFoundEntityException{
        return repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("Quyền hạn", id)
        );

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    PermissionEntity getFreshPermissionEntity(Long id) throws NotFoundEntityException {
        return findPermissionById(id);
    }

}
