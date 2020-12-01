package com.zqkj.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.RoleDao;
import com.zqkj.entity.RoleEntity;
import com.zqkj.service.RoleService;


/**
 * 角色表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, RoleEntity> implements RoleService {

	public List<RoleEntity> getDefault(){
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setType(DEFAULT_TYPE);
		roleEntity.setState(1);
		return super.select(roleEntity);
	}
	
	public List<RoleEntity> getByType(int type){
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setType(type);
		roleEntity.setState(1);
		return super.select(roleEntity);
	}
}
