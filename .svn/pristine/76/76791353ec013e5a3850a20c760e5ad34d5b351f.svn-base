package com.zqkj.service;

import com.zqkj.entity.CommissionEntity;
import com.zqkj.utils.R;

/**
 * 佣金表
 */
public interface CommissionService extends BaseService<CommissionEntity> {

    //消费后进行佣金记录（会员产品，返给邀请人）
    public R addCommission(CommissionEntity commissionEntity,Integer sum);

    //查询个人佣金,积分总和
    public R selectCommissionSum(Integer state);


    //消费后进行佣金记录（抢购分享）
    public R analysisCommission(CommissionEntity commissionEntity,Integer sum);


    //佣金提现创建修改佣金状态
    public boolean cashWithdrawal(String strJson);


    //佣金提现创建修改佣金状态
    public boolean conduct(String orderGuid);
}
