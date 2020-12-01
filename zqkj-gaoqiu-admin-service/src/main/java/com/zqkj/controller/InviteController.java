package com.zqkj.controller;

import com.zqkj.entity.InviteEntity;
import com.zqkj.service.InviteService;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 邀请表
 */
@Controller
@RequestMapping("/security/invite")
@Api(value = "邀请表", tags = { "activity 邀请表" })
public class InviteController extends BaseController<InviteService, InviteEntity> {


    /**
     * 判断用户是否已经注册，已经注册直接进行绑定
     * invitedGuid 邀请人guid
     * @return
     */
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    @ApiOperation(value = "判断用户是否被绑定")
    @ResponseBody
    public R binding(String invitedGuid){
        return service.binding(invitedGuid);
    }


    /**
     * 查询查询用户的邀请人
     * invitedGuid 邀请人guid
     * @return
     */
    @RequestMapping(value = "/selectinviter", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户的邀请人")
    @ResponseBody
    public R selectInviter(@RequestBody String userGuid){
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setBeInvitedGuid(userGuid);
        return R.ok().putData(service.selectOne(inviteEntity));
    }
}
