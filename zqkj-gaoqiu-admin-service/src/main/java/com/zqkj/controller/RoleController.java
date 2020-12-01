package com.zqkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.RoleEntity;
import com.zqkj.service.RoleService;

import io.swagger.annotations.Api;


/**
 * 角色表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Controller
@RequestMapping("/security/role")
@Api(value = "角色表", tags = { "security/role 角色表" })
public class RoleController extends BaseController<RoleService, RoleEntity> {

}
