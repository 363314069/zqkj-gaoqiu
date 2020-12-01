package com.zqkj.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zqkj.entity.CouponsactmapEntity;
import com.zqkj.service.CouponsactmapService;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.IntroductionDao;
import com.zqkj.entity.IntroductionEntity;
import com.zqkj.service.IntroductionService;
import tk.mybatis.mapper.entity.Example;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 场地表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:02:40
 */
@Service("introductionService")
public class IntroductionServiceImpl extends BaseServiceImpl<IntroductionDao, IntroductionEntity> implements IntroductionService {

    @Autowired
    private CouponsactmapService couponsactmapService;


    @Override
    public List<IntroductionEntity> selectIntroductionList(IntroductionEntity introductionEntity) {
        return dao.selectIntroductionList(introductionEntity);
    }

    @Override
    public int saveIntroduction(IntroductionEntity introductionEntity) {
        int nRet = dao.insertSelective(introductionEntity);
        if (nRet > 0) {
            //批量保存商品和优惠券关联
            List<CouponsactmapEntity> couponsactmapList = new ArrayList<>();
            for (String ss : introductionEntity.getPreferentialList()) {
                CouponsactmapEntity couponsactmapEntity = new CouponsactmapEntity();
                couponsactmapEntity.setCouponsGuid(ss);
                couponsactmapEntity.setActivityGuid(introductionEntity.getGuid());
                couponsactmapEntity.setType(1);
                couponsactmapEntity.setState(1);
                couponsactmapList.add(couponsactmapEntity);
            }
            if (couponsactmapList != null && couponsactmapList.size() > 0) {
                couponsactmapService.insertList(couponsactmapList);
            }
        }
        return nRet;
    }

    @Override
    public int updateIntroduction(IntroductionEntity introductionEntity) {
        int nRet = dao.updateByPrimaryKeySelective(introductionEntity);
        if (nRet > 0) {
            //删除商品优惠券关联表的该商品所有优惠券
            CouponsactmapEntity couponsactmapDel = new CouponsactmapEntity();
            couponsactmapDel.setActivityGuid(introductionEntity.getGuid());
            int intDel = couponsactmapService.delete(couponsactmapDel);

            //重新插入选择的优惠券
            List<CouponsactmapEntity> insertList = new ArrayList<>();
            for (String couponsGuid : introductionEntity.getPreferentialList()) {
                CouponsactmapEntity couponsactmapInsert = new CouponsactmapEntity();
                couponsactmapInsert.setType(1);
                couponsactmapInsert.setState(1);
                couponsactmapInsert.setActivityGuid(introductionEntity.getGuid());
                couponsactmapInsert.setCouponsGuid(couponsGuid);
                insertList.add(couponsactmapInsert);
            }
            if (insertList != null && insertList.size() > 0) {
                couponsactmapService.insertList(insertList);
            }

        }
        return nRet;
    }

    @Override
    public PageUtil<IntroductionEntity> selectListPage(PageUtil<IntroductionEntity> pageUtil, IntroductionEntity record) {
        if (pageUtil.getPage() == 0) {
            pageUtil.setPage(1);
        }
        record.setLimit(pageUtil.getLimit());
        record.setPage(((pageUtil.getPage() - 1) * pageUtil.getLimit()));
        Page<IntroductionEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
        if (!StringUtil.isEmpty(pageUtil.getOrderBy())) {
            page.setOrderBy(pageUtil.getOrderBy());
        }
        int count = dao.selectListPageCount(record);
        List<IntroductionEntity> list = dao.selectListPage(record);
        pageUtil = new PageUtil<IntroductionEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
        pageUtil.setCount(count);
        pageUtil.setList(list);
        return pageUtil;
    }
}
