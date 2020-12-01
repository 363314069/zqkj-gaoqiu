package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zqkj.entity.UserGroupEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class UserGroupValidata {
	
	/**名称**/
	public static final String FIELD_NAME = "name";
	/**排序**/
	public static final String FIELD_SORT = "sort";
	public static final String NAME_NOT_EMPTY = "名称不能为空！";
	public static final String SORT_NOT_EMPTY = "排序不能为空!";

	
	public R save(UserGroupEntity entity) {
		return validata(entity);
	}

	
	public R saveList(List<UserGroupEntity> list) {
		return null;
	}

	
	public R info(UserGroupEntity entity) {
		return null;
	}

	
	public R update(UserGroupEntity entity) {
		return validata(entity);
	}

	
	public R del(UserGroupEntity entity) {
		return null;
	}

	
	public R delByIds(Long[] ids) {
		return null;
	}

	
	public R delByGuids(String[] guids) {
		return null;
	}

	
	public R list(UserGroupEntity entity, String orderBy) {
		return null;
	}

	
	public R page(PageUtil<UserGroupEntity> page, UserGroupEntity entity) {
		return null;
	}

	
	public R total(UserGroupEntity entity) {
		return null;
	}
	
	public R listByGuids(String[] guids){
		return null;
	}
	public R validata(UserGroupEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getName() == null || "".equals(entity.getName())) {
			map.put(FIELD_NAME, NAME_NOT_EMPTY);
		}
		if(entity.getSort() == null) {
			map.put(FIELD_SORT, SORT_NOT_EMPTY);
		}
		
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}
}
