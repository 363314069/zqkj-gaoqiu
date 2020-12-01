package com.zqkj.controller;

import com.zqkj.bean.Constants;
import com.zqkj.service.WxService;
import com.zqkj.utils.*;
import com.zqkj.utils.wx.WeinXinUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Created by zqkj on 2019/9/25.
 */
@Controller
@RequestMapping("/security/wxoauth")
public class WxController {

    @Autowired
    private Constants constants;

    @Autowired
    private WxService wxService;

    @RequestMapping(value = "/wxlogin", method = RequestMethod.GET)
    public String wxLogin(){
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constants.getAppid() +
                "&redirect_uri=" + URLEncoder.encode(constants.getCallback()) +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        return "redirect:" + url;
    }

    @RequestMapping(value = "/wxcallback", method = RequestMethod.GET)
    @ApiOperation(value = "登录成功后跳转")
    public String wxCallback(String code) {
        return wxService.wxCallback(code);
    }

    @RequestMapping(value = "/gohome", method = RequestMethod.GET)
    public ModelAndView goHome(String openid){
        return wxService.goHome(openid);
    }

    @RequestMapping(value = "/authcode", method = RequestMethod.POST)
    @ApiOperation(value = "发送短信验证码")
    @ResponseBody
    public R authCode(String phone){
        return wxService.authCode(phone);
    }

    /**
     * 完善信息
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public R register(String name,String phone,String chad,String authCode) {
        return wxService.register(name,phone,chad,authCode);
    }

    /**
     * 微信分享获取签名及其他参数
     */
    @RequestMapping(value = "/share", method = {RequestMethod.POST})
    @ResponseBody
    public R share(String strUrl) {
        return wxService.share(strUrl);
    }


    /**
     * 其他服务远程调用微信accessToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getaccesstoken", method = { RequestMethod.GET, RequestMethod.POST})
    public R getAccessToken() {
        Map<String,String> map = WeinXinUtil.getInstance().getMap(constants.getAppid(),constants.getAppsecret());
        if(map == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(map.get("access_token"));
        }
    }


    /**
     * 判断是否关注公众号
     * @param openid
     * @return
     */
    @RequestMapping(value = "/accountsinfo", method = RequestMethod.GET)
    @ApiOperation(value = "判断是否关注")
    @ResponseBody
    public R wxOfficialAccountsInfo(String openid) {
        return wxService.wxOfficialAccountsInfo(openid);
    }


    /**
     * 生成个人邀请二维码
     * @param codeUrl
     * @return
     */
    @RequestMapping(value = "/invitecode", method = RequestMethod.POST)
    @ApiOperation(value = "生成个人邀请二维码")
    @ResponseBody
    public R inviteCode(String codeUrl) {
        return wxService.inviteCode(codeUrl);
    }


    /**
     * 用户接受邀请进行注册
     */
    @RequestMapping(value = "/acceptregister", method = {RequestMethod.POST})
    @ResponseBody
    public R acceptRegister(String name,String phone,String chad,String authCode,String inviterGuid) {
        return wxService.acceptRegister(name,phone,chad,authCode,inviterGuid);
    }


    /**
     * 后台T-time管理员绑定
     * @return
     */
    @RequestMapping(value = "/orgtimebind", method = RequestMethod.GET)
    public String orgTimeBind(String userGuid,Integer state){
        String url = "";
        if(state == 2){//1登录  2绑定
            url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constants.getAppid() +
                    "&redirect_uri=" + URLEncoder.encode(constants.getOrgBindCallback()) +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state="+userGuid+"#wechat_redirect";
        }else{
            url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constants.getAppid() +
                    "&redirect_uri=" + URLEncoder.encode(constants.getWxLoginBindCallback()) +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state="+userGuid+"#wechat_redirect";
        }
        return "redirect:" + url;
    }


    @RequestMapping(value = "/wxloginbindcallback", method = RequestMethod.GET)
    @ApiOperation(value = "微信授权登录没有绑定跳转到网页登录")
    public ModelAndView wxLoginBindCallback(String code) {
        try {
            return wxService.wxLoginBindCallback(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", "");
        modelAndView.addObject("token","");
        modelAndView.addObject("state", 2);
        modelAndView.setViewName("orgUserLogin");
        return modelAndView;
    }



    @RequestMapping(value = "/orgbindcallback", method = RequestMethod.GET)
    @ApiOperation(value = "绑定授权后业务处理")
    public ModelAndView orgBindCallback(String code,String state) {
        try {
            return wxService.orgBindCallback(code,state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
