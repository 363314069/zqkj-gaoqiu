package com.zqkj.controller.validata;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.entity.WrongQuestionEntity;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

public class WrongQuestionValidata {

	public R page(PageUtil<WrongQuestionEntity> page, WrongQuestionEntity entity) {
		if(StringUtils.isEmpty(BaseContentHandler.getUserGuid())){
			return R.error(Content.STATUS_CODE_4001);
		}
		entity.setCreator(BaseContentHandler.getUserGuid());
		return null;
	}
}
