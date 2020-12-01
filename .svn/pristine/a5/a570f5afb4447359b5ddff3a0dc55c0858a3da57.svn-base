package com.zqkj.service.impl;

import cn.hutool.json.JSONObject;
import com.zqkj.bean.Constants;
import com.zqkj.entity.UserEntity;
import com.zqkj.entity.WxBeingPushedEntity;
import com.zqkj.service.UserService;
import com.zqkj.service.WxBeingPushedService;
import com.zqkj.service.WxService;
import com.zqkj.utils.StringUtil;
import com.zqkj.utils.wx.WeinXinUtil;
import com.zqkj.utils.wxtemplate.PushMessage;
import com.zqkj.utils.wxtemplate.WechatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信业务实现
 */
@Service("wxBeingPushed")
public class WxBeingPushedServiceImpl implements WxBeingPushedService {

    @Autowired
    private UserService userService;

    @Autowired
    private WxService wxService;

    @Autowired
    private Constants constants;

    @Override
    public Integer beingPushed(WxBeingPushedEntity wxBeingPushedEntity) {
        int nRet = 0;
        //微信公众号消息推送
        UserEntity userEntity = new UserEntity();
        userEntity.setType(0);
        List<UserEntity> userList = userService.select(userEntity);   //获取用户信息

        Map<String,String> map = WeinXinUtil.getInstance().getMap(constants.getAppid(),constants.getAppsecret());
        String accessToken = map.get("access_token");

        //设置消息模板
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        //根据具体模板参数组装
        params.put("first", WechatTemplate.item(wxBeingPushedEntity.getTitle(), "#000000"));
        params.put("type", WechatTemplate.item("旅行社", "#000000"));
        params.put("name", WechatTemplate.item("九洲高尔夫", "#000000"));
        params.put("productType", WechatTemplate.item("服务", "#000000"));
        params.put("serviceName", WechatTemplate.item(wxBeingPushedEntity.getProductName(), "#000000"));
        params.put("time", WechatTemplate.item(wxBeingPushedEntity.getDate(), "#000000"));
        params.put("remark", WechatTemplate.item(wxBeingPushedEntity.getData(), "#000000"));
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("h5BkgknHWyyNmqvqin9_KqxUlqz0bwBVVqS8wlIRNns");   //微信模板id
        wechatTemplate.setUrl(wxBeingPushedEntity.getUrl());
        wechatTemplate.setData(params);
        for(UserEntity user : userList){
            if(!StringUtil.isEmpty(user.getOpenid()) && user.getType() == 0){
                wechatTemplate.setTouser(user.getOpenid()); //openId
                String code = PushMessage.send_template_message(wechatTemplate,accessToken);
                System.out.println(code);
                nRet += 1;
            }
        }
        return nRet;
    }
}
