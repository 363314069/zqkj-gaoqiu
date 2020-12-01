package com.zqkj.service;

import com.zqkj.entity.IpEntity;

/**
 * 机构IP管理
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
public interface IpService extends BaseService<IpEntity> {
	
	public IpEntity selectOneByIp(String ip);
	
}
