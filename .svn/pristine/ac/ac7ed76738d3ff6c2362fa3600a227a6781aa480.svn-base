package com.zqkj.service;

import java.util.List;
import java.util.Map;

import com.zqkj.entity.AuthorizedEntity;

/**
 * 受权关联表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
public interface AuthorizedService extends BaseService<AuthorizedEntity> {
	public int saveModuleList(String sourceGuid, List<String> moduleguids, Integer type);
	public List<AuthorizedEntity> getModuleList(String guid, Integer type);
	public Map<String,Map<String, Boolean>> getModuleList();
}
