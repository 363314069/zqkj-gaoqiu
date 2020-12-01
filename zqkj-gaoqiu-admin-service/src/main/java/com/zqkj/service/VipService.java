package com.zqkj.service;


import com.zqkj.entity.VipEntity;
import com.zqkj.utils.R;

/**
 * 会员卡
 */
public interface VipService extends BaseService<VipEntity> {

    //绑定会员卡
    public R bindingVipCode(VipEntity vipEntity);

    //度假卡回调
    public R weeksCard(VipEntity vipEntity);
}
