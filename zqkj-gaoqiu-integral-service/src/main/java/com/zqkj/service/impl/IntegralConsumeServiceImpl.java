package com.zqkj.service.impl;

import com.zqkj.dao.mapper.IntegralConsumeDao;
import com.zqkj.entity.IntegralConsumeEntity;
import com.zqkj.service.IntegralConsumeService;
import org.springframework.stereotype.Service;


/**
 * 积分消费表
 */
@Service("integralConsumeService")
public class IntegralConsumeServiceImpl extends BaseServiceImpl<IntegralConsumeDao, IntegralConsumeEntity> implements IntegralConsumeService {
}
