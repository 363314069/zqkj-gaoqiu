package com.zqkj.service.impl;

import com.zqkj.dao.mapper.ApplyDao;
import com.zqkj.entity.ApplyEntity;
import com.zqkj.service.ApplyService;
import org.springframework.stereotype.Service;


/**
 * 报名表
 */
@Service("applyService")
public class ApplyServiceImpl extends BaseServiceImpl<ApplyDao, ApplyEntity> implements ApplyService {
}
