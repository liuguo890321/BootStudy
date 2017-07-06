package com.asiainfo.messageConvert;

import com.asiainfo.pojo.GoodsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;


/**
 * Created by Administrator on 2017/7/5.
 */
@Configuration
public class MessageConvConfiguration {
    Logger  logger = LoggerFactory.getLogger(MessageConvConfiguration.class);
    @Bean
    public HttpMessageConverters customConverts(){
        HttpMessageConverter<?> addintiol = new MyHttpMessageConvert();
        return new HttpMessageConverters(addintiol);
    }
}
