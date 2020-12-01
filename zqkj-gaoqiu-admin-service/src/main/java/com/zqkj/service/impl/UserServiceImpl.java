package com.zqkj.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.zqkj.bean.Constants;
import com.zqkj.bean.SmsBean;
import com.zqkj.service.*;
import com.zqkj.utils.*;
import com.zqkj.utils.jwt.JWTInfo;
import com.zqkj.utils.wx.WeinXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zqkj.dao.mapper.UserDao;
import com.zqkj.entity.IpEntity;
import com.zqkj.entity.OrganizationEntity;
import com.zqkj.entity.RoleEntity;
import com.zqkj.entity.UserEntity;
import com.zqkj.entity.UserGroupEntity;
import com.zqkj.entity.UserMapEntity;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private IpService ipService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserMapService userMapService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private Constants constants;
	@Autowired
	private SmsBean smsBean;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	/**
	 * 保存一个实体，null属性不会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(UserEntity record) {
		record.setLoginPassword(MD5.md5Salt(record.getLoginPassword()));
		record.setUserGroupGuid(GUID_ZZK);
		return super.insertSelective(record);
	}
	
	/**
	 * 根据主键更新所有值
	 */
	@Override
	public int updateByPrimaryKey(UserEntity record) {
		if(StringUtils.isBlank(record.getLoginPassword())){
			record.setLoginPassword(null);
		} else {
			record.setLoginPassword(MD5.md5Salt(record.getLoginPassword()));
		}
		//return super.updateByPrimaryKey(record);
		return super.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(UserEntity record) {
		if(StringUtils.isBlank(record.getLoginPassword())){
			record.setLoginPassword(null);
		} else {
			record.setLoginPassword(MD5.md5Salt(record.getLoginPassword()));
		}
		return super.updateByPrimaryKeySelective(record);
	}
	@Override
	public UserEntity login(UserEntity entity, String ip) throws Exception {
		UserEntity user = null;
			Example example = new Example(UserEntity.class);
			Criteria criteria = example.createCriteria();
			criteria.andNotEqualTo("userGroupGuid", GUID_ZXKS);
			criteria.orIsNull("userGroupGuid");
			example.and().andEqualTo("loginName", entity.getLoginName());
			//PageHelper.startPage(0, 1);
			user = super.selectOneByExample(example);
			try {
				if (user != null && MD5.verify(entity.getLoginPassword(), user.getLoginPassword())) {
					user.setLoginPassword(null);
					user.setLoginType(LOGIN_TYPE_USER);
					setInfo(user);
				} else {
					return null;
				}
			} catch (Exception e) {
			}
		return user;
	}
	
	@Override
	public UserEntity ipLogin(String ip) {
		UserEntity user = new UserEntity();
		user.setLoginIp(ip);
		try {
			IpEntity ipEntity = ipService.selectOneByIp(ip);
			if(ipEntity != null){
				user.setLoginType(LOGIN_TYPE_IP);
				user.setOrganizationGuid(ipEntity.getOrganizationGuid());
			} else {
				user.setLoginType(LOGIN_TYPE_DEFAULT);
			}
			setInfo(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setName(user.getOrganizationName());
		return user;
	}
	
	public UserEntity selectOne(UserEntity entity) {
		entity = super.selectOne(entity);
		if(entity != null){
			entity.setLoginPassword(null);
			setInfo(entity);
		}
		return entity;
	}

	/**
	 * 获取用户分组名
	 * 用户用户机构名
	 * 用户用户角色
	 * @param user
	 */
	private void setInfo(UserEntity user) {
		// 用户分组信息
		if(!StringUtils.isBlank(user.getUserGroupGuid())){
			UserGroupEntity userGroupEntity = userGroupService.selectOneByGuid(user.getUserGroupGuid());
			if (userGroupEntity != null) {
				user.setGroupName(userGroupEntity.getName());
			}
		}
		// 用户机构信息
		if(!StringUtils.isBlank(user.getOrganizationGuid())){
			OrganizationEntity organizationEntity = organizationService.selectOneByGuid(user.getOrganizationGuid());
			if (organizationEntity != null) {
				user.setOrganizationName(organizationEntity.getName());
			}
		}
		List<String> listRole = new ArrayList<String>();
		if(!StringUtils.isBlank(user.getGuid())){
			UserMapEntity userMapEntity = new UserMapEntity();
			userMapEntity.setUserGuid(user.getGuid());
			//userMapEntity.setType(UserMapService.TYPE_USER_ROLE);
			List<UserMapEntity> userRoleList = userMapService.select(userMapEntity);
			if(userRoleList != null ){
				for (UserMapEntity userMapEntity2 : userRoleList) {
					listRole.add(userMapEntity2.getSourceGuid());
				}
			}
		}
		if(user.getLoginType() == LOGIN_TYPE_IP){
			List<RoleEntity> roleList = roleService.getByType(RoleService.IP_TYPE);
			List<String> roleGuids = new ArrayList<String>();
			for (RoleEntity roleEntity : roleList) {
				roleGuids.add(roleEntity.getGuid());
			}
		}
		if(user.getLoginType() == LOGIN_TYPE_DEFAULT){
			List<RoleEntity> roleList = roleService.getByType(RoleService.DEFAULT_TYPE);
			List<String> roleGuids = new ArrayList<String>();
			for (RoleEntity roleEntity : roleList) {
				roleGuids.add(roleEntity.getGuid());
			}
		}
		user.setListRole(listRole);
	}

	@Override
	public PageUtil<UserEntity> selectBySourceGuid(PageUtil<UserEntity> pageUtil, Map<String,Object> map) {
		Page<UserEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
		if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
			page.setOrderBy(pageUtil.getOrderBy());
		}
		dao.selectListBySourceGuid(map);
		pageUtil = new PageUtil<UserEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
		return pageUtil;
	}

	@Override
	public PageUtil<UserEntity> selectNotBySourceGuid(PageUtil<UserEntity> pageUtil, Map<String,Object> map) {
		Page<UserEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
		if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
			page.setOrderBy(pageUtil.getOrderBy());
		}
		dao.selectListNotBySourceGuid(map);
		pageUtil = new PageUtil<UserEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
		return pageUtil;
	}


	@Override
	public String wxCallback(String code) throws IOException {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + constants.getAppid() +
				"&secret=" + constants.getAppsecret() +
				"&code=" + code +
				"&grant_type=authorization_code";
		JSONObject jsonObject = HttpClientUtils.doGetJson(url.trim());

		String access_token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");

		if(StringUtil.isEmpty(openid)){
			return openid;
		}

		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" +access_token+
				"&openid=" + openid+
				"&lang=zh_CN";
		JSONObject userInfo = HttpClientUtils.doGetJson(infoUrl);

		System.out.println(userInfo);
		//第一种情况，不跟数据库关联
		//map.put("userInfo",userInfo);
		//return "/home";

		//第二种情形关联本地用户数据

		/*Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(0);
		typeList.add(1);
		criteria.andIn("type", typeList);
		criteria.andEqualTo("openid", openid);//openid
		UserEntity wxUserEntity = selectOneByExample(example);*/
		UserEntity wxUser = new UserEntity();
		//wxUserQuery.setType(0);
		wxUser.setOpenid(openid);
		UserEntity wxUserEntity = super.selectOne(wxUser);
		if(wxUserEntity == null){
			//UserEntity wxUser = new UserEntity();
			wxUser.setType(0);
			wxUser.setState(1);
			wxUser.setName(userInfo.getString("nickname"));
			wxUser.setImage(userInfo.getString("headimgurl"));
			this.insertSelective(wxUser);
		}else{
			wxUserEntity.setImage(userInfo.getString("headimgurl"));
			this.updateByPrimaryKeySelective(wxUserEntity);
		}
		return openid;
	}

	@Override
	public Integer wxOfficialAccountsInfo(String openid) throws IOException {
		Map<String,String> map = WeinXinUtil.getInstance().getMap(constants.getAppid(),constants.getAppsecret());
		String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+map.get("access_token")+"&openid="+ openid+"&lang=zh_CN";
		JSONObject userInfo = HttpClientUtils.doGetJson(infoUrl);
		if(userInfo.getInteger("subscribe") == null || userInfo.getInteger("subscribe") == 0){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public UserEntity goHome(String openid) {
		/*Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(0);
		typeList.add(1);
		criteria.andIn("type", typeList);
		criteria.andEqualTo("openid", openid);//openid
		UserEntity wxUserEntity = selectOneByExample(example);*/

		UserEntity wxUser = new UserEntity();
		//wxUser.setType(0);
		wxUser.setOpenid(openid);
		UserEntity wxUserEntity = super.selectOne(wxUser);
		return wxUserEntity;
	}

	@Override
	public JSONObject bindingWxUser(JSONObject json) {
		return null;
	}

	@Override
	public PageUtil<UserEntity> selectListPage(PageUtil<UserEntity> pageUtil, UserEntity record) {
		if(pageUtil.getPage() == 0){
			pageUtil.setPage(1);
		}
		record.setLimit(pageUtil.getLimit());
		record.setPage(((pageUtil.getPage()-1)*pageUtil.getLimit()));
		Page<UserEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
		if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
			page.setOrderBy(pageUtil.getOrderBy());
		}
		List<UserEntity> list = dao.selectListPage(record);
		int count = dao.selectListPageCount(record);
		pageUtil = new PageUtil<UserEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
		pageUtil.setCount(count);
		pageUtil.setList(list);
		return pageUtil;
	}

	@Override
	public R bindingAdminReservation(String reservationGuid, String userGuid) {
		UserEntity userEntity = selectOneByGuid(userGuid);
		if(userEntity != null && StringUtil.isEmpty(userEntity.getReservationGuid())){
			//查询这个球场是否已经被绑定，如果被绑定，需要把该用户进行解绑
			UserEntity queryUser = new UserEntity();
			queryUser.setReservationGuid(reservationGuid);
			queryUser = selectOne(queryUser);
			if(queryUser != null){
				queryUser.setReservationGuid("");
				updateByPrimaryKeySelective(queryUser);//进行解绑
			}
			//绑定用户
			userEntity.setReservationGuid(reservationGuid);
			int nRet = updateByPrimaryKeySelective(userEntity);
			if(nRet > 0){
				return R.ok().putData(userEntity);
			}else{
				return R.error(Content.STATUS_CODE_5007,"修改失败！");
			}
		}else if(reservationGuid.equals(userEntity.getReservationGuid())){
			return R.ok().putData(userEntity);
		}else{
			return R.error(Content.STATUS_CODE_5007,"该用户已经被绑定不可重复绑定！");
		}
	}

	@Override
	public R authLoginCode(String phone) {
		try{
			String authCode = WxServiceImpl.createRandomVcode();
			String[] phoneNumbers = {phone};//电话
			smsBean.setTemplateId(790675);// 短信模板 ID，需要在短信应用中申请
			String[] params = {authCode,"10"};//验证码,有效时长
			SmsSingleSender ssender = new SmsSingleSender(smsBean.getAppid(), smsBean.getAppkey());
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
					smsBean.getTemplateId(), params, smsBean.getSmsSign(), "", "");
			System.out.println(result);
			cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject(result.toString());
			if(json.getInt("result") == 0){
				//将验证码存到session中,同时存入创建时间
				//以json存放，这里使用的是阿里的fastjson
				json = new cn.hutool.json.JSONObject();
				json.put("authCode", authCode);
				json.put("createTime", System.currentTimeMillis());
				// 将认证码存入SESSION
				request.getSession().setAttribute(phone, json);
				return R.ok();
			}else{
				return R.error(Content.STATUS_CODE_5324,"发送失败请重试！");
			}
		}catch (Exception e){
			return R.error(Content.STATUS_CODE_5324,"发送失败请重试！");
		}
	}

	@Override
	public R phoneLogin(String phone, String authCode) {
		cn.hutool.json.JSONObject json = (cn.hutool.json.JSONObject)request.getSession().getAttribute(phone);
		if(json == null){
			return R.error(Content.STATUS_CODE_5206,"验证码过期,请重新获取！");
		}
		if(!json.getStr("authCode").equals(authCode)){
			return R.error(Content.STATUS_CODE_4002,"验证码错误,请输入正确验证码！");
		}
		if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 10){
			//清除当前session
			request.getSession().removeAttribute(phone);
			return R.error(Content.STATUS_CODE_5206,"验证码过期,请重新获取！");
		}
		UserEntity entity = new UserEntity();
		entity.setPhone(phone);
		List<UserEntity> selectUser = select(entity);
		try {
			//清除当前session
			request.getSession().removeAttribute(phone);
			if(selectUser.size() <= 0){
				//没有查询到，进行插入
				entity.setName(phone);
				entity.setType(0);
				this.insertSelective(entity);
				return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN,getToken(entity));
			}else{
				entity = selectUser.get(0);
				String token = getToken(entity);
				return R.ok().put(Content.CONTEXT_KEY_USER_TOKEN,token);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(Content.STATUS_CODE_5006,"登录失败,请重新获取验证码！");
		}
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
}
