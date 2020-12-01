package com.zqkj.dao.mapper;

import com.zqkj.entity.TeatimeEntity;
import com.zqkj.dao.BaseDao;
import com.zqkj.entity.TeatimeGroupByEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:14:20
 */
public interface TeatimeDao extends BaseDao<TeatimeEntity> {
    //条件查询时间表
    public List<TeatimeEntity> selectList(TeatimeEntity teatimeEntity);


    //条件查询时间表
    public List<TeatimeGroupByEntity> selectDateTimeGroupBy(Map map);
}
