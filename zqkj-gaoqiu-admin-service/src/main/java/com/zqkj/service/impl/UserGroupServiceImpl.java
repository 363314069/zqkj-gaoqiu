package com.zqkj.service.impl;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.UserGroupDao;
import com.zqkj.entity.UserGroupEntity;
import com.zqkj.service.UserGroupService;


/**
 * 用户分组
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Service("usergroupService")
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupDao, UserGroupEntity> implements UserGroupService {

}
