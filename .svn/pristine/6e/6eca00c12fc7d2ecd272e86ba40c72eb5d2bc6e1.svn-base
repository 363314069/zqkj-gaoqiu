package com.zqkj.controller.validata;


import com.zqkj.entity.ReservationdateEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ReservationdateValidata {
    public R save(ReservationdateEntity entity) {
        return null;
    }

    public R list(ReservationdateEntity entity, String orderBy) {
        return null;
    }

    public R info(ReservationdateEntity entity) {
        return null;
    }

    public R update(ReservationdateEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R saveList(@RequestBody List<ReservationdateEntity> list) {
        if(ObjectUtil.listObjIsNull(list)){
            return R.error(Content.STATUS_CODE_5005).put("count", 0);
        }
        return null;
    }

    public R del(ReservationdateEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {
        if(ids == null){
            return R.error(Content.STATUS_CODE_20100,"参数为空");
        }
        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R listByGuids(String[] guids){
        return null;
    }

    public R page(PageUtil<ReservationdateEntity> page, ReservationdateEntity entity) {

        return null;
    }

    public R selectReservationdateList(ReservationdateEntity reservationdateEntity) {
        return null;
    }


    /**
     * 批量修改添加
     * @return
     */
    public R selectReservationdateList(@RequestBody List<ReservationdateEntity> reservationdateList) {
        if(reservationdateList == null){
            return R.error(Content.STATUS_CODE_20100,"参数为空");
        }
        return null;
    }


    public R selectOne(@RequestBody String json) {
        if(StringUtil.isEmpty(json)){
            return R.error(Content.STATUS_CODE_20100,"参数为空");
        }
        return null;
    }

    public R updateListWeekend(@RequestBody ReservationdateEntity reservationdateEntity) {
        if(StringUtil.isEmpty(reservationdateEntity.getStartTime())){
            return R.error(Content.STATUS_CODE_5006,"起始时间为空！");
        }
        if(StringUtil.isEmpty(reservationdateEntity.getEndTime())){
            return R.error(Content.STATUS_CODE_5006,"结束时间为空！");
        }
        try {
            Date stateTime = DateUtils.parseDatetime(reservationdateEntity.getStartTime());
            Date endTime = DateUtils.parseDatetime(reservationdateEntity.getEndTime());//结束时间
            boolean dateFlag = DateUtils.isBefore(stateTime,endTime);
            if(!dateFlag){
                return R.error(Content.STATUS_CODE_5006,"结束时间不能小于起始时间！");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return R.error(Content.STATUS_CODE_5006,"时间参数异常请检查！");
        }
        return null;
    }
}
