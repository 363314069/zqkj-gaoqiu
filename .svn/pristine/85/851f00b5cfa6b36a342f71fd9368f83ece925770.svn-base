package com.zqkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.ModuleEntity;
import com.zqkj.service.ModuleService;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 系统模块表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Controller
@RequestMapping("/security/module")
@Api(value = "系统模块表", tags = { "security/module 系统模块表" })
public class ModuleController extends BaseController<ModuleService, ModuleEntity> {
	
	@ResponseBody
	@RequestMapping(value = "/savemove", method = RequestMethod.POST)
	@ApiOperation(value = "移动数据", notes = "参数为json对像")
	@SysLog("移动数据")
	public R saveMove(ModuleEntity entity) {
		Integer count = service.updateByPrimaryKeySelective(entity);
		return R.ok().putData(entity).put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listbyindexid", method = {RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R listByIndexId(ModuleEntity entity) {
		if(entity.getIndexId() == null)
			return R.error("参数错误").putData("索引ID不能为空");
		return R.ok().putData(service.queryListByIndexId(entity));
	}
}
