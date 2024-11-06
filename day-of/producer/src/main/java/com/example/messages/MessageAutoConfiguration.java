package com.example.messages;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessageProperties.class)
class MessageAutoConfiguration {

    @Bean
    ApplicationRunner messageInitializer() {
        return args -> System.out.println("initializing messages!");
    }

    @Bean
    @ConditionalOnMissingBean
    Message message(MessageProperties properties) {
        return new Message(properties.message());
    }
}

@ConfigurationProperties(prefix = "messages")
record MessageProperties(String message) {
}