package com.zqkj.service;

import com.zqkj.entity.ModuleEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统模块表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
public interface ModuleService extends BaseService<ModuleEntity> {
	/**分组类型**/
	public final static int MODULE_TYPE_SORT = 0;
	/**接口类型**/
	public final static int MODULE_TYPE_API = 1;
	public List<ModuleEntity> queryListByIndexId(ModuleEntity entity);
	public Map<String, String> getModuleMap(int type);
	
}
