package com.zqkj.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zqkj.entity.CommissionEntity;
import com.zqkj.service.CommissionService;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 佣金表
 */
@Controller
@RequestMapping("/integral/commission")
@Api(value = "佣金表", tags = { "activity 佣金表" })
public class CommissionController extends BaseController<CommissionService, CommissionEntity> {

    @ResponseBody
    @RequestMapping(value = "/savecommission", method = { RequestMethod.POST})
    @ApiOperation(value = "消费后进行佣金记录（会员产品，返给邀请人）", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R addCommission(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);
        CommissionEntity commissionEntity = new CommissionEntity();
        commissionEntity.setSourceGuid(jsonObject.getStr("sourceGuid"));
        commissionEntity.setProductGuid(jsonObject.getStr("productGuid"));
        commissionEntity.setCreator(jsonObject.getStr("creator"));
        return service.addCommission(commissionEntity,jsonObject.getInt("sum"));
    }


    @ResponseBody
    @RequestMapping(value = "/selectcommission", method = { RequestMethod.POST})
    @ApiOperation(value = "查询用户佣金", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R selectUserCommission(Integer state){
        return service.selectCommissionSum(state);
    }



    @ResponseBody
    @RequestMapping(value = "/analysiscommission", method = { RequestMethod.POST})
    @ApiOperation(value = "消费后进行佣金记录（抢购分享）", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R analysisCommission(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);
        CommissionEntity commissionEntity = new CommissionEntity();
        commissionEntity.setSourceGuid(jsonObject.getStr("sourceGuid"));
        commissionEntity.setProductGuid(jsonObject.getStr("productGuid"));
        commissionEntity.setCreator(jsonObject.getStr("inviterGuid"));//邀请人guid
        return service.analysisCommission(commissionEntity,jsonObject.getInt("sum"));
    }


    @ResponseBody
    @RequestMapping(value = "/cashwithdrawal", method = { RequestMethod.POST})
    @ApiOperation(value = "提现佣金先进行状态修改", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R cashWithdrawal(@RequestBody String jsonStr){
        if(service.cashWithdrawal(jsonStr)){
            return R.ok().putData(jsonStr);
        }else{
            return R.error(Content.STATUS_CODE_5006,"失败");
        }
    }



    @ResponseBody
    @RequestMapping(value = "/conduct", method = { RequestMethod.POST})
    @ApiOperation(value = "进行提现操作先根据订单guid修改佣金状态", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R conduct(@RequestBody String orderGuid){
        if(service.conduct(orderGuid)){
            return R.ok().putData(orderGuid);
        }else{
            return R.error(Content.STATUS_CODE_5006,"失败");
        }
    }

}
