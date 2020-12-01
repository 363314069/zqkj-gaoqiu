package com.zqkj.service.impl;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.NewsDao;
import com.zqkj.entity.NewsEntity;
import com.zqkj.service.NewsService;


/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-05 16:45:51
 */
@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<NewsDao, NewsEntity> implements NewsService {

}
