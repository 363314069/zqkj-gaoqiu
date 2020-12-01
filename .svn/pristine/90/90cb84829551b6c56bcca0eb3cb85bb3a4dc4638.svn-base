package com.zqkj.controller.validata;

import com.zqkj.entity.WxBeingPushedEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;


public class WxBeingPushedValidata {

	public R beingPushed(WxBeingPushedEntity wxBeingPushedEntity) {
		if(StringUtil.isEmpty(wxBeingPushedEntity.getTitle())){
			return R.error(Content.STATUS_CODE_5006,"标题为空");
		}
		if(StringUtil.isEmpty(wxBeingPushedEntity.getData())){
			return R.error(Content.STATUS_CODE_5006,"内容为空");
		}
		if(StringUtil.isEmpty(wxBeingPushedEntity.getDate())){
			return R.error(Content.STATUS_CODE_5006,"时间为空");
		}
		if(StringUtil.isEmpty(wxBeingPushedEntity.getProductName())){
			return R.error(Content.STATUS_CODE_5006,"产品名称为空");
		}
		if(StringUtil.isEmpty(wxBeingPushedEntity.getUrl())){
			return R.error(Content.STATUS_CODE_5006,"跳转链接为空");
		}
		return null;
	}
}
