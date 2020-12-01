package com.zqkj.dao.mapper;

import com.zqkj.entity.IntroductionEntity;
import com.zqkj.dao.BaseDao;

import java.util.List;

/**
 * 
 * 场地信息表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:02:40
 */
public interface IntroductionDao extends BaseDao<IntroductionEntity> {

    //根据订场guid查询场地信息
	public List<IntroductionEntity> selectByReservationGuid(String reservationGuid);


	//根据条件查询场地信息
    public List<IntroductionEntity> selectIntroductionList(IntroductionEntity introductionEntity);


    //分页查询总数计算
    public int selectListPageCount(IntroductionEntity record);

    //分页查询
    public List<IntroductionEntity> selectListPage(IntroductionEntity record);
}
