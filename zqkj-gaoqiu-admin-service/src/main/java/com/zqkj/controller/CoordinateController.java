package com.zqkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.CoordinateEntity;
import com.zqkj.service.CoordinateService;

import io.swagger.annotations.Api;


/**
 * 机构坐标管理
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Controller
@RequestMapping("/security/coordinate")
@Api(value = "机构坐标管理", tags = { "security/coordinate 机构坐标管理" })
public class CoordinateController extends BaseController<CoordinateService, CoordinateEntity> {

}
