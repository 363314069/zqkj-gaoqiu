package com.zqkj.controller.validata;


import com.zqkj.entity.ActivitycouponsEntity;
import com.zqkj.entity.CouponsEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

import java.util.List;

public class ActivitycouponsValidata {

    public R save(ActivitycouponsEntity entity) {
        return null;
    }

    public R list(ActivitycouponsEntity entity, String orderBy) {
        return null;
    }

    public R info(ActivitycouponsEntity entity) {
        return null;
    }

    public R update(ActivitycouponsEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R del(ActivitycouponsEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R listByGuids(String[] guids){
        return null;
    }

    public R page(PageUtil<ActivitycouponsEntity> page, ActivitycouponsEntity entity) {

        return null;
    }

    public R selectCouponsList(ActivitycouponsEntity activitycouponsEntity){
        if(activitycouponsEntity == null){
            return R.error(Content.STATUS_CODE_5006,"参数为空");
        }
        return null;
    }
}
