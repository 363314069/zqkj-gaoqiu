package com.zqkj.dao.mapper;

import com.zqkj.entity.UserExtendEntity;
import com.zqkj.dao.BaseDao;

import java.util.List;

/**
 * 
 * 用户扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
public interface UserExtendDao extends BaseDao<UserExtendEntity> {
    //条件查询返回所有字段
    public List<UserExtendEntity> selectList(UserExtendEntity extendEntity);
}
