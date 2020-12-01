package com.zqkj.controller;

import cn.hutool.json.JSONObject;
import com.zqkj.entity.ReserveEntity;
import com.zqkj.remote.service.UserService;
import com.zqkj.service.ReserveService;
import com.zqkj.utils.*;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * 预定表
 */
@Controller
@RequestMapping("/business/reserve")
@Api(value = "预定表", tags = { "activity 预定表" })
public class ReserveController extends BaseController<ReserveService, ReserveEntity> {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/reservesave", method = RequestMethod.POST)
    @ApiOperation(value = "用户预定", notes = "参数为json对像")
    @SysLog("用户预定")
    public R reserveSave(@RequestBody ReserveEntity entity) {
        ReserveEntity selectBean = new ReserveEntity();
        selectBean.setOrderGuid(entity.getOrderGuid());
        selectBean = service.selectOne(selectBean);
        if(selectBean != null){
            return R.error(Content.STATUS_CODE_5008,"该订单已经使用，如有问题请联系管理员！");
        }
        String user = userService.selectOne(BaseContentHandler.getUserGuid());
        entity.setUserGuid(BaseContentHandler.getUserGuid());
        JSONObject jsonObject = new JSONObject(user);
        jsonObject = new JSONObject(jsonObject.getStr("data"));
        entity.setUserPhone(jsonObject.getStr("phone"));
        entity.setUserName(jsonObject.getStr("name"));
        Integer count = service.insertSelective(entity);
        return R.ok().putData(entity).put("count", count);
    }


    @ResponseBody
    @RequestMapping(value = "/selectbyorder", method = RequestMethod.POST)
    @ApiOperation(value = "根据订单GUID查询", notes = "参数为json对像")
    public R selectOneByOrderGuid(String orderGuid) {
        ReserveEntity selectBean = new ReserveEntity();
        selectBean.setOrderGuid(orderGuid);
        selectBean = service.selectOne(selectBean);
        return R.ok().putData(selectBean);
    }


    @ResponseBody
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ApiOperation(value = "后台接收预订", notes = "参数为json对像")
    public R receive(ReserveEntity reserveEntity) {
        return service.receive(reserveEntity);
    }
}
