package com.zqkj.controller;

import com.zqkj.entity.IntegralEntity;
import com.zqkj.service.IntegralService;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 积分表
 */
@Controller
@RequestMapping("/integral/integral")
@Api(value = "积分表", tags = { "activity 积分表" })
public class IntegralController extends BaseController<IntegralService, IntegralEntity> {

    @ResponseBody
    @RequestMapping(value = "/integralRecurrence", method = { RequestMethod.POST})
    @ApiOperation(value = "订场后返现积分", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R integralRecurrence(@RequestBody String jsonStr){
        return service.integralRecurrence(jsonStr);
    }
}
