package com.asiainfo.dao;
import com.asiainfo.mapper.GoodsMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/6/30.
 */
@SpringBootApplication
@MapperScan("com.asiainfo.mapper")
@ConfigurationProperties("testconfig")
public class MysqlTest implements CommandLineRunner{

    private GoodsMapper goodMapper;

    private String url;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Autowired
    public MysqlTest(GoodsMapper  goodMapper){
        this.goodMapper = goodMapper;
    }
    public static void main(String[] args) {
        SpringApplication.run(MysqlTest.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        System.out.print("goods=" + this.goodMapper.getGoodByid(1).getDescription());
//        this.goodMapper.deleteByid(new Goods(1));
        System.out.print("goods=11" + this.goodMapper.getGoodsCount());
          System.out.print(this.url + this.name);

    }
}
