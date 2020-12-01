package com.zqkj.controller.validata;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WxValidata {
	
	public R wxLogin() {
		return null;
	}


	public R wxCallback(String code){
		return null;
	}

	public R goHome(String openid){
		if(StringUtil.isEmpty(openid)){
			return R.error(Content.STATUS_CODE_5006,"openid为空");
		}
		return null;
	}

	public R authCode(String phone){
		if(StringUtil.isEmpty(phone)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"电话号码为空！");
		}else{
			Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
			Matcher matcher = regex.matcher(phone);
			if(matcher.matches()){
				return null;
			}else{
				return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"电话号码不正确！");
			}
		}
	}

	public R register(String name,String phone,String chad,String authCode) {
		if(StringUtil.isEmpty(name)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"姓名为空！");
		}else if(StringUtil.isEmpty(phone)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"电话为空！");
		}else if(StringUtil.isEmpty(chad)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"差点为空！");
		}else if(StringUtil.isEmpty(authCode)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"验证码为空！");
		}
		return null;
	}

	public R share(String strUrl) {
		if(StringUtil.isEmpty(strUrl)){
			return R.error(Content.STATUS_CODE_5006,"分享链接为空！");
		}
		return null;
	}

	public R getAccessToken() {
		return null;
	}

	public R inviteCode(String codeUrl) {
		if(StringUtil.isEmpty(codeUrl)){
			return R.error(Content.STATUS_CODE_5004,"生成链接地址不能为空！");
		}
		return null;
	}


	public R acceptRegister(String name,String phone,String chad,String authCode,String inviterGuid) {
		if(StringUtil.isEmpty(name)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"姓名为空！");
		}else if(StringUtil.isEmpty(phone)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"电话为空！");
		}else if(StringUtil.isEmpty(chad)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"差点为空！");
		}else if(StringUtil.isEmpty(authCode)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"验证码为空！");
		}else if(StringUtil.isEmpty(inviterGuid)){
			return R.error(Content.STATUS_CODE_PARAM_ERROR_CODE,"邀请人GUID为空！");
		}
		return null;
	}

	public String orgTimeBind(String userGuid,Integer state){
		return null;
	}

	public ModelAndView wxLoginBindCallback(String code) {
		return null;
	}

	public ModelAndView orgBindCallback(String code, String state) {
		return null;
	}
}
