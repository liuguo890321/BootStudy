package com.asiainfo.service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public interface BaseService<T,Q> {

    T getById(int id);
    List<T> getByConditionPage(Q q);
    void add(T m);
    void update(T m);
    void delete(int id);
    void deleteByIdList(List<Integer> idList);

}
