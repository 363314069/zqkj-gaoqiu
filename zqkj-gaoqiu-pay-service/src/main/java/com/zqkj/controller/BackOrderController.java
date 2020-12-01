package com.zqkj.controller;


import com.zqkj.bean.BackOrderParameteBean;
import com.zqkj.entity.OrderEntity;
import com.zqkj.service.BackOrderService;
import com.zqkj.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 后台代订
 */
@Controller
@RequestMapping("/pay/backorder")
@Api(value = "后台代订", tags = { "order 订单表" })
public class BackOrderController extends BaseController<BackOrderService, OrderEntity> {

    /**
     * 创建酒店预订订单
     * @param hotelOrderParameteBean 酒店订单参数
     * @return
     */
    @RequestMapping(value = "/addhotelorder", method = {RequestMethod.POST})
    @ResponseBody
    public R addHotelOrder(BackOrderParameteBean hotelOrderParameteBean){
        return service.addHotelOrder(hotelOrderParameteBean);
    }


    /**
     * 创建订场订单并支付
     * @param ionorderOrderParameteBean
     * @return
     */
    @RequestMapping(value = "/addintroductionorderpay", method = {RequestMethod.POST})
    @ResponseBody
    public R addIntroductionOrderPay(BackOrderParameteBean ionorderOrderParameteBean){
        return service.addIntroductionOrderPay(ionorderOrderParameteBean);
    }


    @ResponseBody
    @RequestMapping(value = "/businconfi", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "商家后台酒店订单操作", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
    public R businessConfirm(@RequestBody OrderEntity entity) {
        return service.businessConfirm(entity);
    }

}
