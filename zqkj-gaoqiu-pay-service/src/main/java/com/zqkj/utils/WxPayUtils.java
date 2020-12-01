package com.zqkj.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.IpKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.RefundModel;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import com.jfinal.kit.StrKit;
import com.zqkj.bean.WxPayBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.utils.ip.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zqkj on 2019/10/24.
 */
@Component
public class WxPayUtils {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxPayBean wxPayBean;

    public R webPay(HttpServletRequest request, OrderEntity orderEntity) {
        String ip = IPUtil.getIpAddr(request);
        System.out.println("----------IP:"+ip);
        if (StrUtil.isEmpty(ip)) {
            ip = "127.0.0.1";
        }
        if(ip.indexOf(",") != -1){
            ip = ip.substring(0,ip.indexOf(","));
        }
        System.out.println(ip);
        WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(wxPayApiConfig.getAppId())
                .mch_id(wxPayApiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .body(orderEntity.getName())
                //.attach("Node.js 版:https://gitee.com/javen205/TNW")
                .out_trade_no(orderEntity.getOrderNumber())
                .total_fee(orderEntity.getOrderMoney().toString())
                .spbill_create_ip(ip)
                .notify_url(wxPayBean.getNotifyUrl())
                .trade_type(TradeType.JSAPI.getTradeType())
                .openid(orderEntity.getOpenid())
                .build()
                .createSign(wxPayApiConfig.getPartnerKey(), SignType.HMACSHA256);

        String xmlResult = WxPayApi.pushOrder(false, params);
        log.info(xmlResult);

        Map<String, String> resultMap = WxPayKit.xmlToMap(xmlResult);
        String returnCode = resultMap.get("return_code");
        String returnMsg = resultMap.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            return R.error(2,returnMsg);
        }
        String resultCode = resultMap.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            return R.error(2,returnMsg);
        }

        // 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回

        String prepayId = resultMap.get("prepay_id");

        Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId, wxPayBean.getAppId(),wxPayBean.getPartnerKey(), SignType.HMACSHA256);
        packageParams.put("orderGuid",orderEntity.getGuid());
        String jsonStr = JSON.toJSONString(packageParams);
        System.out.println(jsonStr);
        return R.ok(jsonStr);
    }


    /**
     * 微信退款
     * @param orderEntity
     * @return
     */
    public String refund(OrderEntity orderEntity) {
        WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
        Map<String, String> params = RefundModel.builder()
                .appid(wxPayApiConfig.getAppId())
                .mch_id(wxPayApiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())      //随机字符串
                //.transaction_id(transactionId)            //微信生成的订单号，在支付通知中有返回  二选一
                .out_trade_no(orderEntity.getOrderNumber())       //商户订单号   同时存在优先级：transaction_id> out_trade_no
                .out_refund_no(WxPayKit.generateStr()) //商户系统内部的退款单号，商户系统内部唯一
                .total_fee(orderEntity.getPayMoney().toString())     //订单金额
                .refund_fee(orderEntity.getPayMoney().toString())       //退款金额
                .build()
                .createSign(wxPayApiConfig.getPartnerKey(), SignType.MD5);
        return WxPayApi.orderRefund(false, params, wxPayApiConfig.getCertPath(), wxPayApiConfig.getMchId());
    }
}
