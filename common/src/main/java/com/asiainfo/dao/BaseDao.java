package com.asiainfo.dao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public interface BaseDao<T,Q> {
    T getById(int id);
    List<T> getByConditionPage(Q q);
    void add(T t);
    void update(T t);
    void delete(int id);
    void deleteByIdList(List<Integer> idList);
}
