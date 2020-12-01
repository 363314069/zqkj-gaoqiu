package com.zqkj.service;

import com.zqkj.entity.UserMapEntity;

/**
 * 用户角色映射表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-11-13 14:59:25
 */
public interface UserMapService extends BaseService<UserMapEntity> {
	/**1表式用户角色**/
	public static final int TYPE_USER_ROLE = 1;
	/**2表式用户分组**/
	public static final int TYPE_USER_GROUP = 2;
	
	public int delByUserGuids(String[] userGuids, String sourceGuid);

	public int insertList(String[] userGuids, String sourceGuid);
}
