package com.zqkj.dao.mapper;

import com.zqkj.entity.CouponsEntity;
import com.zqkj.dao.BaseDao;

import java.util.List;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
public interface CouponsDao extends BaseDao<CouponsEntity> {
    //分页查询总数计算
    public int selectListPageCount(CouponsEntity record);

    //分页查询
    public List<CouponsEntity> selectListPage(CouponsEntity record);
}
