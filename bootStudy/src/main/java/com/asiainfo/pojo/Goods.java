package com.asiainfo.pojo;


/**
 * Created by Administrator on 2017/7/1.
 */

public class Goods {
    public Goods(){

    }
    public Goods (int id) {
        this.id = id;

    }
    private int id;

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

