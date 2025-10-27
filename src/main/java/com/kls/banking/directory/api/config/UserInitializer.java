package com.kls.banking.directory.api.config;

import com.kls.banking.directory.api.dao.UserRepository;
import com.kls.banking.directory.api.entity.Role;
import com.kls.banking.directory.api.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class UserInitializer {

    @Bean
    public CommandLineRunner createInitialUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Map<String, Role> defaultUsers = Map.of(
                "admin", Role.ADMIN,
                "user", Role.USER
            );

            defaultUsers.forEach((username, role) ->
                userRepository.findByUsername(username)
                    .or(() -> createUser(username, role, userRepository, passwordEncoder))
            );
        };
    }

    private java.util.Optional<UserEntity> createUser(
        String username,
        Role role,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(username)); // password = username
        user.setRole(role);

        userRepository.save(user);
        log.info("Default user '{}' with role {} created.", username, role);

        return java.util.Optional.of(user);
    }

}
