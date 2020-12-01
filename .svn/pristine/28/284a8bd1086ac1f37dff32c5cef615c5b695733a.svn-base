package com.zqkj.controller;

import cn.hutool.json.JSONObject;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.ReservationdateEntity;
import com.zqkj.service.ReservationdateService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 
 * 每天订场设置
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:09:36
 */
@Controller
@RequestMapping("/business/reservationdate")
@Api(value = "", tags = { "business/reservationdate " })
public class ReservationdateController extends BaseController<ReservationdateService, ReservationdateEntity> {

    /**
     * 查询每天订场设置集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectlist", method = {RequestMethod.POST})
    public R selectReservationdateList(@RequestBody ReservationdateEntity reservationdateEntity) {
        List<ReservationdateEntity> entityList = service.selectReservationdateList(reservationdateEntity);
        if(entityList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(entityList);
        }
    }


    /**
     * 批量修改添加
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatelist", method = {RequestMethod.POST})
    public R selectReservationdateList(@RequestBody List<ReservationdateEntity> reservationdateList) {
        List<ReservationdateEntity> entityList = service.updateReservationdateList(reservationdateList);
        if(entityList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(entityList);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectOne(@RequestBody String json) {
        JSONObject jsons = new JSONObject(json);
        ReservationdateEntity reservationdateEntity = new ReservationdateEntity();
        reservationdateEntity.setIntroductionGuid(jsons.getStr("guid"));
        reservationdateEntity.setDate(jsons.getStr("date"));
        ReservationdateEntity entity = service.selectOne(reservationdateEntity);
        if(entity == null){
            return R.ok().putData(entity);
        }else{
            return R.ok().putData(entity);
        }
    }


    /**
     * 批量修改添加指定日期中的周末价格
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatelistweekend", method = {RequestMethod.POST})
    public R updateListWeekend(@RequestBody ReservationdateEntity reservationdateEntity) {
        List<ReservationdateEntity> entityList = service.updateListWeekend(reservationdateEntity);
        if(entityList == null){
            return R.error(Content.STATUS_CODE_5005);
        }else{
            return R.ok().putData(entityList);
        }
    }
}
