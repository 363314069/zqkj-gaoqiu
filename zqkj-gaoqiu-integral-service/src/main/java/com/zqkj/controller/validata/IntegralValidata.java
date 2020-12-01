package com.zqkj.controller.validata;

import cn.hutool.json.JSONObject;
import com.zqkj.entity.IntegralEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;

public class IntegralValidata extends BaseValidata<IntegralEntity> {

    public R integralRecurrence(@RequestBody String jsonStr){
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
}
