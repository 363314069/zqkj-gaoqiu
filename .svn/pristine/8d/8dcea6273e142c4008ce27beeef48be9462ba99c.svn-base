package com.zqkj.service;

import com.zqkj.bean.HotelOrderParameteBean;
import com.zqkj.bean.OrderVipBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.entity.PlaceOrderEntity;
import com.zqkj.entity.UserEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

import java.util.List;

/**
 * 订单表
 */
public interface OrderService extends BaseService<OrderEntity> {

    //创建订单
    public R addOrder(String goodsGuid,String inviterGuid,Integer sum,Integer[] typeArr,String userJson,String remark);

    //创建酒店订单
    public R addHotelOrder(HotelOrderParameteBean hotelOrderParameteBean);

    //创建单个订场订单
    public R addIntroductionOrder(String introductionGuid,String date, String time, Integer price);

    //支付单个订场订单
    public R payIntroductionOrder(String orderGuid,String[] couponsUserGuid,Integer goldSum,Integer price);

    //创建订场订单并支付
    public R addIntroductionOrderPay(String introductionGuid,String date, String time, Integer price,String[] couponsUserGuid,String remark,Integer buySum,Integer goldSum);

    //支付
    public R jsapiPay(String orderGuid);

    //取消订单
    public R cancelOrder(Integer id,Integer state);

    //异步通知
    public String payNotify(String xmlMsg);

    //根据guid查询活动
    public PlaceOrderEntity selectOneActivity(String goodsGuid);

    //绑定卡，回调需要记录绑定那个调数据guid
    public R updateVipGuid(OrderVipBean orderVipBean);


    //查询分页场地订单
    public PageUtil<OrderEntity> selectListPage(PageUtil<OrderEntity> pageUtil, OrderEntity record);

    //查询场地订单list
    public List<OrderEntity> queryList(OrderEntity record);

    //根据guid查询用户
    public UserEntity selectOneUser(String userGuid);

    //商家確認
    public R businessConfirm(OrderEntity orderEntity);


    //用户创建提现待审核订单
    public OrderEntity cashWithdrawalOrder(Integer totalAmount,String[] commissionGuids);

    //佣金提现
    public R cashWithdrawal(String guid);


    //用户直接提现不需要审核
    public OrderEntity directlyWithdrawal(Integer totalAmount,String[] commissionGuids);

    //回调提现
    public String callbackWithdrawal(String strJson);

    //查询未支付超时订单并进行取消
    public void timeoutOrderCancel();

    //查询支付后的订单球场待处理再次进行通知球场
    public void untreatedOrderNotice();

    //拼团结束后进行处理
    public void groupBuyingBusiness();
}
