package com.zqkj.service.impl;

import com.zqkj.dao.mapper.AuthorizedDao;
import com.zqkj.entity.AuthorizedEntity;
import com.zqkj.service.AuthorizedService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 受权关联表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Service("authorizedService")
public class AuthorizedServiceImpl extends BaseServiceImpl<AuthorizedDao, AuthorizedEntity> implements AuthorizedService {

	@Override
	public int saveModuleList(String sourceGuid, List<String> moduleguids, Integer type) {
		AuthorizedEntity entity = new AuthorizedEntity();
		List<AuthorizedEntity> listEntity = new ArrayList<AuthorizedEntity>();
		entity.setSourceGuid(sourceGuid);
		entity.setType(type);
		super.delete(entity);
		for (String guid : moduleguids) {
			 entity = new AuthorizedEntity();
			 entity.setSourceGuid(sourceGuid);
			 entity.setModuleGuid(guid);
			 entity.setType(type);
			 listEntity.add(entity);
		}
		Integer count = super.insertList(listEntity);
		return count;
	}

	@Override
	public List<AuthorizedEntity> getModuleList(String guid, Integer type) {
		AuthorizedEntity entity = new AuthorizedEntity();
		entity.setSourceGuid(guid);
		entity.setType(type);
		List<AuthorizedEntity> list = super.select(entity);
		return list;
	}
	
	@Override
	public Map<String,Map<String, Boolean>> getModuleList() {
		Map<String,Map<String, Boolean>> map = new HashMap<String,Map<String, Boolean>>();
		Example example = new Example(AuthorizedEntity.class);
		example.selectProperties("moduleGuid", "sourceGuid");
		List<AuthorizedEntity> list = super.selectByExample(example);
		for (AuthorizedEntity authorizedEntity : list) {
			Map<String, Boolean> moduleMap = map.get(authorizedEntity.getModuleGuid());
			if(moduleMap == null){
				moduleMap = new HashMap<String, Boolean>();
				map.put(authorizedEntity.getModuleGuid(), moduleMap);
			}
			moduleMap.put(authorizedEntity.getSourceGuid(), true);
		}
		return map;
	}
}
