package com.zqkj.service;

import com.zqkj.entity.ReservationEntity;

import java.util.List;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 10:49:45
 */
public interface ReservationService extends BaseService<ReservationEntity> {

    public ReservationEntity selectReservationByGuid(String guid);

    //条件查询球场
    public List<ReservationEntity> selectReservationList(ReservationEntity reservationEntity);

    //条件查询返回场地价格最低的List
    public List<ReservationEntity> selectLowestPrice(ReservationEntity reservationEntity);
}
