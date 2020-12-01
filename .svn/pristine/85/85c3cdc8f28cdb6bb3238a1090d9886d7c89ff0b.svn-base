package com.zqkj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.service.UserMapService;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 用户角色映射表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-11-13 14:59:25
 */
@Controller
@RequestMapping("/security/usermap")
@Api(value = "用户角色映射表", tags = { "security/usermap" })
public class UserMapController {
	
	@Autowired
	private UserMapService userMapService;
	
	@ResponseBody
	@RequestMapping(value = "/savelist", method = {RequestMethod.POST})
	@ApiOperation(value = "批量添加数据", notes = "参数为userGuids如[guid1,guid2,guid3,guid4],sourceGuid")
	@SysLog("批量添加数据")
	public R saveList(String[] userGuids, String sourceGuid) {
		Integer count = userMapService.insertList(userGuids, sourceGuid);
		return R.ok().put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delbyuserguids", method = {RequestMethod.POST})
	@ApiOperation(value = "批量删除数据", notes = "参数为数组json如[1,2,3,4]")
	@SysLog("批量删除数据byGuids")
	public R delByUserGuids(String[] userGuids, String sourceGuid) {
		Integer count = userMapService.delByUserGuids(userGuids, sourceGuid);
		return R.ok().put("count", count);
	}

}
