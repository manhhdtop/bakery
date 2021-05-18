package com.bakery.server.config;

import com.bakery.server.entity.*;
import com.bakery.server.entity.base.AuditModel;
import com.bakery.server.utils.GraphAdapterBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebSecurity implements WebMvcConfigurer {
    @Value("${file.root-path}")
    private String rootPath;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + rootPath + uploadDir + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        PropertyMap<Object, AuditModel> skipCreatedDatesMap = new PropertyMap<>() {
//            protected void configure() {
//                skip().setCreatedDate(null);
//                skip().setCreatedBy(null);
//            }
//        };
//        modelMapper.addMappings(skipCreatedDatesMap);
        return modelMapper;
    }

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        new GraphAdapterBuilder().addType(UserEntity.class).registerOn(gsonBuilder);
        new GraphAdapterBuilder().addType(CategoryEntity.class).registerOn(gsonBuilder);
        new GraphAdapterBuilder().addType(ProductEntity.class).registerOn(gsonBuilder);
        new GraphAdapterBuilder().addType(OptionTypeEntity.class).registerOn(gsonBuilder);
        new GraphAdapterBuilder().addType(ProductOptionEntity.class).registerOn(gsonBuilder);
        return gsonBuilder.create();
    }
}
