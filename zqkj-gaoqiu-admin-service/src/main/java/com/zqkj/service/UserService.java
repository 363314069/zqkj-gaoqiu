package com.zqkj.service;

import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zqkj.entity.UserEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
public interface UserService extends BaseService<UserEntity> {
	public final static String GUID_ZXKS = "guid-zxks";
	public final static String GUID_ZZK = "guid-zzk";
	public final static int LOGIN_TYPE_DEFAULT = 0;
	public final static int LOGIN_TYPE_IP = 2;
	public final static int LOGIN_TYPE_USER = 3;
	public final static int LOGIN_TYPE_API = 4;
	
	public UserEntity login(UserEntity entity, String ip) throws Exception ;

	public PageUtil<UserEntity> selectBySourceGuid(PageUtil<UserEntity> pageUtil, Map<String, Object> map);

	public PageUtil<UserEntity> selectNotBySourceGuid(PageUtil<UserEntity> pageUtil, Map<String, Object> map);

	public UserEntity ipLogin(String ip);

	/***
	 * 获取openid
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String wxCallback(String code) throws IOException;

	/***
	 * 判断是否关注公众号
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public Integer wxOfficialAccountsInfo(String openid) throws IOException;

	/**
	 * 获取微信授权用户信息
	 * @param openid
	 * @return
	 */
	public UserEntity goHome(String openid);

	/**
	 * 绑定微信用户
	 * @param json
	 * @return
	 */
	public JSONObject bindingWxUser(JSONObject json);


	//查询分页
	public PageUtil<UserEntity> selectListPage(PageUtil<UserEntity> pageUtil, UserEntity record);


	//绑定球场管理员
	public R bindingAdminReservation(String reservationGuid,String userGuid);


	//登录发送短信验证码
	public R authLoginCode(String phone);


	//验证码登录
	public R phoneLogin(String phone,String authCode);
}
