package com.asiainfo.controller;

import com.asiainfo.messageConvert.MyHttpMessageConvert;
import com.asiainfo.pojo.GoodsModel;
import com.asiainfo.pojo.GoodsQueryModel;
import com.asiainfo.service.IGoodsService;
import com.asiainfo.util.pageUtil.Page;
import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by Administrator on 2017/7/3.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private IGoodsService iGoodsService;

//    @Autowired
//    private MyHttpMessageConvert messageConvert;

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GoodsModel getById(@PathVariable int id) {
        GoodsModel gm = iGoodsService.getById(id);
        logger.info(gm.toString());
        return gm;
    }
    @RequestMapping(value = "/convert/{id}", method = RequestMethod.GET,produces = "application/liu")
    public GoodsModel getconvertById(@PathVariable int id) {
        GoodsModel gm = iGoodsService.getById(id);
        logger.info(gm.toString());
        return gm;
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)

    public String add(@RequestBody GoodsModel gm) throws UnsupportedEncodingException
    {
//        logger.info("getgmName=" +new String(gm.getName().getBytes(),"utf-8"));
//        logger.info("getgmName=" +new String(gm.getName().getBytes(),"gbk"));
        logger.info(gm.getName());
        iGoodsService.add(gm);
        return "success";
    }
    @RequestMapping(value = "listAll",method = RequestMethod.GET)
    public List<GoodsModel> list(){
        Page<GoodsModel> page = new Page<GoodsModel>();
        page.setPageSize(5);
        page.setCurrentPage(1);
        GoodsQueryModel  queryModel = new GoodsQueryModel();
        queryModel.setPage(page);
 //       System.out.print(messageConvert);
        logger.info(iGoodsService.getByConditionPage(queryModel).toString());
        return iGoodsService.getByConditionPage(queryModel);
    }
}
