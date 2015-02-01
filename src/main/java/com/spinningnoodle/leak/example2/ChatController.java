package com.spinningnoodle.leak.example2;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Freddy on 1/31/2015.
 */

@Controller
@EnableAutoConfiguration
public class ChatController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "hello world!;";

    }

    public static void main(String[] args) {
        SpringApplication.run(ChatController.class, args);

    }

}
