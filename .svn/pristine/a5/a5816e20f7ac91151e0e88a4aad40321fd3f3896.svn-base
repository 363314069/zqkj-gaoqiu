package com.zqkj.service.impl;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.entity.CouponsEntity;
import com.zqkj.service.CouponsService;
import com.zqkj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.ActivitycouponsDao;
import com.zqkj.entity.ActivitycouponsEntity;
import com.zqkj.service.ActivitycouponsService;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-21 11:26:44
 */
@Service("activitycouponsService")
public class ActivitycouponsServiceImpl extends BaseServiceImpl<ActivitycouponsDao, ActivitycouponsEntity> implements ActivitycouponsService {

    @Autowired
    private CouponsService couponsService;

    @Override
    public List<CouponsEntity> selectCouponsList(ActivitycouponsEntity entity) {
        List<ActivitycouponsEntity> list = select(entity);
        if(list != null && list.size() > 0){
            List<String> guidList = new ArrayList<String>();
            for(ActivitycouponsEntity activitycoupons : list){
                guidList.add(activitycoupons.getCouponsGuid());
            }
            Example example = new Example(couponsService.getClazz());
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("guid", guidList);
            List<CouponsEntity> listActivity = couponsService.selectByExample(example);
            return listActivity;
        }else{
            return null;
        }
    }
}
