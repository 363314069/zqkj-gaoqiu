package com.zqkj.controller;

import cn.hutool.json.JSONObject;
import com.zqkj.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.GoldEntity;
import com.zqkj.service.GoldService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 金币表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-22 11:21:50
 */
@Controller
@RequestMapping("/integral/gold")
@Api(value = "", tags = { "integral/gold " })
public class GoldController extends BaseController<GoldService, GoldEntity> {


    @ResponseBody
    @RequestMapping(value = "/productcashback", method = { RequestMethod.POST})
    @ApiOperation(value = "金币返现", notes = "参数为对像的变量,如{id:1,guid:\"ssssss-ssss-sss\"}")
    public R productCashBack(@RequestBody String jsonStr){
        System.out.println("进入返现金币接口");
        JSONObject jsonObject = new JSONObject(jsonStr);
        GoldEntity goldEntity = new GoldEntity();
        goldEntity.setSourceGuid(jsonObject.getStr("sourceGuid"));//订单guid
        goldEntity.setCreator(jsonObject.getStr("userGuid"));//用户guid
        goldEntity.setProductGuid(jsonObject.getStr("productGuid"));//产品guid
        goldEntity.setSum(jsonObject.getInt("sum"));//数量
        int nRet = service.productCashBack(goldEntity);
        System.out.println("return金币返现接口："+nRet);
        return R.ok().putData(nRet);
    }
}
