package com.zqkj.service.impl;

import com.zqkj.dao.mapper.OrderExtendDao;
import com.zqkj.entity.OrderExtendEntity;
import com.zqkj.service.OrderExtendService;
import org.springframework.stereotype.Service;


/**
 * 
 * 订单扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
@Service("orderExtendService")
public class OrderExtendServiceImpl extends BaseServiceImpl<OrderExtendDao, OrderExtendEntity> implements OrderExtendService {

    @Override
    public OrderExtendEntity selectOneByOrderGuid(String orderGuid) {
        OrderExtendEntity orderExtendEntity = new OrderExtendEntity();
        orderExtendEntity.setOrderGUID(orderGuid);
        return selectOne(orderExtendEntity);
    }
}
