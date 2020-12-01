package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.ModuleEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;

public class ModuleValidata {
	/**名称**/
	public static final String FIELD_NAME = "name";
	/**排序**/
	public static final String FIELD_SORT = "sort";
	/**id**/
	public static final String FIELD_ID = "id";
	public static final String ID_NOT_EMPTY = "id不能为空！";
	public static final String NAME_NOT_EMPTY = "名称不能为空！";
	public static final String SORT_NOT_EMPTY = "排序不能为空!";
	
	public R info(ModuleEntity entity){
		if(entity.getId() == null && StringUtils.isBlank(entity.getGuid())){
			return R.error(Content.STATUS_CODE_5006);
		}
		return null;
	}
	public R save(ModuleEntity entity) {
		Map<String, String> map = validata(entity);
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}

	public R update(ModuleEntity entity) {
		Map<String, String> map = validata(entity);
		if(entity.getId() == null)
			map.put(FIELD_ID, ID_NOT_EMPTY);
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}

	public R list(ModuleEntity entity, String orderBy) {
		
		return null;
	}
	
	public R saveMove(ModuleEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getId() == null && (entity.getGuid() == null || "".equals(entity.getGuid())))
			map.put(FIELD_ID, ID_NOT_EMPTY);
		if(entity.getSort() == null)
			map.put(FIELD_SORT, SORT_NOT_EMPTY);
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}
	public R listByIndexId(ModuleEntity entity){
		if(entity.getIndexId() == null)
			return R.error(Content.STATUS_CODE_5006);
		return null;
	}
	public Map<String, String> validata(ModuleEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isBlank(entity.getName())) {
			map.put(FIELD_NAME, NAME_NOT_EMPTY);
		}
		if(entity.getSort() == null) {
			map.put(FIELD_SORT, SORT_NOT_EMPTY);
		}
		return map;
	}

}
