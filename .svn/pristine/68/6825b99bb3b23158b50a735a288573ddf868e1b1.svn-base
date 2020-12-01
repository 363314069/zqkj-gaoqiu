package com.zqkj.controller;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.CouponsEntity;
import com.zqkj.service.CouponsService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 优惠券
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
@Controller
@RequestMapping("/business/coupons")
@Api(value = "", tags = { "business/coupons " })
public class CouponsController extends BaseController<CouponsService, CouponsEntity> {


    @ResponseBody
    @RequestMapping(value = "/savecoupons", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据", notes = "参数为json对像")
    @SysLog("保存数据")
    public R saveCoupons(@RequestBody CouponsEntity entity) {
        Integer count = service.saveCoupons(entity);
        return R.ok().putData(entity).put("count", count);
    }


    @ResponseBody
    @RequestMapping(value = "/updatecoupons", method = RequestMethod.POST)
    @ApiOperation(value = "修改数据", notes = "参数为json对像")
    @SysLog("修改数据")
    public R updateCoupons(@RequestBody CouponsEntity entity) {
        Integer count = service.updateCoupons(entity);
        return R.ok().putData(entity).put("count", count);
    }


    @ResponseBody
    @RequestMapping(value = "/getcoupons", method = RequestMethod.POST)
    @ApiOperation(value = "用户领取", notes = "优惠券guid")
    @SysLog("用户领取")
    public R getCoupons(String guid) {
        return service.getCoupons(guid);
    }


    @ResponseBody
    @RequestMapping(value = "/orgcouponslist", method = {RequestMethod.POST})
    @ApiOperation(value = "查询商家优惠券和全场通用和指定商品的优惠券", notes = "参数为数组如[1,2,3,4]")
    public R orgCouponsList() {
        return R.ok().putData(service.selectOrgList());
    }



    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<CouponsEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }


    @ResponseBody
    @RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
    public R selectListPage(PageUtil<CouponsEntity> page, CouponsEntity entity) {
        // 查询列表数据
        page = service.selectListPage(page, entity);
        return R.ok().putData(page.getList()).put("count", page.getCount());
    }
}
