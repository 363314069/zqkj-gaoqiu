package com.zqkj.controller;

import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.CouponsteatimeEntity;
import com.zqkj.service.CouponsteatimeService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 订场商品
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-17 17:31:53
 */
@Controller
@RequestMapping("/business/couponsteatime")
@Api(value = "", tags = { "business/couponsteatime " })
public class CouponsteatimeController extends BaseController<CouponsteatimeService, CouponsteatimeEntity> {

    @ResponseBody
    @RequestMapping(value = "/savecouponsteatime", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据", notes = "参数为json对像")
    @SysLog("保存数据")
    public R saveCouponsteatime(@RequestBody CouponsteatimeEntity entity) {
        Integer count = service.saveCouponsteatime(entity);
        return R.ok().putData(entity).put("count", count);
    }


    @ResponseBody
    @RequestMapping(value = "/updatecouponsteatime", method = RequestMethod.POST)
    @ApiOperation(value = "修改数据", notes = "参数为json对像")
    @SysLog("修改数据")
    public R updateCouponsteatime(@RequestBody CouponsteatimeEntity entity) {
        Integer count = service.updateCouponsteatime(entity);
        return R.ok().putData(entity).put("count", count);
    }
}
