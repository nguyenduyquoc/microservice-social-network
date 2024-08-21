package com.hdq.identity_service.services.impls;

import com.hdq.event.dto.NotificationEvent;
import com.hdq.identity_service.controllers.accounts.RegisterFormRequest;
import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.dtos.AccountDTO;
import com.hdq.identity_service.dtos.RoleDTO;
import com.hdq.identity_service.dtos.requests.ProfileFormRequest;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.exception.CustomException;
import com.hdq.identity_service.exception.NotFoundEntityException;
import com.hdq.identity_service.mappers.AccountMapper;
import com.hdq.identity_service.mappers.RoleMapper;
import com.hdq.identity_service.repositories.AccountRepositoryImpl;
import com.hdq.identity_service.repositories.RoleRepositoryImpl;
import com.hdq.identity_service.repositories.http_client.ProfileClient;
import com.hdq.identity_service.services.IAccountService;
import com.hdq.identity_service.utils.ArrayUtil;
import com.hdq.identity_service.utils.SetUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService implements IAccountService {

    AccountRepositoryImpl repository;
    RoleRepositoryImpl roleRepository;
    AccountMapper mapper;
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder;
    MessageSource messageSource;
    ModelMapper modelMapper;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${app.kafka.account-event.topic-name}")
    @NonFinal
    String topicName;

    @Override
    public BaseRepository<AccountEntity> getRepository() {
        return repository;
    }

    @Override
    public AccountMapper getMapper() {
        return mapper;
    }

    @Override
    @Transactional
    public AccountDTO createUser(RegisterFormRequest request) {

        AccountEntity userAccount = createAccount(request);

        // Find role USER, if not create one
        RoleEntity adminRole = roleRepository.findByName("USER");
        if (adminRole == null)
            adminRole = roleRepository.save(RoleEntity.builder().name("USER").build());

        userAccount.setRoles(Collections.singleton(adminRole));
        userAccount = repository.save(userAccount);

        // send api to profile service
        sendApiToProfileService(userAccount, request);

        //publish message to kafka
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .receiver(request.getEmail())
                .subject("Welcome to HDQ")
                .body("Hello, " + request.getPhone())
                .build();

        kafkaTemplate.send(topicName, notificationEvent);

        return (AccountDTO) mapper.toDTO(userAccount);

    }


    @Transactional
    public AccountDTO createAdmin(RegisterFormRequest request) {

        AccountEntity adminAccount = createAccount(request);

        List<String> roleNames = Arrays.asList("ADMIN", "USER");
        Set<RoleEntity> roles = new HashSet<>(roleRepository.findByNameIn(roleNames));

        roleNames.stream()
                .filter(roleName -> roles.stream().noneMatch(role -> role.getName().equals(roleName)))
                .forEach(roleName -> {
                    RoleEntity role = roleRepository.save(RoleEntity.builder().name(roleName).build());
                    roles.add(role);
                });

        adminAccount.setRoles(roles);
        adminAccount = repository.save(adminAccount);

        // send api to profile service
        sendApiToProfileService(adminAccount, request);
        //publish message to kafka
        kafkaTemplate.send(topicName, "Welcome our new admin " + adminAccount.getPhone());

        return (AccountDTO) mapper.toDTO(adminAccount);
    }


    @Override
    public List<RoleDTO> getRolesAvailable(Long accountId) throws NotFoundEntityException {
        List<RoleEntity> allRoles = roleRepository.findAll();
        if (ArrayUtil.isEmpty(allRoles))
            return Collections.emptyList();

        AccountEntity account = findAccountById(accountId);
        List<RoleEntity> allRolesAttachWithAccount = account.getRoles().stream().toList();

        List<RoleEntity> rolesAvailable = allRoles.stream().filter(
                role -> !allRolesAttachWithAccount.contains(role)
        ).toList();

        return rolesAvailable.stream().map(
                role -> (RoleDTO) roleMapper.toDTO(role)
        ).toList();
    }

    @Override
    public AccountEntity attachRolesAvailable(Long accountId, Long roleId) throws NotFoundEntityException {
        AccountEntity account = findAccountById(accountId);
        RoleEntity roleAttach = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundEntityException("Vai trò" , roleId)
        );
        Set<RoleEntity> roleEntities = SetUtil.isNotEmpty(account.getRoles()) ? account.getRoles() : new HashSet<>();

        if (roleEntities.contains(roleAttach))
            throw new CustomException(messageSource, "account.db.role.has-contained", account.getPhone(), roleAttach.getName());

        roleEntities.add(roleAttach);
        account.setRoles(roleEntities);

        return repository.save(account);
    }

    @Override
    public AccountEntity detachRolesAvailable(Long accountId, Long roleId) throws NotFoundEntityException {
        AccountEntity account = findAccountById(accountId);
        RoleEntity roleDetach = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundEntityException("Vai trò" , roleId)
        );

        Set<RoleEntity> roleEntities = account.getRoles();

        if (SetUtil.isEmpty(roleEntities))
            throw new CustomException(messageSource, "role.db.list-empty");

        if (!roleEntities.contains(roleDetach))
            throw new CustomException(messageSource, "account.db.role-not-contain");

        roleEntities.remove(roleDetach);
        account.setRoles(roleEntities);

        return repository.save(account);
    }

    @Override
    public Object getMyInfo() throws NotFoundEntityException {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        AccountEntity user = repository.findByPhone(name).orElseThrow(
                () -> new NotFoundEntityException("Tài khoản", name));

        return profileClient.getProfile(user.getId());
    }

    private AccountEntity createAccount(RegisterFormRequest request) {

        if(repository.existsByPhone(request.getPhone()))
            throw new CustomException(messageSource, "accounts.db.phone.existed", request.getPhone());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        AccountEntity account = mapper.toEntity(request);
        account.setPassword(hashedPassword);

        return account;
    }

    private void sendApiToProfileService(AccountEntity account, RegisterFormRequest request) {
        ProfileFormRequest profileFormRequest = modelMapper.map(request, ProfileFormRequest.class);
        profileFormRequest.setAccountId(account.getId());
        profileClient.createProfile(profileFormRequest);
        log.info("created profile successfully");
    }


    private AccountEntity findAccountById(Long id) throws NotFoundEntityException{
        return repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("Tài khoản", id)
        );

    }

}

