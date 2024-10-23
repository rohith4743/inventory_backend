package com.rohithkankipati.Inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private ParameterStoreService parameterStoreService;

    @Bean
    public DataSource dataSource() {
        String username;
        String password;

        // If the active profile is 'prod', fetch secrets from AWS Systems Manager
        if (env.acceptsProfiles("prod") && parameterStoreService != null) {
            username = parameterStoreService.getParameter(env.getProperty("aws.ssm.postgres.username"));
            password = parameterStoreService.getParameter(env.getProperty("aws.ssm.postgres.password"));
        } else {
            // Use local credentials for development
            username = env.getProperty("spring.datasource.username");
            password = env.getProperty("spring.datasource.password");
        }

        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.url"))
                .username(username)
                .password(password)
                .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
                .build();
    }
}

