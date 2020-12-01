package com.zqkj.service;

import java.util.List;

import com.zqkj.entity.RoleEntity;

/**
 * 角色表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
public interface RoleService extends BaseService<RoleEntity> {
	public final static int DEFAULT_TYPE = -1;
	public final static int IP_TYPE = 1;
	public final static int LOGIN_TYPE = 0;
	
	public List<RoleEntity> getDefault();
	public List<RoleEntity> getByType(int type);
}
