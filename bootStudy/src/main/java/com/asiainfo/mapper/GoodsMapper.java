package com.asiainfo.mapper;

import com.asiainfo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2017/7/1.
 */

public interface GoodsMapper {
    @Select("Select * from td_goods where id = #{id}")
    Goods getGoodByid(@Param("id") int id);

    void deleteByid(Goods goods);

    @Select("Select count(1) from td_goods")
    int getGoodsCount();
}
