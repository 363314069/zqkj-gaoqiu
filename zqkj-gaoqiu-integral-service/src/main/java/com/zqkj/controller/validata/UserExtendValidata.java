package com.zqkj.controller.validata;

import com.alibaba.fastjson.JSONObject;
import com.zqkj.entity.UserExtendEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class UserExtendValidata extends BaseValidata<UserExtendEntity> {

    public R list(UserExtendEntity entity, String orderBy) {
        return null;
    }

    public R info(UserExtendEntity entity) {
        return null;
    }

    public R update(UserExtendEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(entity.getId() == null)
            map.put("id", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).putError(map);
        return null;
    }

    public R del(UserExtendEntity entity) {

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

    public R page(PageUtil<UserExtendEntity> page, UserExtendEntity entity) {

        return null;
    }


    public R cancelOrderBalance(@RequestBody String jsonStr) {
        if(StringUtil.isEmpty(jsonStr)){
            return R.error(Content.STATUS_CODE_5006,"参数为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String orderGuid = jsonObject.getString("orderGuid");//订单guid
        String userGuid = jsonObject.getString("userGuid");//用户guid
        Integer orderMoney = jsonObject.getInteger("payMoney");//订单金额
        Integer state = jsonObject.getInteger("state");//订单支付状态

        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空");
        }
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空");
        }
        if(state == null){
            return R.error(Content.STATUS_CODE_5006,"订单支付状态为空");
        }/*else{
            if(state == 1){
                if(orderMoney == null){
                    return R.error(Content.STATUS_CODE_5006,"订单金额为空");
                }
            }
        }*/
        return null;
    }


    public R useGold(@RequestBody String jsonStr) {
        if(StringUtil.isEmpty(jsonStr)){
            return R.error(Content.STATUS_CODE_5006,"参数为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String orderGuid = jsonObject.getString("orderGuid");//订单guid
        String userGuid = jsonObject.getString("userGuid");//用户guid
        Integer orderMoney = jsonObject.getInteger("orderMoney");//订单金额
        Integer goldSum = jsonObject.getInteger("goldSum");//使用金币数量
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空");
        }
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空");
        }
        if(orderMoney == null){
            return R.error(Content.STATUS_CODE_5006,"订单金额为空");
        }
        if(goldSum == null){
            return R.error(Content.STATUS_CODE_5006,"使用金币数量为空");
        }
        return null;
    }


    public R registerGiving(@RequestBody String userGuid) {
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R binVip(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String userGuid = jsonObject.getString("userGuid");//用户GUID
        String vipGuid = jsonObject.getString("vipGuid");//会员卡GUID
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空");
        }
        if(StringUtil.isEmpty(vipGuid)){
            return R.error(Content.STATUS_CODE_5006,"会员卡GUID为空");
        }
        return null;
    }

    public R selectList(UserExtendEntity extendEntity) {
        return null;
    }
}
