package com.asiainfo.dao;

import com.asiainfo.service.IGoodsService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created by Administrator on 2017/7/3.
 */
@SpringBootApplication
@ComponentScan(value = "com.asiainfo")
@MapperScan(value = "com.asiainfo.dao" ,annotationClass = org.springframework.stereotype.Repository.class)
public class GoodsDaoTest implements CommandLineRunner{
    private static Logger logger = LoggerFactory.getLogger(GoodsDaoTest.class);
    @Autowired
    public GoodsDaoTest(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }
    private IGoodsService  goodsService;

    public static  void main(String[] args) {
        SpringApplication.run(GoodsDaoTest.class,args);
    }
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Begin Application");
        logger.info(goodsService.getById(17).toString());
        System.out.println(goodsService.getById(17));

    }
}
