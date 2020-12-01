package com.zqkj.controller.validata;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;

public class UserMapValidata {

	private static final String USER_GUIDS_IS_BLANK = "用户GUID不能为空!";
	private static final String SOURCE_GUIDS_IS_BLANK = "原GUID不能为空!";

	public R saveList(String[] userGuids, String sourceGuid) {
		if(StringUtils.isAllBlank(userGuids)){
			return R.error(Content.STATUS_CODE_5006).put("userGuids", USER_GUIDS_IS_BLANK);
		}
		
		if(StringUtils.isBlank(sourceGuid)){
			return R.error(Content.STATUS_CODE_5006).put("userGuids", SOURCE_GUIDS_IS_BLANK);
		}
		return null;
	}
	
	public R delByUserGuids(String[] userGuids, String sourceGuid){
		if(StringUtils.isAllBlank(userGuids)){
			return R.error(Content.STATUS_CODE_5006).put("userGuids", USER_GUIDS_IS_BLANK);
		}
		if(StringUtils.isBlank(sourceGuid)){
			return R.error(Content.STATUS_CODE_5006).put("userGuids", SOURCE_GUIDS_IS_BLANK);
		}
		return null;
	}
}
