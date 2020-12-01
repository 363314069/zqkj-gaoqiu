package com.zqkj.controller;

import com.zqkj.entity.AreaEntity;
import com.zqkj.service.AreaService;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 地区表
 */
@Controller
@RequestMapping("/security/area")
@Api(value = "地区表", tags = { "activity 地区表" })
public class AreaController extends BaseController<AreaService, AreaEntity> {

    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<AreaEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }


    @ResponseBody
    @RequestMapping(value = "/threelevellink", method = {RequestMethod.POST})
    @ApiOperation(value = "三级联动", notes = "code地区代码")
    public R threeLevelLinkage(Integer code,Integer countryCode) {
        List<AreaEntity> list = service.threeLevelLinkage(code);
        return R.ok().putData(list);
    }
}

