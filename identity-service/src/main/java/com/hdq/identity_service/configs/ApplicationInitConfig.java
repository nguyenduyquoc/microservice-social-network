/*
package com.hdq.identity_service.configs;

import com.hdq.identity_service.dtos.requests.ProfileFormRequest;
import com.hdq.identity_service.entities.AccountEntity;
import com.hdq.identity_service.entities.RoleEntity;
import com.hdq.identity_service.repositories.AccountRepositoryImpl;
import com.hdq.identity_service.repositories.RoleRepositoryImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    AccountRepositoryImpl accountRepository;
    RoleRepositoryImpl roleRepository;

    @Bean
    @Transactional
    ApplicationRunner applicationRunner() {
        return args -> {
            RoleEntity createdRole = roleRepository.findByName("SUPER_ADMIN");
            if (createdRole == null) {
                createdRole = RoleEntity.builder()
                        .name("SUPER_ADMIN")
                        .description("Quản trị hệ thống")
                        .build();

            }

            if (!accountRepository.existsByPhone("0962027042")) {
                var roles = new HashSet<RoleEntity>();
                roles.add(createdRole);

                AccountEntity account = AccountEntity.builder()
                        .phone("0962027042")
                        .password(passwordEncoder.encode("password"))
                        .roles(roles)
                        .build();
                account = accountRepository.save(account);

                ProfileFormRequest profile = ProfileFormRequest.builder()
                        .accountId(account.getId())
                        .phone("0962027042")
                        .firstName("Quản trị")
                        .lastName("hệ thống")
                        .email("nguyenduyquoc293@gmail.com")
                        .dob(LocalDate.parse("2001-04-29"))
                        .city("Ha Tinh")
                        .build();

                log.warn("admin user has been created with default password: password, please change it");
            }
        };
    }

}
*/

