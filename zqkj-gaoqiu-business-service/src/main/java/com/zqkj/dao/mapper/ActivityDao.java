package com.zqkj.dao.mapper;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.ActivityEntity;

import java.util.List;

/**
 * 活动表
 */
public interface ActivityDao extends BaseDao<ActivityEntity> {

    //条件查询列表
    public List<ActivityEntity> selectList(ActivityEntity entity);

    //分页查询总数计算
    public int selectListPageCount(ActivityEntity record);

    //分页查询
    public List<ActivityEntity> selectListPage(ActivityEntity record);
}
