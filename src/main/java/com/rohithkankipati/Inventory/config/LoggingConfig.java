package com.rohithkankipati.Inventory.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    @Bean
    Logger logger() {
        return LogManager.getLogger(LoggingConfig.class);
    }
}
