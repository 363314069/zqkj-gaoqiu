package com.zqkj.controller;

import com.zqkj.entity.OrderExtendEntity;
import com.zqkj.service.OrderExtendService;
import com.zqkj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 订单扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
@Controller
@RequestMapping("/pay/orderextend")
@Api(value = "", tags = { "pay/orderextend " })
public class OrderExtendController extends BaseController<OrderExtendService, OrderExtendEntity> {

    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("orderGUID", guidList);
        List<OrderExtendEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }
}
