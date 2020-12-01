package com.zqkj.controller;

import com.zqkj.entity.VipEntity;
import com.zqkj.service.VipService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 会员卡
 */
@Controller
@RequestMapping("/security/vip")
@Api(value = "会员卡", tags = { "activity 会员卡" })
public class VipController extends BaseController<VipService, VipEntity> {

    /**
     * 用户绑定会员卡
     * @return
     */
    @RequestMapping(value = "/invitecode", method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "用户绑定会员卡")
    @ResponseBody
    public R bindingVipCode(VipEntity vipEntity){
        return service.bindingVipCode(vipEntity);
    }


    /**
     * 度假周卡回调
     * @return
     */
    @RequestMapping(value = "/weekscard", method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "度假周卡回调")
    @ResponseBody
    public R weeksCard(VipEntity vipEntity){
        return service.weeksCard(vipEntity);
    }


    /**
     * 查询用户会员卡
     * @return
     */
    @RequestMapping(value = "/selectuservip", method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询用户会员卡")
    @ResponseBody
    public R selectUserVip(VipEntity vipEntity){
        vipEntity.setUserGuid(BaseContentHandler.getUserGuid());
        List<VipEntity> vipList = service.select(vipEntity);
        return R.ok().putData(vipList);
    }
}
