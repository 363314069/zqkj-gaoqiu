package com.zqkj.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.AuthorizedEntity;
import com.zqkj.service.AuthorizedService;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 受权关联表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Controller
@RequestMapping("/security/authorized")
@Api(value = "受权关联表", tags = { "security/authorized 受权关联表" })
public class AuthorizedController extends BaseController<AuthorizedService, AuthorizedEntity> {

	@ResponseBody
	@RequestMapping(value = "/saverole", method = RequestMethod.POST)
	@ApiOperation(value = "更新角色权限", notes = "参数为json对像")
	@SysLog("更新角色权限")
	public R saveRole(@RequestParam String sourceGuid, @RequestParam List<String> moduleguids) {
		int count = service.saveModuleList(sourceGuid, moduleguids, 1);
		return R.ok().putData(sourceGuid).put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getrole", method = RequestMethod.POST)
	@ApiOperation(value = "查询角色权限", notes = "参数为json对像")
	@SysLog("查询角色权限")
	public R getRole(@RequestParam String guid) {
		List<AuthorizedEntity> list = service.getModuleList(guid, 1);
		return R.ok().putData(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户权限", notes = "参数为json对像")
	@SysLog("更新用户权限")
	public R saveUser(@RequestParam String sourceGuid, @RequestParam List<String> moduleguids) {
		int count = service.saveModuleList(sourceGuid, moduleguids, 2);
		return R.ok().putData(sourceGuid).put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
	@ApiOperation(value = "查询用户权限", notes = "参数为json对像")
	@SysLog("查询用户权限")
	public R getUser(@RequestParam String guid) {
		List<AuthorizedEntity> list = service.getModuleList(guid, 2);
		return R.ok().putData(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/savegroup", method = RequestMethod.POST)
	@ApiOperation(value = "更新分组权限", notes = "参数为json对像")
	@SysLog("更新分组权限")
	public R saveGroup(@RequestParam String sourceGuid, @RequestParam List<String> moduleguids) {
		int count = service.saveModuleList(sourceGuid, moduleguids, 3);
		return R.ok().putData(sourceGuid).put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getgroup", method = RequestMethod.POST)
	@ApiOperation(value = "查询角色权限", notes = "参数为json对像")
	@SysLog("查询角色权限")
	public R getGroup(@RequestParam String guid) {
		List<AuthorizedEntity> list = service.getModuleList(guid, 3);
		return R.ok().putData(list);
	}
}
