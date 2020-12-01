package com.zqkj.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.UserMapDao;
import com.zqkj.entity.UserMapEntity;
import com.zqkj.service.UserMapService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 用户角色映射表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-11-13 14:59:25
 */
@Service("userMapService")
public class UserMapServiceImpl extends BaseServiceImpl<UserMapDao, UserMapEntity> implements UserMapService {

	@Override
	public int delByUserGuids(String[] userGuids, String sourceGuid) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> userGuidList = new ArrayList<String>(Arrays.asList(userGuids));
		criteria.andIn("userGuid", userGuidList);
		criteria.andEqualTo("sourceGuid", sourceGuid);
		return super.deleteByExample(example);
	}
	
	@Override
	public int insertList(String[] userGuids, String sourceGuid) {
		List<UserMapEntity> list = new ArrayList<UserMapEntity>();
		for (String userGuid : userGuids) {
			UserMapEntity entity = new UserMapEntity();
			entity.setUserGuid(userGuid);
			entity.setSourceGuid(sourceGuid);
			list.add(entity);
		}
		return super.insertList(list);
	}
}
