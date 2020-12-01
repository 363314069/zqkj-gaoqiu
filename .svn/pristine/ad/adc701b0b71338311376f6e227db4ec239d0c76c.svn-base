package com.zqkj.service.impl;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.CouponsdateteatimeDao;
import com.zqkj.entity.CouponsdateteatimeEntity;
import com.zqkj.service.CouponsdateteatimeService;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-17 17:31:53
 */
@Service("couponsdateteatimeService")
public class CouponsdateteatimeServiceImpl extends BaseServiceImpl<CouponsdateteatimeDao, CouponsdateteatimeEntity> implements CouponsdateteatimeService {

    @Override
    public List<CouponsdateteatimeEntity> updateCouponsdateteatimeList(List<CouponsdateteatimeEntity> entities) {
        List<CouponsdateteatimeEntity> list = new ArrayList<>();
        for(CouponsdateteatimeEntity reservationdate : entities){
            if(reservationdate.getId() != null){
                int updateInt = updateByPrimaryKeySelective(reservationdate);
                if(updateInt <= 0){
                    list.add(reservationdate);
                }
            }else{
                int insertInt = insertSelective(reservationdate);
                if(insertInt <= 0){
                    list.add(reservationdate);
                }
            }
        }
        return list;
    }
}
