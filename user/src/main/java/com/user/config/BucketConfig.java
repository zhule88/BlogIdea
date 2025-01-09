package com.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class BucketConfig {

    @Bean
    public String bucketName() {
        return "user";
    }
}