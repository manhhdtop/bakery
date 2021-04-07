package com.bakery.server.config;

import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.base.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.bakery.server.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@Slf4j
public class JpaConfig {
    @Bean
    public AuditorAware<UserEntity> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
