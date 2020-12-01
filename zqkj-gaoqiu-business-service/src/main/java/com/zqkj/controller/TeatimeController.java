package com.zqkj.controller;

import com.zqkj.entity.TeatimeGroupByEntity;
import com.zqkj.remote.service.WxService;
import com.zqkj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.TeatimeEntity;
import com.zqkj.service.TeatimeService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 
 * 时间表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:14:20
 */
@Controller
@RequestMapping("/business/teatime")
@Api(value = "", tags = { "business/teatime " })
public class TeatimeController extends BaseController<TeatimeService, TeatimeEntity> {
    /**
     * 订场（没有设定时间），场地单独回调
     */
    @RequestMapping(value = "/introductioncallback", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public R introductionCallback(TeatimeEntity teatimeEntity){
        return service.introductionCallback(teatimeEntity);
    }


    /**
     * 条件查询时间表
     */
    @RequestMapping(value = "/selectList", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public R selectList(@RequestBody TeatimeEntity teatimeEntity){
        return R.ok().putData(service.selectList(teatimeEntity));
    }


    /**
     * 手机端开启关闭时间段功能
     */
    @RequestMapping(value = "/setUpDateTime", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public R setUpDateTime(String map,String introductionGuid,Integer type){
        return service.setUpDateTime(map,introductionGuid,type);
    }


    /**
     * 分组查询
     */
    @RequestMapping(value = "/selectDateTimeGroupBy", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public R selectDateTimeGroupBy(String startDate,String endDate,String introductionGuid,Integer type){
        List<TeatimeGroupByEntity> list = service.selectDateTimeGroupBy(startDate,endDate,introductionGuid,type);
        return R.ok().putData(list);
    }
}
