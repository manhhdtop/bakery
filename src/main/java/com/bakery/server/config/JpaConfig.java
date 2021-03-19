package com.bakery.server.config;

import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.repository.UserRepository;
import com.bakery.server.repository.base.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.bakery.server.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@Slf4j
public class JpaConfig {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.user.username}")
    private String userAdminUsername;
    @Value("${admin.user.password}")
    private String userAdminPassword;
    @Value("${admin.user.name}")
    private String userAdminName;
    @Value("${admin.user.email}")
    private String userAdminEmail;
    @Value("${admin.role.code}")
    private String roleAdminCode;
    @Value("${admin.role.name}")
    private String roleAdminName;

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @PostConstruct
    public void initData() {
        log.info("(initData) start init ------");
        RoleEntity role = roleRepository.findByCode(roleAdminCode);
        if (role == null) {
            role = new RoleEntity();
            role.setCode(roleAdminCode);
            role.setName(roleAdminName);
            roleRepository.save(role);
        }
        UserEntity user = userRepository.findByUsername(userAdminUsername);
        if (user == null) {
            user = new UserEntity();
            user.setUsername(userAdminUsername);
            user.setName(userAdminName);
            user.setPassword(userAdminPassword);
            user.setEmail(userAdminEmail);
            user.setStatus(UserStatus.ACTIVE);
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
        }
        log.info("(initData) end init ------");
    }
}
