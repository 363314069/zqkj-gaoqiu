package com.zqkj.service;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.entity.CouponsEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

import java.util.List;

/**
 * 
 * 优惠券
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
public interface CouponsService extends BaseService<CouponsEntity> {

    //后台添加优惠券
    public int saveCoupons(CouponsEntity entity);


    //后台修改优惠券
    public int updateCoupons(CouponsEntity entity);


    //用户领取
    public R getCoupons(String guid);


    //后台查询商家可用的优惠券
    public List<CouponsEntity> selectOrgList();

    //查询分页场地订单
    public PageUtil<CouponsEntity> selectListPage(PageUtil<CouponsEntity> pageUtil, CouponsEntity record);
}
