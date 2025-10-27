package com.kls.banking.directory.api.dao;

import com.kls.banking.directory.api.entity.Role;
import com.kls.banking.directory.api.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInitiallizer {

    @Bean
    public CommandLineRunner createInitialUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                log.info("Default admin user created!");
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("user");
                admin.setPassword(passwordEncoder.encode("user"));
                admin.setRole(Role.USER);

                userRepository.save(admin);

                log.info("Default app user has been created!");
            }
        };
    }

}
