package com.zqkj.controller.validata;


import com.zqkj.bean.CouponsExchangeBean;
import com.zqkj.entity.CouponsuserEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsuserValidata {

    public R save(CouponsuserEntity entity) {
        return null;
    }

    public R saveList(List<CouponsuserEntity> list) {
        return null;
    }

    public R info(CouponsuserEntity entity) {
        /*if(StringUtil.isEmpty(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5004,"guid为空");
        }*/
        return null;
    }

    public R update(CouponsuserEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).put("error", map);
        return null;
    }
    public R del(CouponsuserEntity entity) {
        if(entity.getId() == null && StringUtils.isAllBlank(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R delByIds(Long[] ids) {
        if(ids == null && ids.length == 0){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R delByGuids(String[] guids) {
        if(guids == null && guids.length == 0){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R list(CouponsuserEntity entity, String orderBy) {
        return null;
    }

    public R page(PageUtil<CouponsuserEntity> page, CouponsuserEntity entity) {
        return null;
    }

    public R selectList(@RequestBody CouponsuserEntity entity) {
        return null;
    }


    public R payOrderBindingCoupons(@RequestBody String jsonStr) {
        if(StringUtil.isEmpty(jsonStr)){
            return R.error(Content.STATUS_CODE_5006,"参数为空");
        }
        return null;
    }

    public R selectCouponsUserAct(String actGuid,String userGuid) {
        if(StringUtil.isEmpty(actGuid)){
            return R.error(Content.STATUS_CODE_5006,"商品GUID为空");
        }
        return null;
    }

    public R selectUserCouponsList(String actGuid,String userGuid) {
        if(StringUtil.isEmpty(actGuid)){
            return R.error(Content.STATUS_CODE_5006,"商品GUID为空");
        }
        return null;
    }

    public R cancelOrderCoupons(@RequestBody String orderGuid) {
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单guid为空");
        }
        return null;
    }

    public R activityCallback(String userGuid, String activityGuid, String orderGuid, Integer sum) {
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空");
        }
        if(StringUtil.isEmpty(activityGuid)){
            return R.error(Content.STATUS_CODE_5006,"产品GUID为空");
        }
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空");
        }
        if(sum == null || sum < 1){
            return R.error(Content.STATUS_CODE_5006,"购买数量为空");
        }
        return null;
    }

    public R total(CouponsuserEntity entity) {
        if(StringUtil.isEmpty(entity.getUserGuid())){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空");
        }
        return null;
    }

    public R selectUserCoupons(Integer state,String vipCardGuid,String userGuid) {
        if(state == null){
            return R.error(Content.STATUS_CODE_5006,"查询类型为空");
        }
        return null;
    }

    public R membersBin(@RequestBody String jsonStr){
        if(StringUtil.isEmpty(jsonStr)){
            return R.error(Content.STATUS_CODE_5006,"参数为空！");
        }
        cn.hutool.json.JSONObject jsonObject = new cn.hutool.json.JSONObject(jsonStr);
        String userGuid = jsonObject.getStr("userGuid");
        Integer hotelType = jsonObject.getInt("hotelType");
        if(hotelType == null){
            return R.error(Content.STATUS_CODE_5006,"酒店类型为空");
        }
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空！");
        }
        return null;
    }


    public R addUserCoupons(CouponsuserEntity couponsuserEntity) {
        if(couponsuserEntity == null){
            return R.error(Content.STATUS_CODE_5006,"参数为空！");
        }
        if(StringUtil.isEmpty(couponsuserEntity.getUserGuid())){
            return R.error(Content.STATUS_CODE_5006,"用户GUID参数为空！");
        }
        if(StringUtil.isEmpty(couponsuserEntity.getUserGuid())){
            return R.error(Content.STATUS_CODE_5006,"用户GUID参数为空！");
        }
        if(StringUtil.isEmpty(couponsuserEntity.getCouponsGuid())){
            return R.error(Content.STATUS_CODE_5006,"优惠券GUID参数为空！");
        }
        if(couponsuserEntity.getSum() == null || couponsuserEntity.getSum() < 0){
            return R.error(Content.STATUS_CODE_5006,"数量必须大于0");
        }
        return null;
    }


    public R userCouponseExchange(CouponsExchangeBean couponsExchangeBean) {
        if(StringUtil.isEmpty(couponsExchangeBean.getUserGuid())){
            return R.error(Content.STATUS_CODE_5006,"用户GUID参数为空！");
        }
        if(couponsExchangeBean.getAddCouponsSum() == null && couponsExchangeBean.getReduceCouponsSum() == null){
            return R.error(Content.STATUS_CODE_5006,"至少填入一个数量，增加或者减少！");
        }
        return null;
    }
}
