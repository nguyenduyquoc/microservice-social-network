package com.hdq.identity_service.services.impls;

import com.hdq.identity_service.common.Filter;
import com.hdq.identity_service.core.BaseEntity;
import com.hdq.identity_service.core.BaseMapper;
import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.dtos.PermissionDTO;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.entities.PermissionEntity;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.exception.CustomException;
import com.hdq.identity_service.exception.NotFoundEntityException;
import com.hdq.identity_service.mappers.PermissionMapper;
import com.hdq.identity_service.mappers.RoleMapper;
import com.hdq.identity_service.repositories.AccountRepositoryImpl;
import com.hdq.identity_service.repositories.PermissionRepositoryImpl;
import com.hdq.identity_service.repositories.RoleRepositoryImpl;
import com.hdq.identity_service.services.IRoleService;
import com.hdq.identity_service.utils.ArrayUtil;
import com.hdq.identity_service.utils.SetUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpls implements IRoleService {

    RoleRepositoryImpl repository;
    AccountRepositoryImpl accountRepository;
    PermissionRepositoryImpl permissionRepository;
    RoleMapper mapper;
    PermissionMapper permissionMapper;
    MessageSource messageSource;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BaseRepository<RoleEntity> getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<RoleEntity> getMapper() {
        return mapper;
    }

    @Override
    public RoleEntity create(RoleEntity roleInput) {
        if (repository.existsByName(roleInput.getName()))
            throw new CustomException(messageSource, "role.db.name.existed");
        return repository.save(roleInput);
    }

    @Override
    @Transactional
    public RoleEntity update(RoleEntity roleUpdate) throws NotFoundEntityException {
        entityManager.detach(roleUpdate);
        RoleEntity roleRoot = getFreshRoleEntity(roleUpdate.getId());

        if (!roleUpdate.getName().equals(roleRoot.getName())) {
            if (repository.existsByName(roleUpdate.getName()))
                throw new CustomException(messageSource, "role.db.name.existed");
        }

        return IRoleService.super.update(roleUpdate);
    }


    @Override
    public void deleteById(Long id) throws NotFoundEntityException {

        RoleEntity role = repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("Quyền truy cập", id)
        );
        AccountEntity account = accountRepository.findFirstByRoles_Id(id);
        if (account != null)
            throw new CustomException(messageSource, "role.db.general.can-not-delete", role.getName(), account.getPhone());

        repository.deleteById(id);
    }

    @Override
    public Set<RoleEntity> checkIds(List<Long> roleIds) throws NotFoundEntityException {
        List<RoleEntity> roles = this.get(List.of(Filter.queryIn("id", Arrays.asList(roleIds.toArray()))));
        List<Long> existsRoleIds = roles.stream().map(BaseEntity::getId).toList();
        List<Long> diffIds = ArrayUtil.diff(roleIds, existsRoleIds);
        if (!diffIds.isEmpty()) {
            throw new NotFoundEntityException("Quyền truy cập", diffIds.getFirst());
        }
        return new HashSet<>(roles);
    }

    @Override
    public List<PermissionDTO> getPermissionAvailable(Long roleId) throws NotFoundEntityException {

        List<PermissionEntity> allPermission = permissionRepository.findAll();
        if (ArrayUtil.isEmpty(allPermission))
            return Collections.emptyList();

        RoleEntity role = findRoleById(roleId);
        List<PermissionEntity> allPermissionAttachWithRole = role.getPermissions().stream().toList();

        List<PermissionEntity> permissionAvailable = allPermission.stream().filter(
                permissionEntity -> !allPermissionAttachWithRole.contains(permissionEntity)
        ).toList();

        return permissionAvailable.stream().map(
                permissionEntity -> (PermissionDTO) permissionMapper.toDTO(permissionEntity)
        ).toList();
    }

    @Override
    public RoleEntity attachPermissionAvailable(Long roleId, Long permissionId) throws NotFoundEntityException {
        RoleEntity role = findRoleById(roleId);
        PermissionEntity permissionAttach= permissionRepository.findById(permissionId).orElseThrow(
                () -> new NotFoundEntityException("Quyền hạn" , permissionId)
        );
        Set<PermissionEntity> permissionEntities = SetUtil.isNotEmpty(role.getPermissions()) ? role.getPermissions() : new HashSet<>();
        if (permissionEntities.contains(permissionAttach))
            throw new CustomException(messageSource, "role.db.permission.has-contained", role.getName(), permissionAttach.getName());

        permissionEntities.add(permissionAttach);
        role.setPermissions(permissionEntities);

        return repository.save(role);
    }

    @Override
    public RoleEntity detachPermissionAvailable(Long roleId, Long permissionId) throws NotFoundEntityException {
        RoleEntity role = findRoleById(roleId);
        PermissionEntity permissionEntity = permissionRepository.findById(permissionId).orElseThrow(
                () -> new NotFoundEntityException("Quyền hạn" , permissionId)
        );

        Set<PermissionEntity> permissionEntities = role.getPermissions();

        if (SetUtil.isEmpty(permissionEntities))
            throw new CustomException(messageSource, "permission.db.list-empty", role.getName());

        if (!permissionEntities.contains(permissionEntity))
            throw new CustomException(messageSource, "role.db.permission.not-contain", role.getName(), permissionEntity.getName());

        permissionEntities.remove(permissionEntity);
        role.setPermissions(permissionEntities);

        return repository.save(role);
    }

    private RoleEntity findRoleById(Long id) throws NotFoundEntityException{
        return repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("Vai trò", id)
        );

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    RoleEntity getFreshRoleEntity(Long id) throws NotFoundEntityException {
        return findRoleById(id);
    }

}
