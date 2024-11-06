package com.example.virtuous_threads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class VirtuousThreadsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtuousThreadsApplication.class, args);
    }

//    Executor virtuous = Executors.newVirtualThreadPerTaskExecutor();
//
//    Executor normalExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}

// Cora Iberkleid
// @ciberkleid 

@Controller
@ResponseBody
class HelloController {

    private final RestClient http;

    HelloController(RestClient http) {
        this.http = http;
    }

    @GetMapping("/delay")
    String hello() throws Exception {
        var str = "";
        str += ("------------------------------------") + System.lineSeparator();
        str += (Thread.currentThread().toString()) + System.lineSeparator();
        var response = this.http.get().uri("https://httpbin.org/delay/5").retrieve().body(String.class);
        str+=(Thread.currentThread().toString())+System.lineSeparator();
        str+=("------------------------------------")+System.lineSeparator();
        System.out.println(str);
        return response;
    }
}