package com.zqkj.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 微信常量配置类
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "oauth")
@Data
public class Constants {
    public String appid;
    public String appsecret;
    public String callback;//H5用户微信授权登录回调地址
    public String orgBindCallback;//后台T-time管理员绑定回调地址
    public String wxLoginBindCallback;//商家后台微信登录回调地址
}