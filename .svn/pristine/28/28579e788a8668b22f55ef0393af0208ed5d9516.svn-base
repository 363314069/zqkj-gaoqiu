package com.zqkj.controller.validata;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.IpEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.ObjectUtil;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class IpValidata	extends BaseValidata<IpEntity>{

	public R info(IpEntity entity) {
		if(ObjectUtil.isIdEmpty(entity) && ObjectUtil.isGuidEmpty(entity)){
			return R.error(Content.STATUS_CODE_5004);
		}
		return null;
	}
	public R save(IpEntity entity) {
		if(StringUtils.isBlank(entity.getAllIp())){
			return R.error(Content.STATUS_CODE_10303);
		}
		return null;
	}
	public R update(IpEntity entity) {
		if(ObjectUtil.isIdEmpty(entity) && ObjectUtil.isGuidEmpty(entity)){
			return R.error(Content.STATUS_CODE_5001).put("count", 0);
		}
		return null;
	}

	public R del(IpEntity entity) {
		if(ObjectUtil.isIdEmpty(entity) && ObjectUtil.isGuidEmpty(entity)){
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
	
	public R page(PageUtil<IpEntity> page, IpEntity entity) {
		
		return null;
	}
}
