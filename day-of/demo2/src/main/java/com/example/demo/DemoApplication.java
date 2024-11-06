package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(JdbcClient jdbcClient) {
        return args -> {
            var all = jdbcClient
                    .sql("select * from customer")
                    .query((rs, rowNum) -> new Customer(rs.getInt("id"), rs.getString("name")))
                    .list();
            all.forEach(customer -> System.out.println("found: " + customer));
        };

    }

    // look mom , no Lombok!!
    record Customer(int id, String name) {
    }

}
