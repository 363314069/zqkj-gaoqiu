package com.zqkj.utils.wxtemplate;


import cn.hutool.json.JSONObject;
import com.zqkj.utils.wxtemplate.util.WeixinUtil;

/**
 * Created by zqkj on 2019/12/10.
 */
public class PushMessage {

    /**
     *  * 发送模板消息  
     * appId 公众账号的唯一标识
     * appSecret 公众账号的密钥  
     * openId 用户标识     
     */
    public static String send_template_message(WechatTemplate temp,String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
        String jsonString = new JSONObject(temp).toString();
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
        System.out.println(jsonObject);
        String code = "500";
        if(jsonObject != null){
            code = jsonObject.getStr("errcode");
        }
        return code;
    }
}
