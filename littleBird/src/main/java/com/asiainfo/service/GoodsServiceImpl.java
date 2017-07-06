package com.asiainfo.service;

import com.asiainfo.dao.BaseDao;
import com.asiainfo.dao.GoodsDao;
import com.asiainfo.pojo.GoodsModel;
import com.asiainfo.pojo.GoodsQueryModel;
import com.asiainfo.service.iml.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/3.
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<GoodsModel,GoodsQueryModel> implements IGoodsService {

    private GoodsDao goodsDao;

//    public GoodsServiceImpl(GoodsDao  goodsDao) {
//        super(goodsDao);
//       // this.goodsDao = goodsDao;
//    }
    @Autowired
    public void setGoodsDao(GoodsDao goodsDao) {
        //this.goodsDao = goodsDao;
        super.setBaseDao(goodsDao);
    }

}
