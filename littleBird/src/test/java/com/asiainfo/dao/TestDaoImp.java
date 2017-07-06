package com.asiainfo.dao;
import java.util.ArrayList;
import java.util.List;

import com.asiainfo.service.GoodsServiceImpl;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

/**
 * Created by Administrator on 2017/7/3.
 */
public class TestDaoImp{
    public static void main (String[]args)throws Exception{
        List l = new ArrayList();
        List l2 =new ArrayList();
        l.add("a");
        l.forEach(e ->l2.add(e));
        System.out.print(l2);
        SOAPMessage  m = MessageFactory.newInstance().createMessage();

    }
}
