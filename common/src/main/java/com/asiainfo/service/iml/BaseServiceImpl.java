package com.asiainfo.service.iml;

import com.asiainfo.dao.BaseDao;
import com.asiainfo.service.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class BaseServiceImpl<T,Q> implements BaseService<T,Q> {

    private BaseDao<T,Q>  baseDao;


    public void setBaseDao(BaseDao<T, Q> baseDao) {
        this.baseDao = baseDao;
    }

    public T getById(int id) {
        return baseDao.getById(id);
    }

    public List<T> getByConditionPage(Q q) {
        return baseDao.getByConditionPage(q);
    }

    public void add(T m) {
        baseDao.add(m);
    }

    public void update(T m) {
        baseDao.update(m);
    }

    public void delete(int id) {
        baseDao.delete(id);
    }

    public void deleteByIdList(List<Integer> idList) {
        baseDao.deleteByIdList(idList);
    }
}
