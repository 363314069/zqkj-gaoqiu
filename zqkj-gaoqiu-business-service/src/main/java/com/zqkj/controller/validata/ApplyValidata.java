package com.zqkj.controller.validata;


import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;

public class ApplyValidata {

    public R activityApply(String activityGuid) {
        if(StringUtil.isEmpty(activityGuid)){
            return R.error(Content.STATUS_CODE_5004,"活动GUID为空");
        }
        return null;
    }
}
