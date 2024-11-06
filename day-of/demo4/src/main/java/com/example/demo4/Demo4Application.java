package com.example.demo4;

import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@SpringBootApplication
public class Demo4Application {

    @GetMapping("/hello")
    Message hodor() {
        return new Message("Hello, world");
    }

    public static void main(String[] args) {
        SpringApplication.run(Demo4Application.class, args);
    }
}

@Configuration
class MyConfig {

    @Bean
    static MyBFIAP myBFIAP() {
        return new MyBFIAP();
    }
}

class MyBFIAP implements BeanFactoryInitializationAotProcessor {

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(
            ConfigurableListableBeanFactory beanFactory) {

        for (var beanName : beanFactory.getBeanDefinitionNames()) {
            System.out.println("compiling!:" + beanName);
        }

        return (gc, bf) -> {
            bf.getMethods().add("method", builder -> {
                builder.addCode("""
                                System.out.println("Hello JFall!") ;
                        """);
            });

        };
    }
}


record Message(String message) {
}