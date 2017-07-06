package com.asiainfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/30.
 */
@EnableAutoConfiguration
@RestController
public class HelloWorld {
    @RequestMapping("/hello")
    public String sayHello(){
        return "Hell121o5,Wo0rld";

    }
    public static void main(String []args) {
        SpringApplication.run(HelloWorld.class,args);

    }
}
