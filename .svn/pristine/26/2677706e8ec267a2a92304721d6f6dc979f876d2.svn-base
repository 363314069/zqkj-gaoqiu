package com.zqkj.controller;

import com.zqkj.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.OrganizationEntity;
import com.zqkj.service.OrganizationService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 机构表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2018-08-30 11:22:26
 */
@Controller
@RequestMapping("/security/organization")
@Api(value = "机构表", tags = { "security/organiztion 机构表" })
public class OrganizationController extends BaseController<OrganizationService, OrganizationEntity> {


    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<OrganizationEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }
}
