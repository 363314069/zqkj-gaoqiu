package com.zqkj.service;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;

import java.util.List;

/**
 * 活动表
 */
public interface ActivityService extends BaseService<ActivityEntity> {

    //发布产品
    public Integer saveActivity(ActivityEntity entity);

    //修改产品
    public Integer updateActivity(ActivityEntity entity);

    //条件查询列表
    public List<ActivityEntity> selectList(ActivityEntity entity);


    //抢购回调通知
    public R notificationCallback(String activityGuid,String userGuid,String orderNumber,String serialNumber,Integer sum);

    //活动回调通知
    public R notificaActivityCallback(String activityGuid,String userGuid,String orderNumber,String serialNumber,Integer sum, String orderGuid);

    //会员产品回调通知
    public R notificaMemberAreaCallback(String activityGuid,String userGuid,String orderNumber,String serialNumber,Integer sum);

    //查询分页场地订单
    public PageUtil<ActivityEntity> selectListPage(PageUtil<ActivityEntity> pageUtil, ActivityEntity record);

    //查询团购结束的数据
    public List<ActivityEntity> selectGroupBuyingList(ActivityEntity entity);

    //酒店回调通知
    public R hotelCallback(String orderGuid,String creator);
}
