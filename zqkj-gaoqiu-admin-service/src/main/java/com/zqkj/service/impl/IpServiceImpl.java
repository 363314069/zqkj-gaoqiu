package com.zqkj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zqkj.dao.mapper.IpDao;
import com.zqkj.entity.IpEntity;
import com.zqkj.service.IpService;
import com.zqkj.utils.ip.IPUtil;
import com.zqkj.utils.ip.IPUtil.IPBean;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 机构IP管理
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Service("ipService")
public class IpServiceImpl extends BaseServiceImpl<IpDao, IpEntity> implements IpService {

	public int insertSelective(IpEntity record){
		String strips = record.getAllIp();
		List<IPBean> ipBeanList = IPUtil.str2IpList(strips);
		List<IpEntity> ipEntityList = new ArrayList<IpEntity>();
		for (IPBean ipBean : ipBeanList) {
			IpEntity ipEntity = new IpEntity();
			ipEntity.setStartIp(ipBean.getStartStrIp());
			ipEntity.setEndIp(ipBean.getEndStrIp());
			ipEntity.setState(record.getState());
			ipEntity.setOrganizationGuid(record.getOrganizationGuid());
			ipEntityList.add(ipEntity);
		}
		return super.insertList(ipEntityList);
		//return 0;
	}
	
	public IpEntity selectOneByIp(String ip){
		ip = IPUtil.ipComplement(ip);
		Example example = new Example(IpEntity.class);
		example.setOrderByClause("id DESC");
		Criteria criteria = example.createCriteria();
		criteria.andLessThanOrEqualTo("startIp", ip);
		criteria.andGreaterThanOrEqualTo("endIp", ip);
		PageHelper.startPage(0, 1);
		return super.selectOneByExample(example);
	}
}
