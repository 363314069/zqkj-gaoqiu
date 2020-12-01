package com.zqkj.controller.validata;


import com.zqkj.entity.ReservationEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationValidata {

    public R save(ReservationEntity entity) {
        return null;
    }

    public R list(ReservationEntity entity, String orderBy) {
        return null;
    }

    public R info(ReservationEntity entity) {
        return null;
    }

    public R update(ReservationEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R del(ReservationEntity entity) {

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

    public R page(PageUtil<ReservationEntity> page, ReservationEntity entity) {

        return null;
    }

    public R selectOneByGuid(String guid) {
        if(StringUtil.isEmpty(guid)){
            return R.error(Content.STATUS_CODE_20000,"GUID不能为空");
        }
        return null;
    }


    public R selectReservationList(ReservationEntity reservationEntity) {
        return null;
    }


    public R selectLowestPrice(ReservationEntity reservationEntity) {
        return null;
    }
}
