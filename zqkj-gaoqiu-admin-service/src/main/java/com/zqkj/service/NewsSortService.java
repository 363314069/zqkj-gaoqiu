package com.zqkj.service;

import java.util.List;

import com.zqkj.entity.NewsSortEntity;

/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-05 16:45:51
 */
public interface NewsSortService extends BaseService<NewsSortEntity> {
	public List<NewsSortEntity> listChildren(NewsSortEntity entity);
}
