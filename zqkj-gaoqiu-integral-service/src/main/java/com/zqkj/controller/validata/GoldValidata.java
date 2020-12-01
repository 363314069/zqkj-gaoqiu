package com.zqkj.controller.validata;

import cn.hutool.json.JSONObject;
import com.zqkj.entity.GoldEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;

public class GoldValidata extends BaseValidata<GoldEntity> {

    public R productCashBack(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);
        if(StringUtil.isEmpty(jsonObject.getStr("sourceGuid"))){
            return R.error(Content.STATUS_CODE_5006,"订单guid为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("userGuid"))){
            return R.error(Content.STATUS_CODE_5006,"用户guid为空！");
        }
        if(StringUtil.isEmpty(jsonObject.getStr("productGuid"))){
            return R.error(Content.STATUS_CODE_5006,"产品guid为空！");
        }
        if(jsonObject.getInt("sum") == null){
            return R.error(Content.STATUS_CODE_5006,"数量为空！");
        }
        return null;
    }
}
