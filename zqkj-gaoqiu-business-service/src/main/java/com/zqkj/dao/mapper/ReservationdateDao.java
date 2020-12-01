package com.zqkj.dao.mapper;

import com.zqkj.entity.ReservationdateEntity;
import com.zqkj.dao.BaseDao;

import java.util.List;

/**
 * 
 * 每天订场设置表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:09:36
 */
public interface ReservationdateDao extends BaseDao<ReservationdateEntity> {
    //根据条件查询订场设置包含时间表信息
    public List<ReservationdateEntity> selectReservationdateList(ReservationdateEntity reservationdateEntity);
}
