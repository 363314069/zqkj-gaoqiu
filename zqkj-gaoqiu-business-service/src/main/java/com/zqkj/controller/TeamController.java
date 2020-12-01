package com.zqkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.TeamEntity;
import com.zqkj.service.TeamService;

import io.swagger.annotations.Api;


/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2020-07-07 14:43:39
 */
@Controller
@RequestMapping("/business/team")
@Api(value = "", tags = { "business/team " })
public class TeamController extends BaseController<TeamService, TeamEntity> {

}
