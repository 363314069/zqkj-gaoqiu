package com.zqkj.service;

import com.zqkj.entity.TeatimeEntity;
import com.zqkj.entity.TeatimeGroupByEntity;
import com.zqkj.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:14:20
 */
public interface TeatimeService extends BaseService<TeatimeEntity> {
    //订场（没有设定时间），场地单独回调
    public R introductionCallback(TeatimeEntity teatimeEntity);

    //条件查询时间表
    public List<TeatimeEntity> selectList(TeatimeEntity teatimeEntity);

    //手机端开启关闭时间段功能
    public R setUpDateTime(String map,String introductionGuid,Integer type);


    //分组查询
    public List<TeatimeGroupByEntity> selectDateTimeGroupBy(String startDate,String endDate,String introductionGuid,Integer type);
}
