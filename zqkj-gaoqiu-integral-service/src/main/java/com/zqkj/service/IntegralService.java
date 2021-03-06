package com.zqkj.service;

import com.zqkj.entity.IntegralEntity;
import com.zqkj.utils.R;

/**
 * 积分表
 */
public interface IntegralService extends BaseService<IntegralEntity> {

    //查询个人积分总和
    public Long selectIntegralSum(IntegralEntity integralEntity);

    //订场返现积分接口
    public R integralRecurrence(String strJson);
}
