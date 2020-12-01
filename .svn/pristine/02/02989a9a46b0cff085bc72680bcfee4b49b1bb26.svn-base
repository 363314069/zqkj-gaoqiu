package com.zqkj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.UserGroupEntity;
import com.zqkj.service.UserGroupService;
import com.zqkj.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 用户分组
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Controller
@RequestMapping("/security/usergroup")
@Api(value = "用户分组", tags = { "security/usergroup 用户分组表" })
public class UserGroupController extends BaseController<UserGroupService, UserGroupEntity> {

	@ResponseBody
	@RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
	@ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
	public R listByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		List<UserGroupEntity> list = service.selectByExample(example);
		return R.ok().putData(list);
	}
}
