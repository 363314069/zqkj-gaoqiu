package com.zqkj.service.impl;

import com.zqkj.dao.mapper.CountryDao;
import com.zqkj.entity.CountryEntity;
import com.zqkj.service.CountryService;
import org.springframework.stereotype.Service;


/**
 * 国家表
 */
@Service("countryService")
public class CountryServiceImpl extends BaseServiceImpl<CountryDao, CountryEntity> implements CountryService {
}
