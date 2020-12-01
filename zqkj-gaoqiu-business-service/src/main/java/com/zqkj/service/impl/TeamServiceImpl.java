package com.zqkj.service.impl;

import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.TeamDao;
import com.zqkj.entity.TeamEntity;
import com.zqkj.service.TeamService;


/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2020-07-07 14:43:39
 */
@Service("teamService")
public class TeamServiceImpl extends BaseServiceImpl<TeamDao, TeamEntity> implements TeamService {

}
