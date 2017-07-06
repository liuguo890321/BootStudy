package com.asiainfo.dao;

import com.asiainfo.pojo.Goods;

/**
 * Created by Administrator on 2017/7/1.
 */
public class TestGoods {
    public static void main(String[]args){
        Goods  g = new Goods();
        g.setDescription("sdf");
        System.out.print(g.getDescription());

    }
}
