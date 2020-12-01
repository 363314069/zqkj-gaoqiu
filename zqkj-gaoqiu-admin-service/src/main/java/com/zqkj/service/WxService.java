package com.zqkj.service;


import com.zqkj.utils.R;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 微信业务
 */
public interface WxService {

    //微信授权登录成功后跳转
    public String wxCallback(String code);

    //获取用户信息存储到数据库中
    public ModelAndView goHome(String openid);

    //发送短信验证码
    public R authCode(String phone);

    //完善个人信息
    public R register(String name,String phone,String chad,String authCode);

    //微信分享获取签名及其他参数
    public R share(String strUrl);

    //判断用户是否关注公众号
    public R wxOfficialAccountsInfo(String openid);

    //生成个人邀请二维码
    public R inviteCode(String codeUrl);

    //用户接受邀请进行注册
    public R acceptRegister(String name,String phone,String chad,String authCode,String inviterGuid);

    //绑定授权后业务处理
    public ModelAndView orgBindCallback(String code,String userGuid) throws IOException;

    //微信授权登录没有绑定跳转到网页登录
    public ModelAndView wxLoginBindCallback(String code) throws IOException;
}
