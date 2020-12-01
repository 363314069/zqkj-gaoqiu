package com.zqkj.service.impl;

import com.zqkj.entity.CouponsactmapEntity;
import com.zqkj.entity.IntroductionEntity;
import com.zqkj.service.CouponsactmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.CouponsteatimeDao;
import com.zqkj.entity.CouponsteatimeEntity;
import com.zqkj.service.CouponsteatimeService;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-17 17:31:53
 */
@Service("couponsteatimeService")
public class CouponsteatimeServiceImpl extends BaseServiceImpl<CouponsteatimeDao, CouponsteatimeEntity> implements CouponsteatimeService {

    @Autowired
    private CouponsactmapService couponsactmapService;


    @Override
    public int saveCouponsteatime(CouponsteatimeEntity entity) {
        int nRet = dao.insertSelective(entity);
        if(nRet > 0){
            //批量保存商品和优惠券关联
            List<CouponsactmapEntity> couponsactmapList = new ArrayList<>();
            for(String ss : entity.getPreferentialList()){
                CouponsactmapEntity couponsactmapEntity = new CouponsactmapEntity();
                couponsactmapEntity.setCouponsGuid(ss);
                couponsactmapEntity.setActivityGuid(entity.getGuid());
                couponsactmapEntity.setType(1);
                couponsactmapEntity.setState(1);
                couponsactmapList.add(couponsactmapEntity);
            }
            if(couponsactmapList != null && couponsactmapList.size() > 0){
                couponsactmapService.insertList(couponsactmapList);
            }
        }
        return nRet;
    }

    @Override
    public int updateCouponsteatime(CouponsteatimeEntity entity) {
        int nRet = dao.updateByPrimaryKeySelective(entity);
        if(nRet > 0){
            //删除商品优惠券关联表的该商品所有优惠券
            CouponsactmapEntity couponsactmapDel = new CouponsactmapEntity();
            couponsactmapDel.setActivityGuid(entity.getGuid());
            int intDel = couponsactmapService.delete(couponsactmapDel);

            //重新插入选择的优惠券
            List<CouponsactmapEntity> insertList = new ArrayList<>();
            for(String couponsGuid : entity.getPreferentialList()){
                CouponsactmapEntity couponsactmapInsert = new CouponsactmapEntity();
                couponsactmapInsert.setType(1);
                couponsactmapInsert.setState(1);
                couponsactmapInsert.setActivityGuid(entity.getGuid());
                couponsactmapInsert.setCouponsGuid(couponsGuid);
                insertList.add(couponsactmapInsert);
            }
            if(insertList != null && insertList.size() > 0){
                couponsactmapService.insertList(insertList);
            }
        }
        return nRet;
    }
}
