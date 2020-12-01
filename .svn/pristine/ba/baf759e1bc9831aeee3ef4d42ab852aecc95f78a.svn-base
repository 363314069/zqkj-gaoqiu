package com.zqkj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.zqkj.bean.CityVo;
import com.zqkj.bean.ProvinceVo;
import com.zqkj.entity.ProvinceEntity;
import com.zqkj.service.ProvinceService;
import com.zqkj.utils.GsonUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;

import cn.hutool.core.io.file.FileReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 省份表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Controller
@RequestMapping("/security/province")
@Api(value = "省份表", tags = { "security/province 省份表" })
@CrossOrigin(maxAge = 3600) 
public class ProvinceController extends BaseController<ProvinceService, ProvinceEntity> {
	
	@ResponseBody
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ApiOperation(value = "初始化省份数据", notes = "参数为json对像")
	@SysLog("初始化省份数据")
	public R init(){
		service.delete(new ProvinceEntity());
		FileReader fileReader = new FileReader("province.json", FileReader.DEFAULT_CHARSET);
		String result = fileReader.readString();
		List<ProvinceVo> list = GsonUtil.fromJson(result, new TypeToken<List<ProvinceVo>>(){}.getType());
		ProvinceEntity provinceEntity = null;
		ProvinceEntity cityEntity = null;
		ProvinceEntity areaEntity = null;
		for (ProvinceVo province : list) {
			provinceEntity = new ProvinceEntity();
			provinceEntity.setName(province.getName());
			provinceEntity.setLevel(1);
			provinceEntity.setParentId(0);
			service.insert(provinceEntity);
			List<CityVo> city = province.getCity();
			for (CityVo cityVo : city) {
				cityEntity = new ProvinceEntity();
				cityEntity.setName(cityVo.getName());
				cityEntity.setLevel(2);
				cityEntity.setParentId(provinceEntity.getId());
				service.insert(cityEntity);
				List<String> area = cityVo.getArea();
				for (String string : area) {
					areaEntity = new ProvinceEntity();
					areaEntity.setName(string);
					areaEntity.setLevel(3);
					areaEntity.setParentId(cityEntity.getId());
					service.insert(areaEntity);
				}
			}
		}
		return R.ok("初始化完成!");
	}
	
	@ResponseBody
	@RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
	@ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
	public R listByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		List<ProvinceEntity> list = service.selectByExample(example);
		return R.ok().putData(list);
	}
}
