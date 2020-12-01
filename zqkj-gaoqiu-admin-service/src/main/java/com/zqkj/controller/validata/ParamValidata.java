package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zqkj.entity.ParamEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StatusCodeUtil;
import com.zqkj.utils.StringUtil;

public class ParamValidata {
	/**名称**/
	public static final String FIELD_NAME = "name";
	/**排序**/
	public static final String FIELD_SORT = "sort";
	/**id**/
	public static final String FIELD_ID = "id";
	/**count**/
	public static final String FIELD_COUNT = "count";
	public static final String ID_NOT_EMPTY = "id不能为空！";
	public static final String NAME_NOT_EMPTY = "名称不能为空！";
	public static final String SORT_NOT_EMPTY = "排序不能为空!";

	public R save(ParamEntity entity) {
		return this.validata(entity);
	}

	public R saveList(List<ParamEntity> list) {
		
		return null;
	}

	public R info(ParamEntity entity) {
		
		return null;
	}

	public R update(ParamEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getId() == null || StringUtil.isEmpty(entity.getGuid()))
			map.put(FIELD_ID, StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}

	public R del(ParamEntity entity) {
		
		return null;
	}

	public R delByIds(Long[] ids) {
		
		return null;
	}

	public R delByGuids(String[] guids) {
		
		return null;
	}

	public R list(ParamEntity entity, String orderBy) {
		
		return null;
	}

	public R page(PageUtil<ParamEntity> page, ParamEntity entity) {
		
		return null;
	}

	public R total(ParamEntity entity) {
		
		return null;
	}
	public R listByGuids(String[] guids) {
		if(guids == null || guids.length == 0)
			return R.error(Content.STATUS_CODE_5004).put(FIELD_COUNT, 0);
		return null;
	}
	public R validata(ParamEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getName() == null || "".equals(entity.getName())) {
			map.put(FIELD_NAME, StatusCodeUtil.getMsg(Content.STATUS_CODE_5211));
		}
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putError(map);
		return null;
	}
}
