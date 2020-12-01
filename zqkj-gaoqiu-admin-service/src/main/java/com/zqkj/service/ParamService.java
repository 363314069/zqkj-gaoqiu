package com.zqkj.service;

import java.util.List;

import com.zqkj.entity.ParamEntity;

/**
 * 省份表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
public interface ParamService extends BaseService<ParamEntity> {
	public List<ParamEntity> listChildren(ParamEntity entity);
}
