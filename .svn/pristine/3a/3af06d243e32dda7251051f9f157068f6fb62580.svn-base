package com.zqkj.dao.mapper;

import java.util.List;
import java.util.Map;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.UserEntity;
import com.zqkj.utils.annotation.SqlFieldIgnore;

/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@SqlFieldIgnore("loginPassword")
public interface UserDao extends BaseDao<UserEntity> {
	public List<UserEntity> selectListBySourceGuid(Map<String, Object> map);
	public List<UserEntity> selectListNotBySourceGuid(Map<String, Object> map);

	//分页查询总数计算
	public int selectListPageCount(UserEntity record);

	//分页查询
	public List<UserEntity> selectListPage(UserEntity record);
}
