package com.zqkj.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.ParamDao;
import com.zqkj.entity.ParamEntity;
import com.zqkj.service.ParamService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 省份表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Service("paramService")
public class ParamServiceImpl extends BaseServiceImpl<ParamDao, ParamEntity> implements ParamService {
	/**
	 * 保存一个实体，null属性也会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	@CacheEvict(value="paramService", allEntries=true)
	public int insertSelective(ParamEntity record) {
		if(record.getIsParent() == null)
			record.setIsParent(false);
		int count = super.insertSelective(record);
		ParamEntity entity = new ParamEntity();
		entity.setId(record.getParentId());
		entity.setIsParent(true);
		super.updateByPrimaryKeySelective(entity);
		return count;
	}
	
	/**
	 * 根据主键更新属性不为null的值
	 */
	@Override
	@CacheEvict(value="paramService", allEntries=true)
	public int updateByPrimaryKeySelective(ParamEntity record) {
		ParamEntity oldEntity = super.selectOneById(record.getId());
		int count = super.updateByPrimaryKeySelective(record);
		if(!oldEntity.getParentId().equals(record.getParentId())){
			//查询以前父节点有没有子节点
			ParamEntity entity = new ParamEntity();
			entity.setParentId(oldEntity.getParentId());
			int n = super.selectCount(entity);
			//没有子节点，把IsParent设为false
			if(n == 0){
				entity = new ParamEntity();
				entity.setId(oldEntity.getParentId());
				entity.setIsParent(false);
				super.updateByPrimaryKeySelective(entity);
			}
			//把新的父节点IsParent设为true
			entity.setId(record.getParentId());
			entity.setIsParent(true);
			super.updateByPrimaryKeySelective(entity);
		}
		return count;
	}

	@Override
	@Cacheable(value="paramService", keyGenerator="cacheKeyGenerator", unless="#result == null")
	public List<ParamEntity> listChildren(ParamEntity entity) {
		List<ParamEntity> list = null;
		entity = super.selectOne(entity);
		if(entity != null){
			Example example = new Example(getClazz());
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("parentId", entity.getId());
			list = super.selectByExample(example);
		}
		return list;
	}
}
