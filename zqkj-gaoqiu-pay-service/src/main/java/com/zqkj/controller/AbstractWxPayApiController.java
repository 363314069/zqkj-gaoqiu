package com.zqkj.controller;


import com.ijpay.wxpay.WxPayApiConfig;

/**
 * @author Javen
 */
public interface AbstractWxPayApiController {
    /**
     * 获取微信支付配置
     *
     * @return {@link WxPayApiConfig} 微信支付配置
     */
    public WxPayApiConfig getApiConfig();
}
