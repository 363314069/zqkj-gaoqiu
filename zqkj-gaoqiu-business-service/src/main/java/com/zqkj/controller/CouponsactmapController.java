package com.zqkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.CouponsactmapEntity;
import com.zqkj.service.CouponsactmapService;

import io.swagger.annotations.Api;


/**
 * 
 * 优惠券商品关联
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
@Controller
@RequestMapping("/business/couponsactmap")
@Api(value = "", tags = { "business/couponsactmap " })
public class CouponsactmapController extends BaseController<CouponsactmapService, CouponsactmapEntity> {

}
