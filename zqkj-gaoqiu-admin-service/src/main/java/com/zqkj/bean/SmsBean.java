package com.zqkj.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 短信发送配置
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "sms")
@Data
public class SmsBean {
    private int appid; // 短信应用 SDK AppID 以1400开头
    private String appkey; // 短信应用 SDK AppKey
    private String smsSign; // 签名
    private String[] phoneNumbers; // 需要发送短信的手机号码
    private int templateId; // 短信模板 ID，需要在短信应用中申请
}