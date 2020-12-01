package com.zqkj.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.service.BaseService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.ObjectUtil;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;

import io.swagger.annotations.ApiOperation;

public class BaseController<S extends BaseService<T>, T> {
	@Autowired
	protected S service;
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存数据", notes = "参数为json对像")
	@SysLog("保存数据")
	public R save(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5005).put("count", 0);
		}
		Integer count = service.insertSelective(entity);
		return R.ok().putData(entity).put("count", count);
	}
	
	@ResponseBody
	@RequestMapping(value = "/savelist", method = RequestMethod.POST)
	@ApiOperation(value = "批量保存数据", notes = "参数为数组json对像")
	@SysLog("批量保存数据")
	public R saveList(@RequestBody List<T> list) {
		if(ObjectUtil.listObjIsNull(list)){
			return R.error(Content.STATUS_CODE_5005).put("count", 0);
		}
		Integer count = service.insertList(list);
		return R.ok().putData(list).put("count", count);
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "根据ID或Guid获取数据", notes = "参数id为字符串Guid或整型Id")
	public R info(T entity) {
		return R.ok().putData(service.selectOne(entity));
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST})
	@ApiOperation(value = "更新数据", notes = "参数为json对像")
	@SysLog("更新数据")
	public R update(T entity) {
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5001).put("count", 0);
		}
		Integer count = service.updateByPrimaryKeySelective(entity);
		return R.ok().putData(entity).put("count", count);
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "删除数据", notes = "参数id为字符串Guid或整型Id")
	@SysLog("删除数据")
	public R del(T entity) {
		/*
		 * 这里存在不传参时会删除所有数据BGU
		 */
		if(ObjectUtil.isAllNull(entity)){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		Integer count = service.delete(entity);
		return R.ok().putData(entity).put("count", count);
	}

	@ResponseBody
	@RequestMapping(value = "/delbyids", method = {RequestMethod.POST})
	@ApiOperation(value = "批量删除数据byIds", notes = "参数如[1,2,3,4]")
	@SysLog("批量删除数据byIds")
	public R delByIds(Long[] ids) {
		if(ids == null || ids.length == 0){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		Integer count = service.deleteByIds(ids);
		return R.ok().putData(ids).put("count", count);
	}

	@ResponseBody
	@RequestMapping(value = "/delbyguids", method = {RequestMethod.POST})
	@ApiOperation(value = "批量删除数据", notes = "参数为数组json如[1,2,3,4]")
	@SysLog("批量删除数据byGuids")
	public R delByGuids(String[] guids) {
		if(guids == null || guids.length == 0){
			return R.error(Content.STATUS_CODE_5003).put("count", 0);
		}
		Integer count = service.deleteByGuids(guids);
		return R.ok().putData(guids).put("count", count);
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R list(T entity, String orderBy) {
		return R.ok().putData(service.select(entity, orderBy));
	}

	@ResponseBody
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
	public R page(PageUtil<T> page,T entity) {
		// 查询列表数据
		page = service.select(page, entity);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}
	
	@ResponseBody
	@RequestMapping(value = "/total", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据记录数", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R total(T entity) {
		return R.ok().putData(service.selectCount(entity));
	}

	public String getUserName() {
		return BaseContentHandler.getUserName();
	}
	
	/**
	 * 获取继承子类的class
	 * @return
	 */
	public Class<?> getClazz() {
		Class<?> clazz = this.getClass();
		// getSuperclass()获得该类的父类
		//System.out.println(clazz.getSuperclass());
		// getGenericSuperclass()获得带有泛型的父类
		// Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
		Type type = clazz.getGenericSuperclass();
		//System.out.println(type);
		// ParameterizedType参数化类型，即泛型
		ParameterizedType p = (ParameterizedType) type;
		// getActualTypeArguments获取参数化类型的数组，泛型可能有多个
		return (Class<?>) p.getActualTypeArguments()[1];
	}
}
