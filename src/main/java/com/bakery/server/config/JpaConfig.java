package com.bakery.server.config;

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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private EntityManager entityManager;

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
    AuditorAware<UserEntity> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @PostConstruct
    @Transactional
    @Modifying
    public void initData() {
        log.info("(initData) start init ------");
        List<ActionEntity> actionInit = new ArrayList<>();
        Long createDate = new Date().getTime();

        String actionEditAction = "ACTION.ADD";
        String actionEditActionName = "Quản lý Action";
        String actionViewAction = "ACTION.VIEW";
        String actionViewActionName = "Xem danh sách Action";
        String actionEditUser = "USER.ADD";
        String actionEditUserName = "Quản lý người dùng";
        String actionViewUser = "USER.VIEW";
        String actionViewUserName = "Xem danh sách người dùng";
        RoleEntity roleAdmin = roleRepository.findByCode(roleAdminCode);
        if (roleAdmin == null) {
            String sql = "INSERT INTO `bakery`.`role` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, %d, %d, %s, %s, -99)";
            entityManager.createNativeQuery(String.format(sql, createDate, createDate, roleAdminCode, roleAdminName, roleAdminName)).executeUpdate();
            roleAdmin = roleRepository.findByCode(roleAdminCode);
        }
        RoleEntity finalRoleAdmin = roleAdmin;
        Arrays.asList(Arrays.asList(actionEditAction, actionEditActionName),
                Arrays.asList(actionViewAction, actionViewActionName),
                Arrays.asList(actionEditUser, actionEditUserName),
                Arrays.asList(actionViewUser, actionViewUserName)).forEach(e -> {
            ActionEntity action = actionRepository.findByCode(e.get(0));
            if (action == null) {
                String sql = "INSERT INTO `bakery`.`action` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, %d, %d, %s, %s, -99)";
                entityManager.createNativeQuery(String.format(sql, createDate, createDate, e.get(0), e.get(1), e.get(1))).executeUpdate();
                action = actionRepository.findByCode(e.get(0));
                sql = "INSERT INTO `bakery`.`role_action`(`role_id`,`action_id`) VALUES (%d, %d)";
                entityManager.createNativeQuery(String.format(sql, finalRoleAdmin.getId(), action.getId())).executeUpdate();
            }
            actionInit.add(action);
        });


        UserEntity user = userRepository.findByUsername(userAdminUsername);
        if (user == null) {
            String sql = "INSERT INTO `bakery`.`user` (`deleted`,`created_date`,`updated_date`,`username`,`name`,`password`,`email`,`status`) VALUES(0, %d, %d, %s, %s, 1)";
            entityManager.createNativeQuery(String.format(sql, createDate, createDate, userAdminUsername, userAdminName, userAdminPassword, userAdminEmail)).executeUpdate();
            user = userRepository.findByUsername(userAdminUsername);
            sql = "INSERT INTO `bakery`.`user_role`(`user_id`,`role_id`) VALUES (%d, %d)";
            entityManager.createNativeQuery(String.format(sql, user.getId(), roleAdmin.getId())).executeUpdate();
        }
        log.info("(initData) end init ------");
    }
}
