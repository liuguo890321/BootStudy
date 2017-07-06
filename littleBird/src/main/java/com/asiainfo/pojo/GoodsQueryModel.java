package com.asiainfo.pojo;

import com.asiainfo.util.pageUtil.Page;
import org.springframework.boot.logging.LoggingSystem;

/**
 * Created by lenovo on 2017/5/16.
 */
public class GoodsQueryModel extends GoodsModel {

    public Page page = new Page();


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "GoodsQueryModel{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", description='" + description + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", gmtModified=" + gmtModified +
                "page=" + page +
                '}';
    }
}
