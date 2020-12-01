package com.zqkj.dao.mapper;

import com.zqkj.dao.BaseDao;
import com.zqkj.entity.AreaEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地区表
 */
public interface AreaDao extends BaseDao<AreaEntity> {
    //三级联动查询
    List<AreaEntity> threeLevelLinkage(@Param("code")Integer code);
}
