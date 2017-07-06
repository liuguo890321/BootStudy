package com.asiainfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2017/7/3.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.asiainfo.control","com.asiainfo.remote"})
public class GoodsWebApplication {
    public  static void main(String[]args){
        SpringApplication.run(GoodsWebApplication.class,args);
    }
}
