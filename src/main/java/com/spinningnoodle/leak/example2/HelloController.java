package com.spinningnoodle.leak.example2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by Freddy on 2/1/2015.
 */
@RestController
@EnableAutoConfiguration
public class HelloController {
    @RequestMapping("/hello/")
    public String index(String someAttr) {

        return "Ok now what? +" + someAttr;


    }

    public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }
}
