package com.zqkj.controller.validata;


import com.zqkj.bean.BackOrderParameteBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;


public class BackOrderValidata {

    public R addHotelOrder(BackOrderParameteBean hotelOrderParameteBean){
        if(StringUtil.isEmpty(hotelOrderParameteBean.getActivityGuid())){
            return R.error(Content.STATUS_CODE_20100,"商品guid为空");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getStartDate())){
            return R.error(Content.STATUS_CODE_20100,"请选择打球入住时间");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getEndDate())){
            return R.error(Content.STATUS_CODE_20100,"请选择离店日期");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getRemark())){
            return R.error(Content.STATUS_CODE_20100,"请填入入住人信息");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getUserGuid())){
            return R.error(Content.STATUS_CODE_20100,"用户GUID为空");
        }
        if(hotelOrderParameteBean.getDateTotal() == null){
            return R.error(Content.STATUS_CODE_20100,"预定天数为空");
        }
        if(hotelOrderParameteBean.getRoomTotal() == null){
            return R.error(Content.STATUS_CODE_20100,"预定间数为空");
        }
        return null;
    }


    public R addIntroductionOrderPay(BackOrderParameteBean hotelOrderParameteBean){
        if(StringUtil.isEmpty(hotelOrderParameteBean.getIntroductionGuid())){
            return R.error(Content.STATUS_CODE_20100,"商品guid为空");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getTime())){
            return R.error(Content.STATUS_CODE_20100,"请选择打球时间");
        }
        if(StringUtil.isEmpty(hotelOrderParameteBean.getDate())){
            return R.error(Content.STATUS_CODE_20100,"请选择打球日期");
        }
        if(hotelOrderParameteBean.getPrice() == null){
            return R.error(Content.STATUS_CODE_20100,"价格为空");
        }
        if(hotelOrderParameteBean.getBuySum() == null || hotelOrderParameteBean.getBuySum() == 0){
            return R.error(Content.STATUS_CODE_20100,"购买数量最少一套");
        }
        return null;
    }


    public R businessConfirm(@RequestBody OrderEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_20100,"订单id为空");
        }
        if(entity.getBusinConfi() == null){
            return R.error(Content.STATUS_CODE_20100,"确认状态为空");
        }
        return null;
    }
}
