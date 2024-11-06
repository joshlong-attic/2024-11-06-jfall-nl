package com.example.consumer;

import com.example.messages.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    Message myMessageIsAwesome (){
        return new Message("My Message!!") ;
    }
    
}

@Controller
@ResponseBody
class MessageController {
    
    private final Message message;

    MessageController(Message message) {
        this.message = message;
    }

    @GetMapping("/message")
    String read() {
        return this.message.message();
    }
}