package com.bakery.server.config;

import com.bakery.server.constant.Status;
import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.ActionEntity;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.ActionRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.bakery.server.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@Slf4j
public class JpaConfig {
    @Autowired
    private ActionRepository actionRepository;
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
    private String actionEditAction = "ACTION.ADD";
    private String actionEditActionName = "Quản lý Action";
    private String actionViewAction = "ACTION.VIEW";
    private String actionViewActionName = "Xem danh sách Action";
    private String actionEditUser = "USER.ADD";
    private String actionEditUserName = "Quản lý người dùng";
    private String actionViewUser = "USER.VIEW";
    private String actionViewUserName = "Xem danh sách người dùng";

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @PostConstruct
    public void initData() {
        log.info("(initData) start init ------");
        List<ActionEntity> actionInit = new ArrayList<>();

        Arrays.asList(Arrays.asList(actionEditAction, actionEditActionName),
                Arrays.asList(actionViewAction, actionViewActionName),
                Arrays.asList(actionEditUser, actionEditUserName),
                Arrays.asList(actionViewUser, actionViewUserName)).forEach(e -> {
            ActionEntity action = actionRepository.findByCode(e.get(0));
            if (action == null) {
                action = new ActionEntity(e.get(0), e.get(1), Status.HIDDEN);
                actionRepository.save(action);
            }
            actionInit.add(action);
        });

        RoleEntity roleAdmin = roleRepository.findByCode(roleAdminCode);
        if (roleAdmin == null) {
            roleAdmin = new RoleEntity();
            roleAdmin.setCode(roleAdminCode);
            roleAdmin.setName(roleAdminName);
            roleAdmin.setStatus(Status.HIDDEN);
            roleAdmin.setActions(actionInit);

            roleRepository.save(roleAdmin);
        }


        UserEntity user = userRepository.findByUsername(userAdminUsername);
        if (user == null) {
            user = new UserEntity();
            user.setUsername(userAdminUsername);
            user.setName(userAdminName);
            user.setPassword(userAdminPassword);
            user.setEmail(userAdminEmail);
            user.setStatus(UserStatus.ACTIVE);
            user.setRoles(Collections.singletonList(roleAdmin));
            userRepository.save(user);
        }
        log.info("(initData) end init ------");
    }
}
