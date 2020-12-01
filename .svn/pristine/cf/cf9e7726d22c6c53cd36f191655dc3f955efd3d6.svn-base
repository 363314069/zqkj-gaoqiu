package com.zqkj.service.impl;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.ProvinceDao;
import com.zqkj.entity.ProvinceEntity;
import com.zqkj.service.ProvinceService;


/**
 * 省份表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Service("provinceService")
public class ProvinceServiceImpl extends BaseServiceImpl<ProvinceDao, ProvinceEntity> implements ProvinceService {
	/**
	 * 保存一个实体，null属性也会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(ProvinceEntity record) {
		if(record.getIsParent() == null)
			record.setIsParent(false);
		int count = super.insertSelective(record);
		ProvinceEntity provinceEntity = new ProvinceEntity();
		provinceEntity.setId(record.getParentId());
		provinceEntity.setIsParent(true);
		super.updateByPrimaryKeySelective(provinceEntity);
		return count;
	}
	
	/**
	 * 根据主键更新属性不为null的值
	 */
	@Override
	public int updateByPrimaryKeySelective(ProvinceEntity record) {
		ProvinceEntity oldParentEntity = super.selectOneById(record.getId());
		int count = super.updateByPrimaryKeySelective(record);
		if(!oldParentEntity.getParentId().equals(record.getParentId())){
			//查询以前父节点有没有子节点
			ProvinceEntity provinceEntity = new ProvinceEntity();
			provinceEntity.setParentId(oldParentEntity.getParentId());
			int n = super.selectCount(provinceEntity);
			//没有子节点，把IsParent设为false
			if(n == 0){
				provinceEntity = new ProvinceEntity();
				provinceEntity.setId(oldParentEntity.getParentId());
				provinceEntity.setIsParent(false);
				super.updateByPrimaryKeySelective(provinceEntity);
			}
			//把新的父节点IsParent设为true
			provinceEntity.setId(record.getParentId());
			provinceEntity.setIsParent(true);
			super.updateByPrimaryKeySelective(provinceEntity);
		}
		return count;
	}
}
