package com.kls.banking.directory.api.config;

import com.kls.banking.directory.api.dao.UserRepository;
import com.kls.banking.directory.api.enums.Role;
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

    private final UserProperties userProperties;

    public UserInitializer(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @Bean
    public CommandLineRunner createInitialUsers(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {

        return args -> {
            userProperties.getUsers().forEach(defaultUser -> {
                String username = defaultUser.getUsername();

                userRepository.findByUsername(username).ifPresentOrElse(
                    existing -> log.info("User '{}' already exists.", username),
                    () -> {
                        UserEntity user = new UserEntity();

                        user.setUsername(username);
                        user.setPassword(passwordEncoder.encode(defaultUser.getPassword()));
                        user.setRole(defaultUser.getRole());

                        userRepository.save(user);

                        log.info("Default user '{}' with role {} created.", username, defaultUser.getRole());
                    }
                );
            });
        };
    }
}

