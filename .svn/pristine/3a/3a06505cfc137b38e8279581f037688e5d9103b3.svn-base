package com.zqkj.service.impl;

import com.zqkj.entity.IntroductionEntity;
import com.zqkj.service.IntroductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.ReservationDao;
import com.zqkj.entity.ReservationEntity;
import com.zqkj.service.ReservationService;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 10:49:45
 */
@Service("reservationService")
public class ReservationServiceImpl extends BaseServiceImpl<ReservationDao, ReservationEntity> implements ReservationService {


    @Autowired
    private IntroductionService introductionService;


    @Override
    public ReservationEntity selectReservationByGuid(String guid) {
        return dao.selectReservationByGuid(guid);
    }

    @Override
    public List<ReservationEntity> selectReservationList(ReservationEntity reservationEntity) {
        return dao.selectReservationList(reservationEntity);
    }

    @Override
    public List<ReservationEntity> selectLowestPrice(ReservationEntity reservationEntity) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reservationEntity.setEndTime(format.format(new Date()));
        List<ReservationEntity> reservationList = dao.selectReservationList(reservationEntity);

        Iterator<ReservationEntity> iterator = reservationList.iterator();
        while (iterator.hasNext()) {
            ReservationEntity next = iterator.next();
            List<Integer> prices = new ArrayList<>();
            int existFlag = 0;
            for(IntroductionEntity introductionEntity : next.getIntroductionList()){
                prices.add(introductionEntity.getPrice());
                if(introductionEntity.getState() == 1){
                    existFlag = 1;
                }
            }
            if(existFlag == 1){
                if(prices.size() > 0){
                    next.setPrice(Collections.min(prices));
                }
            }else{
                iterator.remove();
            }
        }
        return reservationList;
    }
}
