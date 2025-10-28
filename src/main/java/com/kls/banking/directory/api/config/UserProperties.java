package com.kls.banking.directory.api.config;

import com.kls.banking.directory.api.enums.Role;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class UserProperties {

    private List<DefaultUser> users;

    @Data
    public static class DefaultUser {
        private String username;
        private String password;
        private Role role;
    }

}