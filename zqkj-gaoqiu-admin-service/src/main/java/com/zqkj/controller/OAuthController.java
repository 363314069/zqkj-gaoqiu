package com.zqkj.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zqkj.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.UserEntity;
import com.zqkj.service.UserService;
import com.zqkj.service.impl.UserServiceImpl;
import com.zqkj.utils.annotation.SysLog;
import com.zqkj.utils.ip.IPUtil;
import com.zqkj.utils.jwt.JWTInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Controller
@RequestMapping("/security/oauth")
@Api(value = "用户登录", tags = { "/security/oauth 用户登录" })
public class OAuthController{
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "/img", method = RequestMethod.GET)
	@ApiOperation(value = "获取code", notes = "")
	public void captcha() {
		//String path = request.getContextPath();
		//System.err.println(request.getHeader("HostUrl"));
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//System.err.println("--->" + basePath);
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		BufferedImage bi = CodeUtil.getCodeImg();
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (IOException e) {
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}	
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", notes = "参数为json对像")
	@SysLog("用户登录")
	public R login(UserEntity entity) {
		String ip = IPUtil.getIpAddr(request);
		UserEntity loginUser = null;
		try {
			loginUser = userService.login(entity, ip);
		} catch (Exception e1) {
			return R.error(Content.STATUS_CODE_4006, e1.getMessage());
		}
		if(loginUser == null){
			return R.error(Content.STATUS_CODE_5307);
		}
		String token = null;
		try {
			token = getToken(loginUser);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}


	@ResponseBody
	@RequestMapping(value = "/loginguid", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录guid", notes = "参数为json对像")
	@SysLog("用户登录guid")
	public R loginGuid(String guid) {
		UserEntity loginUser = userService.selectOneByGuid(guid);
		if(loginUser == null)
			return R.error(Content.STATUS_CODE_5004);

		JWTInfo jwtInfo = new JWTInfo();
		jwtInfo.setAppType(loginUser.getAppId() == null ? JWTInfo.APP_TYPE_WEB : loginUser.getAppId());
		jwtInfo.setId(loginUser.getId());
		jwtInfo.setGuid(loginUser.getGuid());
		jwtInfo.setUserName(loginUser.getName());
		jwtInfo.setLoginIp(IPUtil.getIpAddr(request));
		jwtInfo.setOpenid(loginUser.getOpenid());
		jwtInfo.setOrganizationGuid(loginUser.getOrganizationGuid());
		String token = null;
		try {
			token = jwtUtil.toJwt(jwtInfo);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5004);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}


	
	@ResponseBody
	@RequestMapping(value = "/iplogin", method = {RequestMethod.POST, RequestMethod.GET})
	@ApiOperation(value = "用户登录", notes = "参数为json对像")
	@SysLog("用户登录")
	public R iplogin() {
		String ip = IPUtil.getIpAddr(request);
		UserEntity entity = userService.ipLogin(ip);
		if(entity == null){
			return R.error(Content.STATUS_CODE_4201).putData(ip);
		}
		String token = null;
		try {
			token = getToken(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}
	
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	@ApiOperation(value = "用户token", notes = "参数为json对像")
	@SysLog("用户token")
	public R token( UserEntity entity) {
		String ip = IPUtil.getIpAddr(request);
		UserEntity loginUser;
		try {
			loginUser = userService.login(entity, ip);
		} catch (Exception e1) {
			return R.error(Content.STATUS_CODE_4006, e1.getMessage());
		}
		if(loginUser == null)
			return R.error(Content.STATUS_CODE_5307);
		String token = null;
		try {
			token = getToken(loginUser);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	@ApiOperation(value = "刷新token", notes = "参数为json对像")
	@SysLog("刷新token")
	public R refresh() {
		String token = request.getHeader(Content.CONTEXT_KEY_USER_TOKEN);
		JWTInfo jwtInfo = null;
		try {
			jwtInfo = jwtUtil.fromJwt(token, JWTInfo.class);
		} catch (Exception e) {
		}
		if(ObjectUtil.isAllNull(jwtInfo) || StringUtils.isBlank(jwtInfo.getLoginName())){
			return R.error(Content.STATUS_CODE_4003);
		}
		UserEntity entity = new UserEntity();
		entity.setLoginName(jwtInfo.getLoginName());
		entity = userService.selectOne(entity);
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_4003);
		}
		if(!token.equals(entity.getToken())){
			//return R.error(Content.STATUS_CODE_4003);
		}
		entity.setLoginIp(IPUtil.getIpAddr(request));
		try {
			token = getToken(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_4003);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}
	
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	@ApiOperation(value = "根具token获取用户信息", notes = "参数为json对像")
	@SysLog("根具token获取用户信息")
	public R info() {
		String token = request.getHeader(Content.CONTEXT_KEY_USER_TOKEN);
		JWTInfo jwtInfo = null;
		try {
			jwtInfo = jwtUtil.fromJwt(token, JWTInfo.class);
		} catch (Exception e) {
		}

		if(ObjectUtil.isAllNull(jwtInfo)){
			jwtInfo = new JWTInfo();
			jwtInfo.setLoginIp(IPUtil.getIpAddr(request));
			jwtInfo.setType(UserServiceImpl.LOGIN_TYPE_IP);
			return R.ok().putData(jwtInfo);
		}
		if(StringUtils.isEmpty(jwtInfo.getLoginName())){
			if(jwtInfo.getType() == UserServiceImpl.LOGIN_TYPE_IP){
				return R.ok().putData(jwtInfo);
			} 
			if(jwtInfo.getType() == UserServiceImpl.LOGIN_TYPE_DEFAULT){
				return R.ok().putData(jwtInfo);
			} 
			return R.error(Content.STATUS_CODE_4003);
		}
		UserEntity entity = new UserEntity();
		entity.setLoginName(jwtInfo.getLoginName());
		entity.setUserGroupGuid(jwtInfo.getReGuid());
		entity = userService.selectOne(entity);
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_4003);
		}
		if(!token.equals(entity.getToken())){
			//return R.error(Content.STATUS_CODE_4003);
		}
		entity.setLoginIp(jwtInfo.getLoginIp());
		return R.ok().putData(getUserInfo(entity));
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "根具token获取用户信息", notes = "参数为json对像")
	@SysLog("根具token获取用户信息")
	public R save(UserEntity entity){
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", notes = "参数为json对像")
	@SysLog("用户登录")
	public R reg(UserEntity entity) {
		entity.setLoginName(entity.getLoginName().trim());
		entity.setLoginPassword(entity.getLoginPassword().trim());
		entity.setMail(entity.getMail().trim());
		
		UserEntity entity1 = new UserEntity();
		entity1.setLoginName(entity.getLoginName());
		int count = userService.selectCount(entity1);
		if(count > 0){
			return R.error(Content.STATUS_CODE_5304);
		}
		
		if(StringUtils.isBlank(entity.getName())){
			entity.setName(entity.getLoginName());
		}
		entity.setType(0);
		userService.insertSelective(entity);
		UserEntity loginUser = userService.selectOneByGuid(entity.getGuid());
		if(loginUser == null){
			return R.error("注册成功，请登录！");
		}
		loginUser.setLoginIp(IPUtil.getIpAddr(request));
		String token = null;
		try {
			token = getToken(loginUser);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}

	public UserEntity getUserInfo(UserEntity entity){
		UserEntity userEntity = new UserEntity();
		userEntity.setId(entity.getId());
		userEntity.setGuid(entity.getGuid());
		userEntity.setLoginName(entity.getLoginName());
		userEntity.setName(entity.getName());
		userEntity.setLoginIp(entity.getLoginIp());
		userEntity.setOrganizationGuid(entity.getOrganizationGuid());
		userEntity.setListRole(entity.getListRole());
		userEntity.setUserGroupGuid(entity.getUserGroupGuid());
		userEntity.setOpenid(entity.getOpenid());
		userEntity.setReservationGuid(entity.getReservationGuid());
		return userEntity;
	}
	
	public String getToken(UserEntity entity) throws Exception{
		JWTInfo jwtInfo = new JWTInfo();
		jwtInfo.setAppType(entity.getAppId() == null ? JWTInfo.APP_TYPE_WEB : entity.getAppId());
		jwtInfo.setId(entity.getId());
		jwtInfo.setType(entity.getLoginType());
		jwtInfo.setGuid(entity.getGuid());
		jwtInfo.setLoginName(entity.getLoginName());
		jwtInfo.setUserName(entity.getName());
		jwtInfo.setLoginIp(entity.getLoginIp());
		jwtInfo.setOrganizationGuid(entity.getOrganizationGuid());
		jwtInfo.setListRole(entity.getListRole());
		jwtInfo.setReGuid(entity.getUserGroupGuid());
		jwtInfo.setOpenid(entity.getOpenid());
		String token = jwtUtil.toJwt(jwtInfo);
		return token;
	}


	@ResponseBody
	@RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
	@ApiOperation(value = "客户端登录", notes = "参数为json对像")
	@SysLog("客户端登录")
	public R adminLogin(UserEntity entity) {
		String ip = IPUtil.getIpAddr(request);
		UserEntity loginUser = null;
		try {
			loginUser = userService.login(entity, ip);
		} catch (Exception e1) {
			return R.error(Content.STATUS_CODE_4006, e1.getMessage());
		}
		if(loginUser == null){
			return R.error(Content.STATUS_CODE_5307,"用户名或密码输入有误");
		}
		String token = null;
		try {
			token = getToken(loginUser);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307,"用户名或密码输入有误");
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}



	@ResponseBody
	@RequestMapping(value = "/memberlogin", method = RequestMethod.POST)
	@ApiOperation(value = "会员管理登录", notes = "参数为json对像")
	@SysLog("会员管理登录")
	public R memberLogin(UserEntity entity) {
		String ip = IPUtil.getIpAddr(request);
		UserEntity loginUser = null;
		Map<String,Boolean> map = new HashMap<>();
		map.put("57731d01-6057-42f6-a732-0f7dffea3b4d",true);//admin
		map.put("6123bf57-00bc-4ebf-94d5-97a661cabc67",true);//晖哥
		map.put("44a3695d-3afa-4418-bb3b-e81179325411",true);//泠姐
		map.put("4f8d06cd-0824-418f-bd40-73230dea004b",true);//燕子
		try {
			loginUser = userService.login(entity, ip);
		} catch (Exception e1) {
			return R.error(Content.STATUS_CODE_4006, e1.getMessage());
		}
		if(loginUser == null){
			return R.error(Content.STATUS_CODE_5307);
		}
		Boolean flag = map.get(loginUser.getGuid());
		if(flag == null){
			return R.error(Content.STATUS_CODE_4005,"您没有权限登录该应用！");
		}
		String token = null;
		try {
			token = getToken(loginUser);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN, token);
	}


	@RequestMapping(value = "/authlogincode", method = RequestMethod.POST)
	@ApiOperation(value = "登录发送短信验证码")
	@ResponseBody
	public R authLoginCode(String phone){
		return userService.authLoginCode(phone);
	}


	@RequestMapping(value = "/phonelogin", method = RequestMethod.POST)
	@ApiOperation(value = "验证码验证并登录")
	@ResponseBody
	public R phoneLogin(String phone,String authCode){
		return userService.phoneLogin(phone,authCode);
	}

}
