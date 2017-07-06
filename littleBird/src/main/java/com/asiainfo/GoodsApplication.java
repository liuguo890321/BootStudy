package com.asiainfo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2017/7/3.
 */
@SpringBootApplication
@MapperScan(value = "com.asiainfo.dao" ,annotationClass = org.springframework.stereotype.Repository.class)

public class GoodsApplication {

    public static  void main(String [] args) {
        System.setProperty("spring.profiles.active","development2");
        SpringApplication.run(GoodsApplication.class,args);
    }

}
