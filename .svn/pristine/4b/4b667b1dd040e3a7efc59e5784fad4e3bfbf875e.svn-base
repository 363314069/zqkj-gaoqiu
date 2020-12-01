package com.zqkj.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.NewsSortDao;
import com.zqkj.entity.NewsSortEntity;
import com.zqkj.service.NewsSortService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-05 16:45:51
 */
@Service("newsSortService")
public class NewsSortServiceImpl extends BaseServiceImpl<NewsSortDao, NewsSortEntity> implements NewsSortService {
	/**
	 * 保存一个实体，null属性也会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(NewsSortEntity record) {
		if(record.getIsParent() == null)
			record.setIsParent(false);
		int count = super.insertSelective(record);
		NewsSortEntity entity = new NewsSortEntity();
		entity.setId(record.getParentId());
		entity.setIsParent(true);
		super.updateByPrimaryKeySelective(entity);
		return count;
	}
	
	/**
	 * 根据主键更新属性不为null的值
	 */
	@Override
	public int updateByPrimaryKeySelective(NewsSortEntity record) {
		NewsSortEntity oldEntity = super.selectOneById(record.getId());
		int count = super.updateByPrimaryKeySelective(record);
		if(!oldEntity.getParentId().equals(record.getParentId())){
			//查询以前父节点有没有子节点
			NewsSortEntity entity = new NewsSortEntity();
			entity.setParentId(oldEntity.getParentId());
			int n = super.selectCount(entity);
			//没有子节点，把IsParent设为false
			if(n == 0){
				entity = new NewsSortEntity();
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
	public List<NewsSortEntity> listChildren(NewsSortEntity entity) {
		List<NewsSortEntity> list = null;
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
