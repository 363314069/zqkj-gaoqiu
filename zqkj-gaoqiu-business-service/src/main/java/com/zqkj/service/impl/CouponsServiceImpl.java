package com.zqkj.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zqkj.entity.*;
import com.zqkj.service.CouponsactmapService;
import com.zqkj.service.CouponsorgmapService;
import com.zqkj.service.CouponsuserService;
import com.zqkj.utils.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.CouponsDao;
import com.zqkj.service.CouponsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 
 * 优惠券
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
@Service("couponsService")
public class CouponsServiceImpl extends BaseServiceImpl<CouponsDao, CouponsEntity> implements CouponsService {

    @Autowired
    private CouponsorgmapService couponsorgmapService;

    @Autowired
    private CouponsuserService couponsuserService;

    @Override
    public int saveCoupons(CouponsEntity entity) {
        int insertCount = insertSelective(entity);
        if(insertCount > 0){
            if(entity.getScope() == 3 || entity.getScope() == 4){
                //指定商家
                List<CouponsorgmapEntity> orgList = new ArrayList<>();
                for(String orgGuid : entity.getOrganizationGuids()){
                    CouponsorgmapEntity couponsorgmapEntity = new CouponsorgmapEntity();
                    couponsorgmapEntity.setType(1);
                    couponsorgmapEntity.setState(1);
                    couponsorgmapEntity.setCouponsGuid(entity.getGuid());
                    couponsorgmapEntity.setOrganizationGuid(orgGuid);
                    orgList.add(couponsorgmapEntity);
                }
                int orgCount = couponsorgmapService.insertList(orgList);
                return insertCount;
            }else{
                return insertCount;
            }
        }else{
            return 0;
        }
    }

    @Override
    public int updateCoupons(CouponsEntity entity) {
        Integer updateCount = updateByPrimaryKeySelective(entity);
        if(updateCount > 0){
            if(entity.getScope() == 3 || entity.getScope() == 4){
                CouponsorgmapEntity delOrgMap = new CouponsorgmapEntity();
                delOrgMap.setCouponsGuid(entity.getGuid());
                int delOrgCount = couponsorgmapService.delete(delOrgMap);
                //指定商家
                List<CouponsorgmapEntity> orgList = new ArrayList<>();
                for(String orgGuid : entity.getOrganizationGuids()){
                    CouponsorgmapEntity couponsorgmapEntity = new CouponsorgmapEntity();
                    couponsorgmapEntity.setType(1);
                    couponsorgmapEntity.setState(1);
                    couponsorgmapEntity.setCouponsGuid(entity.getGuid());
                    couponsorgmapEntity.setOrganizationGuid(orgGuid);
                    orgList.add(couponsorgmapEntity);
                }
                int orgCount = couponsorgmapService.insertList(orgList);
                return updateCount;
            }else{
                return updateCount;
            }
        }else{
            return 0;
        }
    }

    @Override
    public synchronized R getCoupons(String guid) {
        //查询
        CouponsEntity couponsEntity = selectOneByGuid(guid);
        if(couponsEntity.getSeleaseNumber() <= 0){
            return R.error(Content.STATUS_CODE_5007,"手慢了该优惠券已经被领完！");
        }
        //查询用户是否已经领取过
        CouponsuserEntity couponsuserEntity = new CouponsuserEntity();
        couponsuserEntity.setUserGuid(BaseContentHandler.getUserGuid());
        couponsuserEntity.setCouponsGuid(couponsEntity.getGuid());
        int userCouponsCount = couponsuserService.selectCount(couponsuserEntity);
        if(userCouponsCount >= couponsEntity.getReceiveAmount()){
            return R.error(Content.STATUS_CODE_5007,"您已经领取该优惠券最大限额，不可在进行领取！");
        }else{
            //没超出领取限制，可以进行领取
            couponsuserEntity.setIfUse(0);
            couponsuserEntity.setType(1);
            couponsuserEntity.setState(0);
            int insertCount = couponsuserService.insertSelective(couponsuserEntity);
            if(insertCount > 0){
                CouponsEntity couponsUpdate = new CouponsEntity();
                couponsUpdate.setId(couponsEntity.getId());
                couponsUpdate.setSeleaseNumber(couponsEntity.getSeleaseNumber() - 1);
                updateByPrimaryKeySelective(couponsUpdate);
                return R.ok().putData(couponsEntity);
            }else{
                return R.error(Content.STATUS_CODE_5007,"领取失败请重试！");
            }
        }
    }

    @Override
    public List<CouponsEntity> selectOrgList() {

        CouponsorgmapEntity couponsorgmapEntity = new CouponsorgmapEntity();
        couponsorgmapEntity.setOrganizationGuid(BaseContentHandler.getOrganizationGuid());
        List<CouponsorgmapEntity> couponsorgmapList = couponsorgmapService.select(couponsorgmapEntity);
        List<String> guidList = new ArrayList<String>();
        for(CouponsorgmapEntity cc : couponsorgmapList){
            guidList.add(cc.getCouponsGuid());
        }

        List<CouponsEntity> list = null;
        if(guidList != null && guidList.size() > 0){
            Example example = new Example(getClazz());
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("guid", guidList);//优惠券guid
            criteria.andGreaterThan("endTime",new Date());
            list = selectByExample(example);
        }

        Example example1 = new Example(getClazz());
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andGreaterThan("endTime",new Date());
        List<Integer> listInt = new ArrayList<Integer>();
        listInt.add(1);//全品类
        listInt.add(2);//指定商品
        criteria1.andIn("scope", listInt);
        List<CouponsEntity> list1 = selectByExample(example1);
        if(list != null && list1 != null){
            list1.addAll(list);//合并这俩个集合 返回list1
            return list1;
        }else if(list == null &&  list1 != null){
            return list1;
        }else if(list1 == null &&  list != null){
            return list;
        }else{
            return null;
        }
    }

    @Override
    public PageUtil<CouponsEntity> selectListPage(PageUtil<CouponsEntity> pageUtil, CouponsEntity record) {
        if(pageUtil.getPage() == 0){
            pageUtil.setPage(1);
        }
        record.setLimit(pageUtil.getLimit());
        record.setPage(((pageUtil.getPage()-1)*pageUtil.getLimit()));
        Page<CouponsEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
        if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
            page.setOrderBy(pageUtil.getOrderBy());
        }
        List<CouponsEntity> list = dao.selectListPage(record);
        int count = dao.selectListPageCount(record);
        pageUtil = new PageUtil<CouponsEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
        pageUtil.setCount(count);
        pageUtil.setList(list);
        return pageUtil;
    }
}
