package com.zqkj.utils.wx;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONObject;
import com.zqkj.entity.WinXinEntity;

/**
 * 微信分析获取access_token及其他参数
 */
public class WeinXinUtil {

    private Map<String, String> map = new HashMap<>();
    private WeinXinUtil() {

    }
    private static WeinXinUtil single = null;
    // 静态工厂方法
    public static WeinXinUtil getInstance() {
        if (single == null) {
            single = new WeinXinUtil();
         }
        return single;
    }

    public static WinXinEntity getWinXinEntity(String url,String jsapi_ticket,String appid) {
        WinXinEntity wx = new WinXinEntity();
        Map<String, String> ret = Sign.sign(jsapi_ticket, url);
        wx.setTicket(ret.get("jsapi_ticket"));
        wx.setSignature(ret.get("signature"));
        wx.setNoncestr(ret.get("nonceStr"));
        wx.setTimestamp(ret.get("timestamp"));
        wx.setAppId(appid);
        return wx;
    }

    public Map<String, String> getMap(String appid,String appsercret) {
        String time = map.get("time");
        String accessToken = map.get("access_token");
        Long nowDate = new Date().getTime();
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 6000*1000) {
            System.out.println("accessToken存在，且没有超时 ， 返回单例");
        } else {
            System.out.println("accessToken 超时 ， 或者不存在 ， 重新获取");
            String access_token = getAccessToken(appid,appsercret);
            //这里是直接调用微信的API去直接获取 accessToken 和Jsapi_ticket 获取
            String jsapi_token = getTicket(access_token);
            //"获取jsapi_token";
            map.put("time", nowDate + "");
            map.put("access_token", access_token);
            map.put("jsapi_token", jsapi_token);
        }
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    public static WeinXinUtil getSingle() {
        return single;
    }
    public static void setSingle(WeinXinUtil single) {
        WeinXinUtil.single = single;
    }


    //获取token
    private static String getAccessToken(String wxAppId,String wxSecret) {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
        String AppId = wxAppId;//第三方用户唯一凭证
        String secret = wxSecret;//第三方用户唯一凭证密钥，即appsecret
        //这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;  //访问链接

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            /*System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒 */
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = new JSONObject(message);
            access_token = demoJson.getStr("access_token");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }

    //获取ticket
    private static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = new JSONObject(message);
            ticket = demoJson.getStr("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }
}
