package com.zqkj.controller.validata;


import com.zqkj.bean.HotelOrderParameteBean;
import com.zqkj.bean.OrderVipBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OrderValidata {

    public R getApiConfig() {
        return null;
    }

    public R addOrder(String goodsGuid,String inviterGuid,Integer sum,Integer[] typeArr,String userJson,String remark){
        if(StringUtil.isEmpty(goodsGuid)){
            return R.error(Content.STATUS_CODE_5006,"商品guid为空");
        }
        if(sum == null){
            return R.error(Content.STATUS_CODE_5006,"购买数量为空");
        }
        if(typeArr != null){
            if(typeArr.length != sum){
                return R.error(Content.STATUS_CODE_5006,"购买数量和金额有误");
            }
        }
        return null;
    }

    public R addIntroductionOrder(String introductionGuid,String date,String time, Integer price){
        if(StringUtil.isEmpty(introductionGuid)){
            return R.error(Content.STATUS_CODE_20100,"商品guid为空");
        }
        if(StringUtil.isEmpty(time)){
            return R.error(Content.STATUS_CODE_20100,"请选择打球时间");
        }
        if(StringUtil.isEmpty(date)){
            return R.error(Content.STATUS_CODE_20100,"请选择打球日期");
        }
        if(price == null){
            return R.error(Content.STATUS_CODE_20100,"价格为空");
        }
        return null;
    }


    public R payIntroductionOrder(String  orderGuid,String[] couponsUserGuid,Integer goldSum){
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_20100,"订单guid为空");
        }
        return null;
    }


    public R addIntroductionOrderPay(String introductionGuid,String date,String time, Integer price,String[] couponsUserGuid,String remark,Integer buySum,Integer goldSum){
        if(StringUtil.isEmpty(introductionGuid)){
            return R.error(Content.STATUS_CODE_20100,"商品guid为空");
        }
        if(StringUtil.isEmpty(time)){
            return R.error(Content.STATUS_CODE_20100,"请选择打球时间");
        }
        if(StringUtil.isEmpty(date)){
            return R.error(Content.STATUS_CODE_20100,"请选择打球日期");
        }
        if(price == null){
            return R.error(Content.STATUS_CODE_20100,"价格为空");
        }
        if(buySum == null || buySum == 0){
            return R.error(Content.STATUS_CODE_20100,"购买数量最少一套");
        }
        return null;
    }


    public R addHotelOrder(HotelOrderParameteBean hotelOrderParameteBean){
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
        if(hotelOrderParameteBean.getDateTotal() == null){
            return R.error(Content.STATUS_CODE_20100,"预定天数为空");
        }
        if(hotelOrderParameteBean.getRoomTotal() == null){
            return R.error(Content.STATUS_CODE_20100,"预定间数为空");
        }
        return null;
    }


    public R jsapiPay(String orderGuid){
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        return null;
    }

    public String payNotify(HttpServletRequest request) {
        return null;
    }

    public R selectlist(OrderEntity entity, String orderBy){
        return null;
    }

    public R info(OrderEntity entity){
        return null;
    }

    public R userTotal(String goodsGuid){
        if(StringUtil.isEmpty(goodsGuid)){
            return R.error(Content.STATUS_CODE_5006,"产品GUID为空！");
        }
        return null;
    }

    public R updateVipGuid(@RequestBody OrderVipBean orderVipBean){
        if(StringUtil.isEmpty(orderVipBean.getOrderGuid())){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        if(StringUtil.isEmpty(orderVipBean.getVipGuid())){
            return R.error(Content.STATUS_CODE_5006,"VIP GUID为空！");
        }
        return null;
    }


    public R selectOneOrder(@RequestBody String guid){
        if(StringUtil.isEmpty(guid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        return null;
    }


    public R update(OrderEntity entity) {
        if(ObjectUtil.isAllNull(entity)){
            return R.error(Content.STATUS_CODE_5001).put("count", 0);
        }
        return null;
    }


    public R page(PageUtil<OrderEntity> page, OrderEntity entity) {
        return null;
    }

    public R queryList(OrderEntity entity) {
        if(StringUtil.isEmpty(entity.getGoodsGuid())){
            return R.error(Content.STATUS_CODE_5006,"商品GUID为空！");
        }
        return null;
    }

    public R list(OrderEntity entity, String orderBy) {
        return null;
    }


    public R selectListPage(PageUtil<OrderEntity> page, OrderEntity entity) {
        return null;
    }

    public R businessConfirm(@RequestBody OrderEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_20100,"订单id为空");
        }
        if(entity.getBusinConfi() == null){
            return R.error(Content.STATUS_CODE_20100,"确认状态为空");
        }else{
            if(entity.getBusinConfi() == 4){
                if(StringUtil.isEmpty(entity.getTime())){
                    return R.error(Content.STATUS_CODE_20100,"修改时间不能为空");
                }
            }
        }
        return null;
    }

    public R listByGuids(String[] guids) {
        if(guids == null && guids.length == 0){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }


    public R cashWithdrawalOrder(Integer totalAmount,String[] commissionGuids) {
        if(totalAmount == null || totalAmount < 100){
            return R.error(Content.STATUS_CODE_5006,"提现金额必须大于等于1元");
        }
        if(commissionGuids == null || commissionGuids.length < 0){
            return R.error(Content.STATUS_CODE_5006,"请选择佣金列表");
        }
        return null;
    }


    public R cashWithdrawal(String guid) {
        if(StringUtil.isEmpty(guid)){
            return R.error(Content.STATUS_CODE_5006,"订单guid为空");
        }
        return null;
    }


    public R directlyWithdrawal(Integer totalAmount,String[] commissionGuids) {
        if(totalAmount == null || totalAmount < 100 || totalAmount > 20000){
            return R.error(Content.STATUS_CODE_5006,"提现金额范围在1~200元之间");
        }
        if(commissionGuids == null || commissionGuids.length < 0){
            return R.error(Content.STATUS_CODE_5006,"请选择佣金列表");
        }
        return null;
    }

    public R callbackWithdrawal(@RequestBody String strJson) {
        if(StringUtil.isEmpty(strJson)){
            return R.error(Content.STATUS_CODE_5006,"参数为空");
        }else{
            cn.hutool.json.JSONObject jsonObject = new cn.hutool.json.JSONObject(strJson);
            String userGuid = jsonObject.getStr("userGuid");      //用户guid
            Integer totalAmount = jsonObject.getInt("totalAmount");   //总金额
            if(StringUtil.isEmpty(userGuid)){
                return R.error(Content.STATUS_CODE_5006,"用户guid为空");
            }
            if(totalAmount == null){
                return R.error(Content.STATUS_CODE_5006,"提现金额为空");
            }
        }
        return null;
    }

    public R cancelOrder(Integer id){
        if(id == null){
            return R.error(Content.STATUS_CODE_5006,"订单ID为空！");
        }
        return null;
    }

    public R userConfirm(OrderEntity orderEntity){
        if(orderEntity.getId() == null){
            return R.error(Content.STATUS_CODE_5006,"订单ID为空");
        }
        if(orderEntity.getBusinConfi() == null){
            return R.error(Content.STATUS_CODE_5006,"确认状态为空");
        }
        return null;
    }


    public R listByGoodsGuid(String[] goodsGuids,String[] businConfis) {
        if(goodsGuids == null || goodsGuids.length < 0){
            return R.error(Content.STATUS_CODE_5006,"产品GUID为空");
        }
        return null;
    }

    public R selectOneExtend(@RequestBody String orderGuid){
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        return null;
    }

    public R test() {
        return null;
    }
}
