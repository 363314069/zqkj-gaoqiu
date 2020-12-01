package com.zqkj.service;

import com.zqkj.entity.GoldEntity;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-22 11:21:50
 */
public interface GoldService extends BaseService<GoldEntity> {

    //产品购买后返现金币接口
    public Integer productCashBack(GoldEntity goldEntity);
}
