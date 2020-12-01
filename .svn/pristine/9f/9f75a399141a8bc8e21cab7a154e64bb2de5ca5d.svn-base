package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.OrganizationEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class OrganizationValidata extends BaseValidata<OrganizationEntity> {
	/**名称**/
	public static final String FIELD_NAME = "name";
	/**排序**/
	public static final String FIELD_TOKEN = "token";
	/**联系人**/
	public static final String FIELD_CONTACTS = "contacts";
	/**电话**/
	public static final String FIELD_PHONE = "phone";
	
	public static final String NAME_NOT_EMPTY = "名称不能为空！";
	public static final String TOKEN_NOT_EMPTY = "token不能为空!";
	public static final String TOKEN_REGEX = "^[a-zA-Z0-9]+$";
	public static final String TOKEN_VALIDATA = "token必须为字母或数字或。组成!";
	public static final String CONTACTS_NOT_EMPTY = "联系人不能为空!";
	public static final String PHONE_NOT_EMPTY = "联系电话不能为空!";
	public static final String PHONE_VALIDATA = "联系电话必须为11位数字!";
	public static final String PHONE_REGEX = "^1\\d{10}$";
	
	public R info(OrganizationEntity entity) {
		if(entity.getId() == null && StringUtils.isBlank(entity.getGuid()))
			return R.error(Content.STATUS_CODE_5004);
		return null;
	}
	
	public R save(OrganizationEntity entity) {
		return validata(entity);
	}

	public R update(OrganizationEntity entity) {
		return validata(entity);
	}
	
	public R del(OrganizationEntity entity) {
		return super.del(entity);
	}
	
	public R delByGuids(String[] guids) {
		return super.delByGuids(guids);
	}
	
	public R list(OrganizationEntity entity, String orderBy) {
		return null;
	}

	public R page(PageUtil<OrganizationEntity> page, OrganizationEntity entity) {
		return null;
	}

	public R total(OrganizationEntity entity) {
		return null;
	}
	
	public R validata(OrganizationEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getName() == null || "".equals(entity.getName())) {
			map.put(FIELD_NAME, NAME_NOT_EMPTY);
		}
		/*
		if(entity.getToken() == null || "".equals(entity.getToken())) {
			map.put(FIELD_TOKEN, TOKEN_NOT_EMPTY);
		} else {
			if(!entity.getToken().matches(TOKEN_REGEX)) {
				map.put(FIELD_TOKEN, TOKEN_VALIDATA);
			}
		}
		if(entity.getContacts() == null || "".equals(entity.getContacts())) {
			map.put(FIELD_CONTACTS, CONTACTS_NOT_EMPTY);
		}
		if(entity.getPhone() == null || "".equals(entity.getPhone())) {
			map.put(FIELD_PHONE, PHONE_NOT_EMPTY);
		} else {
			if(!entity.getPhone().matches(PHONE_REGEX)) {
				map.put(FIELD_PHONE, PHONE_VALIDATA);
			}
		}*/
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}

	public R listByGuids(String[] guids) {
		if(guids == null || guids.length == 0)
			return R.error(Content.STATUS_CODE_5004).put("count", 0);
		return null;
	}

}
