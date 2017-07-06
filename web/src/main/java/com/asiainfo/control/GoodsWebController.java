package com.asiainfo.control;

import com.asiainfo.pojo.GoodsModel;
import com.asiainfo.remote.Remoteable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.List;


/**
 * Created by Administrator on 2017/7/3.
 */
@Controller
@RequestMapping("/goods")
public class GoodsWebController {

    private Logger logger = LoggerFactory.getLogger(GoodsWebController.class);

    @Autowired
    private Remoteable remote;

    @Value("${remote.serverName}")
    private String serverName;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") int id){
        return "goods/index";
    }

    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(){
        return "goods/addGoods";
    }

    @RequestMapping(value = "/toList",method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsModel> toList(){
        List l = remote.getCall(serverName,List.class,"goods/listAll");
        logger.info(l.get(0).toString());
        return l;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("gm") GoodsModel gm){
        gm.setUuid(UUID.randomUUID().toString());
        Date createDate = new Date();
        if(gm.getImgPath() == null || gm.getImgPath() == ""){
            gm.setImgPath("static/img/123.jpg");
        }
        gm.setGmtCreate(createDate);
        gm.setGmtModified(createDate);
        logger.info(gm.toString());
        remote.postCall(serverName, gm, String.class, null, "goods");
        return "goods/success";
    }


}
