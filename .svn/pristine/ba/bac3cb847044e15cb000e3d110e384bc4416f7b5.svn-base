package com.zqkj.service.impl;

import com.zqkj.dao.mapper.AreaDao;
import com.zqkj.entity.AreaEntity;
import com.zqkj.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 地区表
 */
@Service("areaService")
public class AreaServiceImpl extends BaseServiceImpl<AreaDao, AreaEntity> implements AreaService {

    @Override
    public List<AreaEntity> threeLevelLinkage(Integer code) {
        List<AreaEntity> list = dao.threeLevelLinkage(code);
        return list;
    }
}
