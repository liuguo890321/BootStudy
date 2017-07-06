package com.asiainfo.dao;

import com.asiainfo.pojo.GoodsModel;
import com.asiainfo.pojo.GoodsQueryModel;
import org.springframework.stereotype.Repository;



/**
 * Created by Administrator on 2017/7/3.
 */
@Repository
public interface GoodsDao  extends  BaseDao<GoodsModel,GoodsQueryModel> {

}
