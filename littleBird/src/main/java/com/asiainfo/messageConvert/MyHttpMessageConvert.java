package com.asiainfo.messageConvert;

import com.asiainfo.pojo.GoodsModel;
import com.asiainfo.pojo.GoodsQueryModel;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;


import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/7/5.
 */
public class MyHttpMessageConvert extends AbstractHttpMessageConverter<GoodsModel> {


    public MyHttpMessageConvert(){

        super(new MediaType("application", "liu", Charset.forName("UTF-8")));
       // super(MediaType.valueOf("application/liu"));
        logger.info("加载MyHttpMessageConvert");
    }


    @Override
    protected boolean supports(Class<?> aClass) {
        logger.info("TEST CLASS!!!");
        logger.info(aClass.getName());
        return GoodsModel.class.isAssignableFrom(aClass);

            }

    @Override
    protected GoodsModel readInternal(Class<? extends GoodsModel> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        System.out.print("========================");
        logger.info(httpInputMessage.getBody());

        return null;
    }

    @Override
    protected void writeInternal(GoodsModel goodsModel, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

        String out = goodsModel.toString();

        httpOutputMessage.getBody().write(out.getBytes("utf-8"));


    }

}
