package com.zqkj.controller.validata;


import com.zqkj.entity.CouponsEntity;
import com.zqkj.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsValidata {

    public R save(CouponsEntity entity) {
        return null;
    }

    public R saveList(List<CouponsEntity> list) {
        return null;
    }

    public R info(CouponsEntity entity) {
        /*if(StringUtil.isEmpty(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5004,"guid为空");
        }*/
        return null;
    }

    public R update(CouponsEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).put("error", map);
        return null;
    }
    public R del(CouponsEntity entity) {
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

    public R list(CouponsEntity entity, String orderBy) {
        return null;
    }

    public R page(PageUtil<CouponsEntity> page, CouponsEntity entity) {
        return null;
    }

    public R selectList(@RequestBody CouponsEntity entity) {
        return null;
    }


    public R saveCoupons(@RequestBody CouponsEntity entity) {
        if(ObjectUtil.isAllNull(entity)){
            return R.error(Content.STATUS_CODE_5006).put("count", 0);
        }
        //校验发布数量
        if(entity.getSeleaseNumber() == null || entity.getSeleaseNumber() <= 0){
            return R.error(Content.STATUS_CODE_5006,"发布数量不能小于0");
        }
        //校验用户领取量
        if(entity.getReceiveAmount() == null || entity.getReceiveAmount() <= 0){
            return R.error(Content.STATUS_CODE_5006,"限购量不能小于0");
        }
        if(entity.getScope() == 3 || entity.getScope() == 4){
            if(entity.getOrganizationGuids().length <= 0){
                return R.error(Content.STATUS_CODE_5006,"请选择商家");
            }
        }
        return null;
    }


    public R updateCoupons(@RequestBody CouponsEntity entity) {
        if(ObjectUtil.isAllNull(entity)){
            return R.error(Content.STATUS_CODE_5006).put("count", 0);
        }
        //校验发布数量
        if(entity.getSeleaseNumber() == null || entity.getSeleaseNumber() <= 0){
            return R.error(Content.STATUS_CODE_5006,"发布数量不能小于0");
        }
        //校验用户领取量
        if(entity.getReceiveAmount() == null || entity.getReceiveAmount() <= 0){
            return R.error(Content.STATUS_CODE_5006,"限购量不能小于0");
        }
        if(entity.getScope() == 3 || entity.getScope() == 4){
            if(entity.getOrganizationGuids().length <= 0){
                return R.error(Content.STATUS_CODE_5006,"请选择商家");
            }
        }
        return null;
    }


    public R getCoupons(String guid) {
        if(StringUtil.isEmpty(guid)){
            return R.error(Content.STATUS_CODE_5006,"优惠券guid为空");
        }
        return null;
    }


    public R orgCouponsList() {
        return null;
    }


    public R listByGuids(String[] guids) {
        if(guids == null || guids.length == 0)
            return R.error(Content.STATUS_CODE_5004).put("count", 0);
        return null;
    }

    public R selectListPage(PageUtil<CouponsEntity> page, CouponsEntity entity) {
        return null;
    }
}
