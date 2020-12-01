package com.zqkj.service;

import com.zqkj.bean.CouponsExchangeBean;
import com.zqkj.entity.CouponsEntity;
import com.zqkj.entity.CouponsuserEntity;
import com.zqkj.utils.R;

import java.util.List;

/**
 * 
 * 优惠券用户关联
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
public interface CouponsuserService extends BaseService<CouponsuserEntity> {

    //支付订单绑定优惠券
    public R payOrderBindingCoupons(String jsonStr);

    //查询用户在某个商品中可以使用的优惠券
    public List<CouponsuserEntity> selectCouponsUserAct(String actGuid,String userGuid);

    //阳江护照功能临时使用查询用户优惠券接口
    public List<CouponsuserEntity> selectUserCouponsList(String actGuid,String userGuid);

    //取消订单，取消该订单绑定的优惠券
    public R cancelOrderCoupons(String orderGuid);


    //抢购优惠券回调
    public Integer activityCallback(String userGuid, String activityGuid,String orderGuid, Integer sum);

    //查询用户优惠券
    public List<CouponsEntity> selectUserCoupons(Integer state,String vipCardGuid,String userGuid);

    //用户验证后进行绑定优惠券
    public Integer membersBin(String jsonStr);

    //添加用户的优惠券
    public Integer addUserCoupons(CouponsuserEntity entity);

    //添加用户的优惠券
    public Integer userCouponseExchange(CouponsExchangeBean couponsExchangeBean);
}
