package com.zqkj.controller.validata;


import com.zqkj.entity.IntroductionEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class IntroductionValidata{

    public R save(IntroductionEntity entity) {
        return null;
    }

    public R list(IntroductionEntity entity, String orderBy) {
        return null;
    }

    public R info(IntroductionEntity entity) {
        return null;
    }

    public R update(IntroductionEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R del(IntroductionEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R listByGuids(String[] guids){
        if(guids == null || guids.length == 0)
            return R.error(Content.STATUS_CODE_5004).put("count", 0);
        return null;
    }

    public R page(PageUtil<IntroductionEntity> page,IntroductionEntity entity) {

        return null;
    }

    public R selectIntroductionList(IntroductionEntity entity) {
        return null;
    }


    public R selectOne(@RequestBody String guid) {
        if(StringUtil.isEmpty(guid)){
            return R.error(Content.STATUS_CODE_5004,"guid为空");
        }
        return null;
    }


    public R saveIntroduction(@RequestBody IntroductionEntity entity) {
        return null;
    }



    public R updateIntroduction(@RequestBody IntroductionEntity entity) {
        return null;
    }


    public R listByReservations(String[] reservationGuids,String type, String state,String orderBy) {
        if(reservationGuids == null || reservationGuids.length <= 0){
            return R.error(Content.STATUS_CODE_5006,"球场GUID数组为空");
        }
        /*if(StringUtil.isEmpty(type)){
            return R.error(Content.STATUS_CODE_5006,"类型为空");
        }
        if(StringUtil.isEmpty(state)){
            return R.error(Content.STATUS_CODE_5006,"状态为空");
        }*/
        return null;
    }


    public R selectListPage(PageUtil<IntroductionEntity> page, IntroductionEntity entity) {
        return null;
    }

    public R normalTimeList(String reservationGuid,Integer state) {
        if(StringUtil.isEmpty(reservationGuid)){
            return R.error(Content.STATUS_CODE_5004,"球场guid为空");
        }
        return null;
    }
}
