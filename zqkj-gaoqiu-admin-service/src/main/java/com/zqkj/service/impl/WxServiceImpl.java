package com.zqkj.service.impl;

import cn.hutool.json.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.zqkj.bean.Constants;
import com.zqkj.bean.SmsBean;
import com.zqkj.entity.InviteEntity;
import com.zqkj.entity.UserEntity;
import com.zqkj.entity.WinXinEntity;
import com.zqkj.remote.IntegralRemote;
import com.zqkj.service.InviteService;
import com.zqkj.service.UserService;
import com.zqkj.service.WxService;
import com.zqkj.utils.*;
import com.zqkj.utils.ip.IPUtil;
import com.zqkj.utils.jwt.JWTInfo;
import com.zqkj.utils.wx.WeinXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * 微信业务实现
 */
@Service("wxService")
public class WxServiceImpl implements WxService {

    @Value("${zxing.sharePath}")
    private String sharePath ;

    @Autowired
    private Constants constants;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private IntegralRemote integralRemote;


    @Override
    public String wxCallback(String code) {
        String openid = "";
        Integer nRet = 0;
        try {
            //获取用户信息，并返回openid
            openid = userService.wxCallback(code);
            if(StringUtil.isEmpty(openid)){
                return "redirect:/security/wxoauth/wxlogin";
            }
            //进行判断是否关注该公众号
            //nRet = userService.wxOfficialAccountsInfo(openid);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/security/wxoauth/wxlogin";
        }
        /*if(nRet == 1){
            return "redirect:/security/wxoauth/gohome?openid="+openid;
        }else{
            //没有关注公众号 跳转公众号二维码页面
            return "redirect:"+request.getScheme()+"://"+ request.getServerName() + "/wap/business/qr_code.html";
        }*/
        return "redirect:/security/wxoauth/gohome?openid="+openid;
    }

    @Override
    public ModelAndView goHome(String openid) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity json = userService.goHome(openid);
        if(json != null){
            JWTInfo jwtInfo = new JWTInfo();
            jwtInfo.setAppType(json.getAppId() == null ? JWTInfo.APP_TYPE_WEB : json.getAppId());
            jwtInfo.setId(json.getId());
            jwtInfo.setGuid(json.getGuid());
            jwtInfo.setUserName(json.getName());
            jwtInfo.setLoginIp(IPUtil.getIpAddr(request));
            jwtInfo.setOrganizationGuid(json.getOrganizationGuid());
            jwtInfo.setOpenid(json.getOpenid());
            jwtInfo.setType(json.getType());
            String token = null;
            try {
                token = jwtUtil.toJwt(jwtInfo);
            } catch (Exception e) {
                modelAndView.setViewName("跳转错误页面");
                return modelAndView;
            }
            BaseContentHandler.setLoginName(jwtInfo.getLoginName());
            BaseContentHandler.setUserName(jwtInfo.getUserName());
            BaseContentHandler.setUserId(jwtInfo.getId());
            BaseContentHandler.setUserGuid(jwtInfo.getGuid());
            BaseContentHandler.setOrganizationGuid(jwtInfo.getOrganizationGuid());
            BaseContentHandler.set("type", jwtInfo.getType());
            BaseContentHandler.set("jwtInfo", jwtInfo);
            BaseContentHandler.setToken(token);
            BaseContentHandler.setOpenid(jwtInfo.getOpenid());

            json.setToken(token);
            modelAndView.setViewName("home");
            JSONObject jsonss = new  JSONObject(json);
            modelAndView.addObject("user", jsonss.toString());
            modelAndView.addObject("token",token);
            //已经和用户绑定正常跳转到首页
            return modelAndView;
        }
        modelAndView.setViewName("404");
        return modelAndView;
    }

    @Override
    public R authCode(String phone) {
        try{
            String authCode = createRandomVcode();
            String[] phoneNumbers = {phone};//电话
            smsBean.setTemplateId(551110);// 短信模板 ID，需要在短信应用中申请
            String[] params = {authCode};//验证码
            SmsSingleSender ssender = new SmsSingleSender(smsBean.getAppid(), smsBean.getAppkey());
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    smsBean.getTemplateId(), params, smsBean.getSmsSign(), "", "");
            System.out.println(result);
            cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject(result.toString());
            if(json.getInt("result") == 0){
                //发送成功先把电话号码进行记录
                UserEntity userEntity = new UserEntity();
                userEntity.setId(BaseContentHandler.getUserId());
                userEntity.setInitPhone(phone);
                userService.updateByPrimaryKeySelective(userEntity);

                //将验证码存到session中,同时存入创建时间
                //以json存放，这里使用的是阿里的fastjson
                json = new JSONObject();
                json.put("authCode", authCode);
                json.put("createTime", System.currentTimeMillis());
                // 将认证码存入SESSION
                request.getSession().setAttribute(BaseContentHandler.getUserGuid(), json);
                return R.ok(authCode);
            }else{
                return R.error(Content.STATUS_CODE_5324,"发送失败请重试！");
            }
        }catch (Exception e){
            return R.error(Content.STATUS_CODE_5324,"发送失败请重试！");
        }
    }

    @Override
    public R register(String name, String phone, String chad, String authCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute(BaseContentHandler.getUserGuid());
        if(json == null){
            return R.error(Content.STATUS_CODE_5206,"验证码过期,请重新获取！");
        }
        if(!json.getStr("authCode").equals(authCode)){
            return R.error(Content.STATUS_CODE_4002,"验证码错误,请重新获取！");
        }
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 10){
            return R.error(Content.STATUS_CODE_5206,"验证码过期,请重新获取！");
        }
        //将用户信息存入数据库
        UserEntity userEntity = new UserEntity();
        userEntity.setId(BaseContentHandler.getUserId());
        userEntity.setName(name);
        userEntity.setPhone(phone);
        userEntity.setGap(chad);
        Integer nRet = userService.updateByPrimaryKeySelective(userEntity);
        if(nRet > 0){
            //清除当前session
            request.getSession().removeAttribute(BaseContentHandler.getUserGuid());
            return R.ok().putData(userEntity);
        }else{
            return R.error(Content.STATUS_CODE_5005,"信息保存失败");
        }
    }

    @Override
    public R share(String strUrl) {
        //从缓存获取accessToken
        Map<String,String> map = WeinXinUtil.getInstance().getMap(constants.getAppid(),constants.getAppsecret());
        String jsapi_token = map.get("jsapi_token");
        //获取签名及其他信息
        WinXinEntity wx = WeinXinUtil.getWinXinEntity(strUrl,jsapi_token,constants.getAppid());
        return R.ok().putData(wx);
    }

    @Override
    public R wxOfficialAccountsInfo(String openid) {
        Integer nRet = 0;
        try {
            nRet = userService.wxOfficialAccountsInfo(openid);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
        if(nRet == 1){
            return R.ok().putData(true);
        }else{
            return R.ok().putData(false);
        }
    }

    @Override
    public R inviteCode(String codeUrl) {
        UserEntity userEntity = userService.selectOneByGuid(BaseContentHandler.getUserGuid());
        if(userEntity == null){
            return R.error(Content.STATUS_CODE_5004,"查询数为空请重新登录！");
        }else{
            File file = new File(sharePath + userEntity.getGuid()+".png");
            if(!file.exists()){
                BufferedImage zxingImage = null;
                try {
                    //链接拼上用户guid参数
                    codeUrl +="?guid=" + userEntity.getGuid();
                    //二维码图片流
                    zxingImage = ZxingUtils.enQRCode(codeUrl, 350, 350);
                    //背景图片地址
                    String backgroundPath = sharePath + "sharebj.jpg";
                    InputStream inputStream = null;
                    //合成二维码和背景图
                    BufferedImage image = ZxingUtils.drawImage(backgroundPath, zxingImage, 200, 580);
                    //图片转inputStream
                    inputStream = ZxingUtils.bufferedImageToInputStream(image);
                    //保存为本地图片
                    String originalFileName = sharePath + userEntity.getGuid()+".png";
                    ZxingUtils.saveFile(inputStream, originalFileName);
                    return R.ok().putData("upload/zxing/"+userEntity.getGuid()+".png");
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(Content.STATUS_CODE_5004,"生成邀请码错误，请重试！");
                }
            }else{
                return R.ok().putData("upload/zxing/"+userEntity.getGuid()+".png");
            }
        }
    }

    @Override
    public R acceptRegister(String name, String phone, String chad, String authCode,String inviterGuid) {
        JSONObject json = (JSONObject)request.getSession().getAttribute(BaseContentHandler.getUserGuid());
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 10){
            return R.error(Content.STATUS_CODE_5206,"验证码过期");
        }
        if(!json.getStr("authCode").equals(authCode)){
            return R.error(Content.STATUS_CODE_4002,"验证码错误");
        }

        //判断用户是否已经注册
        UserEntity selectUser = userService.selectOneByGuid(BaseContentHandler.getUserGuid());
        if(!StringUtil.isEmpty(selectUser.getPhone())){
            return R.error(Content.STATUS_CODE_5305,"您已经注册请勿重复注册！");
        }


        //将用户信息存入数据库
        UserEntity userEntity = new UserEntity();
        userEntity.setId(BaseContentHandler.getUserId());
        userEntity.setName(name);
        userEntity.setPhone(phone);
        userEntity.setGap(chad);
        Integer nRet = userService.updateByPrimaryKeySelective(userEntity);

        //默认被邀请注册人 送300金币。  送注册人300金币，不是送邀请人
        /*if(nRet > 0){
            //查询扩展表是否有金币记录，有做修改，没有做添加（正常业务不会有记录，防止程序出错导致注册失败）
            String returnStr = integralRemote.registerGiving(BaseContentHandler.getUserGuid());
            System.out.println("金币添加："+returnStr);
        }*/

        /*//邀请表添加记录
        //修改邀请人用户邀请数量
        UserEntity userInviter = userService.selectOneByGuid(inviterGuid);
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setInvitedGuid(inviterGuid);
        int inviteCount = inviteService.selectCount(inviteEntity);
        if(userInviter.getInviteSum() != null){
            if(userInviter.getInviteSum() == -1){
                //-1代表可无限邀请
                InviteEntity inviteSave = new InviteEntity();
                inviteSave.setInvitedGuid(inviterGuid);//邀请人
                inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                inviteSave.setType(0);
                inviteSave.setState(0);
                inviteService.insertSelective(inviteSave);
            }else if(inviteCount >= userInviter.getInviteSum()){
                //如果不是-1，则判断邀请人数限制是否大于，当前已邀请人数，如果大于这继续记录邀请记录，类型为1
                InviteEntity inviteSave = new InviteEntity();
                inviteSave.setInvitedGuid(inviterGuid);//邀请人
                inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                inviteSave.setType(1);
                inviteSave.setState(0);
                inviteService.insertSelective(inviteSave);
            }else {
                //以上条件都不满足则为正常邀请流程
                InviteEntity inviteSave = new InviteEntity();
                inviteSave.setInvitedGuid(inviterGuid);//邀请人
                inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                inviteSave.setType(0);
                inviteSave.setState(0);
                inviteService.insertSelective(inviteSave);
                *//*userInviter.setInviteSum(userInviter.getInviteSum() + 1);
                userService.updateByPrimaryKeySelective(userInviter);*//*
            }
        }else{
            InviteEntity inviteSave = new InviteEntity();
            inviteSave.setInvitedGuid(inviterGuid);//邀请人
            inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
            inviteSave.setType(0);
            inviteSave.setState(0);
            inviteService.insertSelective(inviteSave);
            userInviter.setInviteSum(-1);
            userService.updateByPrimaryKeySelective(userInviter);
        }*/

        //查询用户是否已经被绑定
        InviteEntity inviteSelect = new InviteEntity();
        inviteSelect.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
        int count = inviteService.selectCount(inviteSelect);
        if(count > 0){
            return R.ok().putData(inviteSelect);
        }

        InviteEntity inviteSave = new InviteEntity();
        inviteSave.setInvitedGuid(inviterGuid);//邀请人
        inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
        inviteSave.setType(0);
        inviteSave.setState(0);
        int updateSig = inviteService.insertSelective(inviteSave);

        if(nRet > 0 && updateSig > 0){
            //清除当前session
            request.getSession().removeAttribute(BaseContentHandler.getUserGuid());
            return R.ok().putData(userEntity);
        }else{
            return R.error(Content.STATUS_CODE_5007,"失败请重试！");
        }
    }

    @Override
    public ModelAndView orgBindCallback(String code,String userGuid) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + constants.getAppid() +
                "&secret=" + constants.getAppsecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        com.alibaba.fastjson.JSONObject jsonObject = HttpClientUtils.doGetJson(url.trim());

        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        UserEntity userSelect = new UserEntity();
        userSelect.setOpenid(openid);
        userSelect = userService.selectOne(userSelect);

        UserEntity userEntity = userService.selectOneByGuid(userGuid);
        if(StringUtil.isEmpty(userEntity.getOpenid())){
            userEntity.setLoginPassword(null);
            userEntity.setOpenid(userSelect.getGuid());//绑定微信用户的guid，防止登录时候出现重复数据
            userService.updateByPrimaryKeySelective(userEntity);
        }
        JWTInfo jwtInfo = new JWTInfo();
        jwtInfo.setAppType(userEntity.getAppId() == null ? JWTInfo.APP_TYPE_WEB : userEntity.getAppId());
        jwtInfo.setId(userEntity.getId());
        jwtInfo.setGuid(userEntity.getGuid());
        jwtInfo.setUserName(userEntity.getName());
        jwtInfo.setLoginIp(IPUtil.getIpAddr(request));
        jwtInfo.setOrganizationGuid(userEntity.getOrganizationGuid());
        jwtInfo.setOpenid(userEntity.getOpenid());
        jwtInfo.setType(userEntity.getType());
        jwtInfo.setLoginName(userEntity.getLoginName());
        String token = null;
        try {
            token = jwtUtil.toJwt(jwtInfo);
        } catch (Exception e) {
            modelAndView.setViewName("跳转错误页面");
            return modelAndView;
        }

        BaseContentHandler.setLoginName(jwtInfo.getLoginName());
        BaseContentHandler.setUserName(jwtInfo.getUserName());
        BaseContentHandler.setUserId(jwtInfo.getId());
        BaseContentHandler.setUserGuid(jwtInfo.getGuid());
        BaseContentHandler.setOrganizationGuid(jwtInfo.getOrganizationGuid());
        BaseContentHandler.set("type", jwtInfo.getType());
        BaseContentHandler.set("jwtInfo", jwtInfo);
        BaseContentHandler.setToken(token);
        BaseContentHandler.setOpenid(jwtInfo.getOpenid());

        userEntity.setToken(token);
        modelAndView.setViewName("orgUserBind");
        JSONObject jsonss = new  JSONObject(userEntity);
        modelAndView.addObject("user", jsonss.toString());
        modelAndView.addObject("token",token);
        //已经和用户绑定正常跳转到首页
        return modelAndView;
    }

    @Override
    public ModelAndView wxLoginBindCallback(String code) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + constants.getAppid() +
                "&secret=" + constants.getAppsecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        com.alibaba.fastjson.JSONObject jsonObject = HttpClientUtils.doGetJson(url.trim());

        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        //根据openid查询用户，在用用户的GUID查询是否绑定管理员
        UserEntity userEntity = new UserEntity();
        userEntity.setOpenid(openid);
        userEntity = userService.selectOne(userEntity);

        UserEntity userSelect = new UserEntity();
        userSelect.setOpenid(userEntity.getGuid());//管理员openid绑定的是用户的GUID
        userSelect = userService.selectOne(userSelect);

        if(userSelect == null){
            //没有绑定
            modelAndView.addObject("user", "");
            modelAndView.addObject("token","");
            modelAndView.addObject("state", 2);
            modelAndView.setViewName("orgUserLogin");
            return modelAndView;
        }
        JWTInfo jwtInfo = new JWTInfo();
        jwtInfo.setAppType(userSelect.getAppId() == null ? JWTInfo.APP_TYPE_WEB : userSelect.getAppId());
        jwtInfo.setId(userSelect.getId());
        jwtInfo.setGuid(userSelect.getGuid());
        jwtInfo.setUserName(userSelect.getName());
        jwtInfo.setLoginIp(IPUtil.getIpAddr(request));
        jwtInfo.setOrganizationGuid(userSelect.getOrganizationGuid());
        jwtInfo.setOpenid(userSelect.getOpenid());
        jwtInfo.setType(userSelect.getType());
        jwtInfo.setLoginName(userSelect.getLoginName());
        String token = null;
        try {
            token = jwtUtil.toJwt(jwtInfo);
        } catch (Exception e) {
            modelAndView.setViewName("跳转错误页面");
            return modelAndView;
        }
        BaseContentHandler.setLoginName(jwtInfo.getLoginName());
        BaseContentHandler.setUserName(jwtInfo.getUserName());
        BaseContentHandler.setUserId(jwtInfo.getId());
        BaseContentHandler.setUserGuid(jwtInfo.getGuid());
        BaseContentHandler.setOrganizationGuid(jwtInfo.getOrganizationGuid());
        BaseContentHandler.set("type", jwtInfo.getType());
        BaseContentHandler.set("jwtInfo", jwtInfo);
        BaseContentHandler.setToken(token);
        BaseContentHandler.setOpenid(jwtInfo.getOpenid());

        userSelect.setToken(token);
        modelAndView.setViewName("orgUserLogin");
        JSONObject jsonss = new  JSONObject(userSelect);
        modelAndView.addObject("state", 1);
        modelAndView.addObject("user", jsonss.toString());
        modelAndView.addObject("token",token);
        //已经和用户绑定正常跳转到首页
        return modelAndView;
    }


    /**
     * 随机生成6位随机验证码
     * 方法说明
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
