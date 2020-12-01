package com.zqkj.dao.mapper;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.IntegralEntity;
import com.zqkj.utils.R;

/**
 * 积分表
 */
public interface IntegralDao extends BaseDao<IntegralEntity> {
    //查询个人积分总和
    public Long selectIntegralSum(IntegralEntity integralEntity);
}
