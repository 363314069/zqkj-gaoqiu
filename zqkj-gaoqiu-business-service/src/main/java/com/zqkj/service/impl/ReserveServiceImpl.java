package com.zqkj.service.impl;

import cn.hutool.json.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.zqkj.bean.SmsBean;
import com.zqkj.dao.mapper.ReserveDao;
import com.zqkj.entity.ActivityEntity;
import com.zqkj.entity.ReserveEntity;
import com.zqkj.remote.service.UserService;
import com.zqkj.remote.service.WxService;
import com.zqkj.service.ActivityService;
import com.zqkj.service.ReserveService;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.wxtemplate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;


/**
 * 预定表
 */
@Service("reserveService")
public class ReserveServiceImpl extends BaseServiceImpl<ReserveDao, ReserveEntity> implements ReserveService {

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private WxService wxService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public R receive(ReserveEntity reserveEntity) {
        try {
            int nRet = updateByPrimaryKeySelective(reserveEntity);
            if(nRet > 0){
                reserveEntity = selectOneById(reserveEntity.getId());
                ActivityEntity activityEntity = activityService.selectOneByGuid(reserveEntity.getActivityGuid());
                String[] phoneNumbers = {reserveEntity.getUserPhone()};//电话
                smsBean.setTemplateId(551126);// 短信模板 ID，需要在短信应用中申请

                /*String activityName = activityEntity.getName();
                if(activityName.length() > 9){
                    activityName = activityName.substring(0, 8)+"……";
                }*/

                String[] paramsSms = {activityEntity.getName(),reserveEntity.getStartTime(),activityEntity.getPhone()};
                SmsSingleSender ssender = new SmsSingleSender(smsBean.getAppid(), smsBean.getAppkey());
                SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                        smsBean.getTemplateId(), paramsSms, smsBean.getSmsSign(), "", "");
                System.out.println(result);
                cn.hutool.json.JSONObject jsonSms = new cn.hutool.json.JSONObject(result.toString());

                //微信公众号消息推送
                String user = userService.selectOne(reserveEntity.getUserGuid());   //获取用户信息
                String openId = "";
                JSONObject userJson = new JSONObject(user);
                if(userJson.getInt("code") == 0){
                    JSONObject userData = new JSONObject(userJson.getStr("data"));
                    openId = userData.getStr("openid");
                }

                JSONObject accessTokenJson = new JSONObject(wxService.getAccessToken());    //获取accessToken
                String accessToken = accessTokenJson.getStr("data");

                //设置消息模板
                TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                //根据具体模板参数组装
                params.put("first", WechatTemplate.item("您好，您已预订成功。", "#000000"));
                params.put("type", WechatTemplate.item("旅行社", "#000000"));
                params.put("name", WechatTemplate.item("九洲高尔夫", "#000000"));
                params.put("productType", WechatTemplate.item("服务", "#000000"));
                params.put("serviceName", WechatTemplate.item(activityEntity.getName(), "#000000"));
                params.put("time", WechatTemplate.item(reserveEntity.getStartTime(), "#000000"));
                params.put("remark", WechatTemplate.item("如有疑问，请咨询：" + activityEntity.getPhone(), "#000000"));
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id("h5BkgknHWyyNmqvqin9_KqxUlqz0bwBVVqS8wlIRNns");   //微信模板id
                wechatTemplate.setTouser(openId); //openId
                wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/snapUp_info.html?guid="+activityEntity.getGuid());
                wechatTemplate.setData(params);
                String code = PushMessage.send_template_message(wechatTemplate,accessToken);
                if(jsonSms.getInt("result") == 0){
                    return R.ok().putData(reserveEntity);
                }else{
                    return R.error(Content.STATUS_CODE_5001,"通知失败");
                }
            }else{
                return R.error(Content.STATUS_CODE_5001,"更新失败");
            }
        }catch (Exception e){
            return R.error(Content.STATUS_CODE_5001,"通知失败");
        }
    }
}
