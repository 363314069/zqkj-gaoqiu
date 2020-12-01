package com.zqkj.controller;

import java.io.IOException;
import java.util.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zqkj.utils.*;
import com.zqkj.utils.annotation.SysLog;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.entity.UserEntity;
import com.zqkj.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@EnableEurekaClient
@Controller
@RequestMapping("/security/user")
@Api(value = "用户表", tags = { "security/user 用户表" })
public class UserController extends BaseController<UserService, UserEntity> {
	
	@ResponseBody
	@RequestMapping(value = "/pagebyroleguid", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R pageByRoleGuid(PageUtil<UserEntity> page, String roleGuid, String userGroupGuid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceGuid", roleGuid);
		map.put("userGroupGuid", userGroupGuid);
		//map.put("type", UserMapService.TYPE_USER_ROLE);
		page = service.selectBySourceGuid(page, map);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}
	
	@ResponseBody
	@RequestMapping(value = "/pagebygroupguid", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R pageByGroupGuid(PageUtil<UserEntity> page, String userGroupGuid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceGuid", userGroupGuid);
		//map.put("type", UserMapService.TYPE_USER_GROUP);
		page = service.selectBySourceGuid(page, map);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}
	
	@ResponseBody
	@RequestMapping(value = "/pagenotbyroleguid", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R pageNotByRoleGuid(PageUtil<UserEntity> page, String roleGuid, String userGroupGuid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceGuid", roleGuid);
		map.put("userGroupGuid", userGroupGuid);
		//map.put("type", UserMapService.TYPE_USER_ROLE);
		page = service.selectNotBySourceGuid(page, map);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}
	
	@ResponseBody
	@RequestMapping(value = "/pagenotbygroupguid", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询数据列表", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
	public R pageNotByGroupGuid(PageUtil<UserEntity> page, String userGroupGuid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceGuid", userGroupGuid);
		//map.put("type", UserMapService.TYPE_USER_GROUP);
		page = service.selectNotBySourceGuid(page, map);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}

	/**
	 * 其他服务远程调用查询用户
	 * @param guid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOne", method = { RequestMethod.GET, RequestMethod.POST})
	public R selectOne(@RequestBody String guid) {
		UserEntity userEntity = service.selectOneByGuid(guid);
		if(userEntity == null){
			return R.error(Content.STATUS_CODE_5004);
		}else{
			return R.ok().putData(userEntity);
		}
	}


	/**
	 * 根据token查询用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectUserMessage", method = { RequestMethod.GET})
	public R selectUserMessage() {
		UserEntity userEntity = service.selectOneByGuid(BaseContentHandler.getUserGuid());
		if(userEntity == null){
			return R.error(Content.STATUS_CODE_5004,"查询数为空请重新登录！");
		}else{
			return R.ok().putData(userEntity);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
	@ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
	public R listByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		List<UserEntity> list = service.selectByExample(example);
		return R.ok().putData(list);
	}


	@ResponseBody
	@RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
	public R selectListPage(PageUtil<UserEntity> page, UserEntity entity) {
		// 查询列表数据
		page = service.selectListPage(page, entity);
		return R.ok().putData(page.getList()).put("count", page.getCount());
	}

	@ResponseBody
	@RequestMapping(value = "/selectAdmin", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "查询所有管理员", notes = "")
	public R selectAdmin() {
		Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(3);
		criteria.andIn("type", typeList);
		List<UserEntity> list = service.selectByExample(example);
		return R.ok().putData(list);
	}

	@ResponseBody
	@RequestMapping(value = "/bindingReservation", method = { RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "绑定球场管理员", notes = "")
	public R bindingAdminReservation(String reservationGuid,String userGuid) {
		return service.bindingAdminReservation(reservationGuid,userGuid);
	}


	@RequestMapping(value = "/exportexcel", method = {RequestMethod.GET, RequestMethod.POST})
	@ApiOperation(value = "导出Excel", notes = "列表  Header: token")
	@SysLog("导出Excel")
	public void exportExcel(HttpServletResponse response,String[] guids){
		Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		List<UserEntity> list = service.selectByExample(example);
		ArrayList<Map<String, Object>> rows = CollUtil.newArrayList();
		for(UserEntity userEntity : list){
			Map<String, Object> row1 = new LinkedHashMap<>();
			row1.put("姓名", userEntity.getName());
			row1.put("电话", userEntity.getPhone());
			rows.add(row1);
		}

		// 通过工具类创建writer，默认创建xls格式
		ExcelWriter writer = ExcelUtil.getWriter();
		// 一次性写出内容，使用默认样式
		writer.write(rows);
		//out为OutputStream，需要写出到的目标流

		//response为HttpServletResponse对象
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		response.setHeader("Content-Disposition","attachment;filename=test.xls");
		ServletOutputStream out= null;
		try {
			out = response.getOutputStream();
			writer.flush(out);
			// 关闭writer，释放内存
			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			writer.close();
		}
	}


	/*@ResponseBody
	@RequestMapping(value = "/queryAdmin", method = {RequestMethod.POST})
	@ApiOperation(value = "根据openid查询是否绑定管理员", notes = "参数为数组如[1,2,3,4]")
	public R queryAdmin(String openid) {
		Example example = new Example(getClazz());
		Example.Criteria criteria = example.createCriteria();
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(3);
		typeList.add(2);
		typeList.add(4);
		typeList.add(6);
		criteria.andIn("type", typeList);
		criteria.andEqualTo("openid",openid);
		int listCount = service.selectCountByCondition(example);
		return R.ok().putData(listCount);
	}*/
}
