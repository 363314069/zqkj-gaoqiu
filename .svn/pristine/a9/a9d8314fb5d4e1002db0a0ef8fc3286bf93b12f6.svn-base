package com.zqkj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zqkj.bean.AuthBean;
import com.zqkj.dao.mapper.ModuleDao;
import com.zqkj.entity.AuthorizedEntity;
import com.zqkj.entity.ModuleEntity;
import com.zqkj.service.ModuleService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 系统模块表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl<ModuleDao, ModuleEntity> implements ModuleService {
	public static AuthBean authBean = null;
	@Override
	public int insertSelective(ModuleEntity entity) {
		updateSort(entity.getParentId(), null, entity.getSort());
		return super.insertSelective(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(ModuleEntity entity) {
		// TODO Auto-generated method stub
		if(entity.getId() == null)
			return 0;
		updateSort(entity.getParentId(), entity.getId(), entity.getSort());
		return super.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 更新节点下数据排序
	 * @param parentId
	 * @param id
	 * @param moduleSort
	 */
	public void updateSort(Integer parentId,Integer id, Integer moduleSort){
		if(parentId == null)
			return;
		Example example = new Example(ModuleEntity.class);
		example.setOrderByClause("sort");
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		List<ModuleEntity> list = this.selectByCondition(example);
		int i = 0;
		ModuleEntity entity1 = new ModuleEntity();
		for (ModuleEntity moduleListEntity : list) {
			if(moduleSort == i){
				i++;
			}
			if(id == moduleListEntity.getId()){
				continue;
			}
			entity1.setId(moduleListEntity.getId());
			entity1.setSort(i++);
			this.updateByPrimaryKeySelective(entity1);
		}
	}

	@Override
	public List<ModuleEntity> queryListByIndexId(ModuleEntity entity) {
		if(StringUtils.isBlank(entity.getIndexId())){
			return null;
		}
		Example example = new Example(ModuleEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andLike("indexId", entity.getIndexId());
		List<ModuleEntity> list = this.selectByCondition(example);
		return list;
	}
	
	public AuthBean getAuth(){
		if(authBean != null){
			return authBean;
		}
		authBean = new AuthBean();
		Condition condition = new Condition(ModuleEntity.class);
		condition.setOrderByClause("sort");
		List<ModuleEntity> list = this.selectByCondition(condition);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getGuid(), i);
		}
		authBean.setId((new Date()).getTime());
		authBean.setModuleList(list);
		authBean.setModuleMap(map);
		return authBean;
	}
	
	@Override
	public Map<String, String> getModuleMap(int type){
		Example example = new Example(ModuleEntity.class);
		example.selectProperties("guid", "link");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("type", type);
		List<ModuleEntity> list = this.selectByExample(example);
		Map<String, String> map = new HashMap<String, String>();
		for (ModuleEntity moduleEntity : list) {
			map.put(moduleEntity.getLink(), moduleEntity.getGuid());
		}
		return map;
	}
}
