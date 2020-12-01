package com.zqkj.dao.mapper;

import java.util.List;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.UserExamEntity;

/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-25 15:50:28
 */
public interface UserExamDao extends BaseDao<UserExamEntity> {
	public List<UserExamEntity> selectSoId(UserExamEntity entity);
	public UserExamEntity statistics(UserExamEntity entity);
}
