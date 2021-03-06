package com.zqkj.controller;

import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.IntroductionEntity;
import com.zqkj.service.IntroductionService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 
 * 场地信息表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:02:40
 */
@Controller
@RequestMapping("/business/introduction")
@Api(value = "", tags = { "business/introduction " })
public class IntroductionController extends BaseController<IntroductionService, IntroductionEntity> {
    /**
     * 查询每天订场设置集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectIntroductionList(IntroductionEntity introductionEntity) {
        List<IntroductionEntity> entityList = service.selectIntroductionList(introductionEntity);
        if(entityList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(entityList);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectOne(@RequestBody String guid) {
        IntroductionEntity userEntity = service.selectOneByGuid(guid);
        if(userEntity == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(userEntity);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<IntroductionEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }


    @ResponseBody
    @RequestMapping(value = "/saveintroduction", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据", notes = "参数为json对像")
    @SysLog("保存数据")
    public R saveIntroduction(@RequestBody IntroductionEntity entity) {
        Integer count = service.saveIntroduction(entity);
        return R.ok().putData(entity).put("count", count);
    }


    @ResponseBody
    @RequestMapping(value = "/updateintroduction", method = RequestMethod.POST)
    @ApiOperation(value = "修改数据", notes = "参数为json对像")
    @SysLog("修改数据")
    public R updateIntroduction(@RequestBody IntroductionEntity entity) {
        Integer count = service.updateIntroduction(entity);
        return R.ok().putData(entity).put("count", count);
    }



    @ResponseBody
    @RequestMapping(value = "/listByReservations", method = {RequestMethod.POST})
    @ApiOperation(value = "根据球场guids查询场地数据", notes = "参数为数组如[1,2,3,4]")
    public R listByReservations(String[] reservationGuids,String type, String state,String orderBy) {
        Example example = new Example(getClazz());
        if(!StringUtil.isEmpty(orderBy)){
            example.orderBy(orderBy);
        }
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(reservationGuids));
        criteria.andIn("reservationGuid", guidList);
        criteria.andGreaterThanOrEqualTo("endTime", new Date());
        if(!StringUtil.isEmpty(type)){
            criteria.andEqualTo("type",type);
        }
        if(!StringUtil.isEmpty(state)){
            criteria.andEqualTo("state",state);
        }
        List<IntroductionEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }


    @ResponseBody
    @RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
    public R selectListPage(PageUtil<IntroductionEntity> page, IntroductionEntity entity) {
        // 查询列表数据
        page = service.selectListPage(page, entity);
        return R.ok().putData(page.getList()).put("count", page.getCount());
    }


    @ResponseBody
    @RequestMapping(value = "/normaltimelist", method = {RequestMethod.POST})
    @ApiOperation(value = "根据球场GUID查询对应产品列表", notes = "")
    public R normalTimeList(String reservationGuid,Integer state) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("reservationGuid", reservationGuid);
        if(state != null){
            criteria.andEqualTo("state", state);
        }
        criteria.andLessThanOrEqualTo("startTime",new Date());
        criteria.andGreaterThanOrEqualTo("endTime",new Date());
        List<IntroductionEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }

}
