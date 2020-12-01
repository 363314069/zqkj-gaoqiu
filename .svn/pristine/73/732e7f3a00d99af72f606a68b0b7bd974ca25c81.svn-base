package com.zqkj.controller.validata;


import com.zqkj.entity.ReserveEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class ReserveValidata extends BaseValidata<ReserveEntity>  {

    public R info(ReserveEntity entity) {
        return null;
    }

    public R list(ReserveEntity entity, String orderBy) {
        return null;
    }

    public R page(PageUtil<ReserveEntity> page, ReserveEntity entity) {
        return null;
    }

    public R reserveSave(@RequestBody ReserveEntity entity){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtil.isEmpty(entity.getStartTime())){
            map.put("StartTime","预定开始时间为空");
        }
        if(StringUtil.isEmpty(entity.getEndTime())){
            map.put("StartTime","预定结束时间为空");
        }
        if(StringUtil.isEmpty(entity.getOrderGuid())){
            map.put("StartTime","订单GUID为空");
        }
        if(StringUtil.isEmpty(entity.getActivityGuid())){
            map.put("StartTime","商品GUID为空");
        }
        if(map.size() > 0){
            return R.error(Content.STATUS_CODE_5006).put("error", map);
        }
        return null;
    }


    public R selectOneByOrderGuid(String orderGuid) {
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空");
        }
        return null;
    }


    public R receive(ReserveEntity reserveEntity) {
        if(reserveEntity.getId() == null){
            return R.error(Content.STATUS_CODE_5006,"预订GUID为空");
        }
        if(reserveEntity.getState() == null){
            return R.error(Content.STATUS_CODE_5006,"预订状态为空");
        }
        return null;
    }

}
