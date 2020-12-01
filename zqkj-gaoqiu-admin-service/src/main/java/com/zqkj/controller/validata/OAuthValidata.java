package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.zqkj.entity.UserEntity;
import com.zqkj.utils.CodeUtil;
import com.zqkj.utils.Content;
import com.zqkj.utils.DESUtil;
import com.zqkj.utils.GsonUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StatusCodeUtil;
import com.zqkj.utils.StringUtil;

public class OAuthValidata{
	public final static String KEY = "6A010292E2930C29A8A6509AEAE3A7B7";
	
	public R captcha(){
		return null;
	}
	
	public R login(UserEntity entity){
		Map<String, String> map = new HashMap<String, String>();
		try {
			dataDes(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		} 
		if(entity.getLoginName() == null || "".equals(entity.getLoginName())) {
			map.put("loginName", "登录帐号不能为空");
		}
		if(entity.getLoginPassword() == null || "".equals(entity.getLoginPassword())) {
			map.put("loginPassword", "登录密码不能为空");
		}
		if(entity.getCode() == null || "".equals(entity.getCode())) {
			map.put("code", "验证不能为空");
		} else if(!CodeUtil.verify(entity.getCode())){
			map.put("code", "验证码错误");
		}
		if(map.size() > 0)
			return R.error("参数错误").putData(map);
		return null;
	}
	public R iplogin() {
		return null;
	}
	public R token( UserEntity entity){
		Map<String, String> map = new HashMap<String, String>();
		try {
			dataDes(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		} 
		if(entity.getLoginName() == null || "".equals(entity.getLoginName())) {
			map.put("loginName", "登录帐号不能为空");
		}
		if(entity.getLoginPassword() == null || "".equals(entity.getLoginPassword())) {
			map.put("loginPassword", "登录密码不能为空");
		}
		if(map.size() > 0)
			return R.error("参数错误").putData(map);
		return null;
	}
	
	public R reg(UserEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			dataDes(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5006);
		} 
		if(StringUtils.isBlank(entity.getLoginName())) {
			map.put("loginName", StatusCodeUtil.getMsg(Content.STATUS_CODE_10101));
		} else if(!entity.getLoginName().matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{5,15}$")){
			map.put("loginName", StatusCodeUtil.getMsg(Content.STATUS_CODE_10102));
		}
		if(StringUtils.isBlank(entity.getLoginPassword()) || StringUtils.isBlank(entity.getLoginPasswordTwo())) {
			map.put("loginPassword",  StatusCodeUtil.getMsg(Content.STATUS_CODE_10103));
		} else if(!entity.getLoginPassword().equals(entity.getLoginPasswordTwo())){
			map.put("loginPassword",  StatusCodeUtil.getMsg(Content.STATUS_CODE_5301));
		}
		if(StringUtils.isBlank(entity.getMail())) {
			map.put("mail",  "邮箱不能为空！");
		} else if(!entity.getMail().matches("^[^@]+@[^@\\.]+\\.[^@]+$")){
			map.put("mail",  "请输入正确的邮箱格式，如xxx@163.com");
		}
		if(StringUtils.isBlank(entity.getPhone()) || !StringUtil.isPhone(entity.getPhone())) {
			map.put("phone",  "请输入正确的手机号！");
		}
		if(map.size() > 0)
			return R.error("提交信息不全").putData(map);
		return null;
	}
	public R refresh(){
		return null;
	}
	
	public R info(){
		return null;
	}
	
	public String toYFCousult(){
		return null;
	}
	
	public void dataDes(UserEntity entity) throws Exception{
		if(!StringUtils.isBlank(entity.getDatades())){
			try {
				DESUtil des = DESUtil.getInstance(KEY);
				String json = des.Decryptor(entity.getDatades());
				UserEntity source = GsonUtil.fromJson(json, UserEntity.class);
				BeanUtils.copyProperties(source, entity);
			} catch (Exception e) {
				throw e;
			} 
		}
	}
	
	public String toSchoolTain(){
		return null;
	}


	public R adminLogin(UserEntity entity){
		Map<String, String> map = new HashMap<String, String>();
		try {
			dataDes(entity);
		} catch (Exception e) {
			return R.error(Content.STATUS_CODE_5307);
		}
		if(entity.getLoginName() == null || "".equals(entity.getLoginName())) {
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"登录帐号不能为空");
		}
		if(entity.getLoginPassword() == null || "".equals(entity.getLoginPassword())) {
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"登录密码不能为空");
		}
		return null;
	}

	public R memberLogin(UserEntity entity) {
		if(entity.getLoginName() == null || "".equals(entity.getLoginName())) {
			return R.error(Content.STATUS_CODE_5006,"登录帐号不能为空");
		}
		if(entity.getLoginPassword() == null || "".equals(entity.getLoginPassword())) {
			return R.error(Content.STATUS_CODE_5006,"登录密码不能为空");
		}
		return null;
	}


	public R authLoginCode(String phone){
		if(StringUtil.isEmpty(phone)){
			return R.error(Content.STATUS_CODE_5006,"手机号为空！");
		}
		return null;
	}


	public R phoneLogin(String phone,String authCode){
		if(StringUtil.isEmpty(phone)){
			return R.error(Content.STATUS_CODE_5006,"手机号为空！");
		}
		if(StringUtil.isEmpty(authCode)){
			return R.error(Content.STATUS_CODE_5006,"验证码为空！");
		}
		return null;
	}
}
