package com.monaim.storageservice.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ApiBaseUrl apiBaseUrl() {
        return new ApiBaseUrl();
    }

}
