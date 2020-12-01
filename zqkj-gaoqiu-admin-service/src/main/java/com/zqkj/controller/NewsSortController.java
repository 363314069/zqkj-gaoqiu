package com.zqkj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.NewsSortEntity;
import com.zqkj.service.NewsSortService;
import com.zqkj.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-05 16:45:51
 */
@Controller
@RequestMapping("/security/newssort")
@Api(value = "", tags = { "security/newssort " })
public class NewsSortController extends BaseController<NewsSortService, NewsSortEntity> {
	@ResponseBody
	@RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
	@ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
	public R listByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		List<NewsSortEntity> list = service.selectByExample(example);
		return R.ok().putData(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listchildren", method = {RequestMethod.POST})
	@ApiOperation(value = "查询子节点", notes = "参数为数组如[1,2,3,4]")
	public R listChildren(NewsSortEntity entity) {
		List<NewsSortEntity> list = service.listChildren(entity);
		return R.ok().putData(list);
	}
}
