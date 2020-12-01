package com.zqkj.service;

import com.zqkj.entity.ReserveEntity;
import com.zqkj.utils.R;

/**
 * 预定表
 */
public interface ReserveService extends BaseService<ReserveEntity> {

    //接收预订
    public R receive(ReserveEntity reserveEntity);
}
