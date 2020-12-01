package com.zqkj.controller;

import com.zqkj.entity.CouponsEntity;
import com.zqkj.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.ActivitycouponsEntity;
import com.zqkj.service.ActivitycouponsService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 
 * 抢购优惠券关联表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-21 11:26:44
 */
@Controller
@RequestMapping("/business/activitycoupons")
@Api(value = "", tags = { "business/activitycoupons " })
public class ActivitycouponsController extends BaseController<ActivitycouponsService, ActivitycouponsEntity> {

    /**
     * 根据产品优惠券关联表查询优惠券信息
     * @param activitycouponsEntity
     * @return
     */
    @RequestMapping(value = "/couponsList", method = {RequestMethod.POST})
    @ApiOperation(value = "根据产品优惠券关联表查询优惠券信息", notes = "参数为json对像")
    @ResponseBody
    public R selectCouponsList(ActivitycouponsEntity activitycouponsEntity){
        List<CouponsEntity> list = service.selectCouponsList(activitycouponsEntity);
        return R.ok().putData(list);
    }
}
