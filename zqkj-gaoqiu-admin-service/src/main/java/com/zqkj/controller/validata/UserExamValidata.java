package com.zqkj.controller.validata;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.UserExamEntity;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class UserExamValidata {
	
	public R info(UserExamEntity entity){
		if(entity.getId() == null && StringUtils.isBlank(entity.getGuid())){
			return R.error(Content.STATUS_CODE_5006);
		}
		return null;
	}
	
	public R save(UserExamEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid()) && StringUtils.isEmpty(BaseContentHandler.getOrganizationGuid()))
			return R.error(Content.STATUS_CODE_4001);
		if(entity.getExId() == null)
			return R.error(Content.STATUS_CODE_5006);
		if(entity.getSuId() == null)
			return R.error(Content.STATUS_CODE_5006);
		if(entity.getAnswerStatus() == null)
			return R.error(Content.STATUS_CODE_5006);
		return null;
	}

	public R update(UserExamEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		return null;
	}
	/*
	public R list(ModuleEntity entity, String orderBy) {
		
		return null;
	}
	*/
	public R page(PageUtil<UserExamEntity> page, UserExamEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		entity.setCreator(BaseContentHandler.getUserGuid());
		return null;
	}
	public R listSoId() {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		return null;
	}
	
	public R statistics(UserExamEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		return null;
	}
	public R answerPage(PageUtil<UserExamEntity> page, UserExamEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		return null;
	}
}
