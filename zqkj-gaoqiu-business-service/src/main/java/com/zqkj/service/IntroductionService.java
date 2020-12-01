package com.zqkj.service;

import com.zqkj.entity.IntroductionEntity;
import com.zqkj.utils.PageUtil;

import java.util.List;

/**
 * 
 * 场地表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:02:40
 */
public interface IntroductionService extends BaseService<IntroductionEntity> {

    //条件查询场地信息
    public List<IntroductionEntity> selectIntroductionList(IntroductionEntity introductionEntity);

    //插入场地表信息
    public int saveIntroduction(IntroductionEntity introductionEntity);

    //修改场地表信息
    public int updateIntroduction(IntroductionEntity introductionEntity);

    //查询分页场地订单
    public PageUtil<IntroductionEntity> selectListPage(PageUtil<IntroductionEntity> pageUtil, IntroductionEntity record);
}
