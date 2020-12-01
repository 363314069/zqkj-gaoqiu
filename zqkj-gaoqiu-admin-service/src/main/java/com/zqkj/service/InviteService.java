package com.zqkj.service;

import com.zqkj.entity.InviteEntity;
import com.zqkj.utils.R;

/**
 * 邀请表
 */
public interface InviteService extends BaseService<InviteEntity> {

    //判断用户是否已经注册，已经注册直接进行绑定
    public R binding(String invitedGuid);
}
