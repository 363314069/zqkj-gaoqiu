package com.zqkj.controller.validata;

import com.zqkj.entity.InviteEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;

public class InviteValidata extends BaseValidata<InviteEntity> {

    public R binding(String invitedGuid){
        if(StringUtil.isEmpty(invitedGuid)){
            return R.error(Content.STATUS_CODE_5004,"邀请人GUID为空！");
        }
        return null;
    }

    public R selectInviter(@RequestBody String userGuid){
        if(StringUtil.isEmpty(userGuid)){
            return R.error(Content.STATUS_CODE_5004,"用户GUID为空！");
        }
        return null;
    }

    public R list(InviteEntity entity, String orderBy){
        return null;
    }
}
