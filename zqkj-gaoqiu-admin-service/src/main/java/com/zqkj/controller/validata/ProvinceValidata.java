package com.zqkj.controller.validata;

import java.util.HashMap;
import java.util.Map;

import com.zqkj.entity.ProvinceEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StatusCodeUtil;
import com.zqkj.utils.StringUtil;

public class ProvinceValidata extends BaseValidata<ProvinceEntity> {

	public R save(ProvinceEntity entity) {
		return this.validata(entity);
	}

	public R update(ProvinceEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getId() == null || StringUtil.isEmpty(entity.getGuid()))
			map.put("key", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putData(map);
		return null;
	}
	
	public R info(ProvinceEntity entity) {
		return super.info(entity);
	}
	
	public R del(ProvinceEntity entity) {
		return super.del(entity);
	}

	public R list(ProvinceEntity entity, String orderBy) {
		
		return null;
	}

	public R listByGuids(String[] guids) {
		if(guids == null || guids.length == 0)
			return R.error(Content.STATUS_CODE_5004).put("count", 0);
		return null;
	}
	public R validata(ProvinceEntity entity) {
		Map<String, String> map = new HashMap<String, String>();
		if(entity.getName() == null || "".equals(entity.getName())) {
			map.put("name", StatusCodeUtil.getMsg(Content.STATUS_CODE_5211));
		}
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putData(map);
		return null;
	}
}
