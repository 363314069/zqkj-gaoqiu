package com.zqkj.dao.mapper;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.CommissionEntity;
import com.zqkj.utils.R;

/**
 * 佣金表
 */
public interface CommissionDao extends BaseDao<CommissionEntity> {
    //查询个人佣金总和
    public Long selectCommissionSum(CommissionEntity commissionEntity);
}
