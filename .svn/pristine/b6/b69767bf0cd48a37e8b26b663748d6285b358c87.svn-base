package com.zqkj.controller.validata;

import java.util.List;
import java.util.Map;

import com.zqkj.utils.Content;
import com.zqkj.utils.ObjectUtil;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public abstract class BaseValidata<T>{

	public R save(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5005).put("count", 0);
		}
		return null;
	}

	
	public R saveList(List<T> list) {
		if(ObjectUtil.listObjIsNull(list)){
			return R.error(Content.STATUS_CODE_5005).put("count", 0);
		}
		return null;
	}

	
	public R info(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5004);
		}
		return null;
	}

	
	public R update(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5001).put("count", 0);
		}
		return null;
	}

	
	public R del(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		return null;
	}

	
	public R delByIds(Long[] ids) {
		if(ids == null || ids.length == 0){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		return null;
	}

	
	public R delByGuids(String[] guids) {
		if(guids == null || guids.length == 0){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		return null;
	}

	
	public R list(T entity) {
		return null;
	}

	
	public R page(PageUtil<T> page, T entity) {
		return null;
	}

	
	public R total(T entity) {
		return null;
	}

	public R errorMsg(Map<String, String> map){
		if(map.size() > 0)
			return R.error(Content.STATUS_CODE_5006).putData(map);
		return null;
	}
}
