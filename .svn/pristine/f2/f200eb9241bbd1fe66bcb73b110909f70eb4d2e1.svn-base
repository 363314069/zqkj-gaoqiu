package com.zqkj.dao.mapper;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.OrderEntity;
import com.zqkj.utils.PageUtil;

import java.util.List;

/**
 * 订单表
 */
public interface OrderDao extends BaseDao<OrderEntity> {
    public List<OrderEntity> selectListPage(OrderEntity record);

    //查询未支付超时订单
    public List<OrderEntity> selectTimeoutOrder();

    //查询订场支付后的订单球场未处理的数据
    public List<OrderEntity> selectOrderFailsNotify();
}
