package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.RoleEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class RoleValidata {

	
	public R save(RoleEntity entity) {
		
		return validata(entity);
	}

	
	public R saveList(List<RoleEntity> list) {
		
		return null;
	}

	
	public R info(RoleEntity entity) {
		
		return null;
	}

	
	public R update(RoleEntity entity) {
		return validata(entity);
	}

	
	public R del(RoleEntity entity) {
		
		return null;
	}

	
	public R delByIds(Long[] ids) {
		
		return null;
	}

	
	public R delByGuids(String[] guids) {
		
		return null;
	}

	
	public R list(RoleEntity entity, String orderBy) {
		
		return null;
	}

	
	public R page(PageUtil<RoleEntity> page, RoleEntity entity) {
		
		return null;
	}

	
	public R total(RoleEntity entity) {
		
		return null;
	}

	public R validata(RoleEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isBlank(entity.getName())) {
			map.put("name", "名称不能为空");
		}
		if(map.size() > 0)
			return R.error("数据验证错误").putData(map);
		return null;
	}
}
