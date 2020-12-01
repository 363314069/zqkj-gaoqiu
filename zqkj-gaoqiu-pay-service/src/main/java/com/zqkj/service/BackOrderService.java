package com.zqkj.service;

import com.zqkj.bean.BackOrderParameteBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.utils.R;

/**
 * 订单表
 */
public interface BackOrderService extends BaseService<OrderEntity> {
    //创建酒店订单
    public R addHotelOrder(BackOrderParameteBean hotelOrderParameteBean);

    //创建订场订单并支付
    public R addIntroductionOrderPay(BackOrderParameteBean ionorderOrderParameteBean);

    //商家后台酒店订单操作
    public R businessConfirm(OrderEntity orderEntity);
}
