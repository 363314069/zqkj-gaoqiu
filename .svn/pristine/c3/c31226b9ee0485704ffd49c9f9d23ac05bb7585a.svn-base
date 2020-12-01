package com.zqkj.controller.validata;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zqkj.entity.CommissionEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;

public class CommissionValidata extends BaseValidata<CommissionEntity> {

    public R page(PageUtil<CommissionEntity> page, CommissionEntity entity) {
        return null;
    }

    public R list(CommissionEntity entity, String orderBy) {
        return null;
    }

    public R addCommission(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);

        if(StringUtil.isEmpty(jsonObject.getStr("sourceGuid"))){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("productGuid"))){
            return R.error(Content.STATUS_CODE_5006,"商品GUID为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("creator"))){
            return R.error(Content.STATUS_CODE_5006,"创建人为空！");
        }
        if(jsonObject.getInt("sum") == null){
            return R.error(Content.STATUS_CODE_5006,"购买数量为空！");
        }
        return null;
    }

    public R selectUserCommission(Integer state){
        if(state == null){
            return R.error(Content.STATUS_CODE_5006,"佣金状态为空");
        }
        return null;
    }


    public R analysisCommission(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);

        if(StringUtil.isEmpty(jsonObject.getStr("sourceGuid"))){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("productGuid"))){
            return R.error(Content.STATUS_CODE_5006,"商品GUID为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("inviterGuid"))){
            return R.error(Content.STATUS_CODE_5006,"邀请人为空！");
        }
        if(jsonObject.getInt("sum") == null){
            return R.error(Content.STATUS_CODE_5006,"购买数量为空！");
        }
        return null;
    }


    public R cashWithdrawal(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);

        String userGuid = jsonObject.getStr("sourceGuid");
        Integer totalAmount = jsonObject.getInt("totalAmount");
        JSONArray guids = jsonObject.getJSONArray("guids");
        Integer state = jsonObject.getInt("state");
        if(StringUtil.isEmpty(jsonObject.getStr("userGuid"))){
            return R.error(Content.STATUS_CODE_5006,"用户GUID为空！");
        }
        if(jsonObject.getInt("totalAmount") == null){
            return R.error(Content.STATUS_CODE_5006,"提现金额为空！");
        }
        if(jsonObject.getJSONArray("guids").size() < 1){
            return R.error(Content.STATUS_CODE_5006,"佣金GUID为空！");
        }
        if(state == null){
            return R.error(Content.STATUS_CODE_5006,"状态为空！");
        }
        return null;
    }


    public R conduct(@RequestBody String orderGuid){
        if(StringUtil.isEmpty(orderGuid)){
            return R.error(Content.STATUS_CODE_5006,"订单GUID为空！");
        }
        return null;
    }
}
