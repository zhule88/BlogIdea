package com.article.config;


import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient MinioClient(){
        return MinioClient.builder().
                endpoint("http://localhost:9000/").
                credentials("minioadmin", "minioadmin").
                build();
    }
}
