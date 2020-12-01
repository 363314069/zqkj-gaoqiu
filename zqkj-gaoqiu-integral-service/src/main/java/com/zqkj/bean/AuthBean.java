package com.zqkj.bean;

import com.zqkj.entity.ModuleEntity;

import java.util.List;
import java.util.Map;

public class AuthBean {

	private long id;
	private List<ModuleEntity> moduleList;
	private Map<String, Integer> moduleMap;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<ModuleEntity> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModuleEntity> moduleList) {
		this.moduleList = moduleList;
	}
	public Map<String, Integer> getModuleMap() {
		return moduleMap;
	}
	public void setModuleMap(Map<String, Integer> moduleMap) {
		this.moduleMap = moduleMap;
	}
}
