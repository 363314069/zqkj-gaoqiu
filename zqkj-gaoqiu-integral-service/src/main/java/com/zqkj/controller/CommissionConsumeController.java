package com.zqkj.controller;

import com.zqkj.entity.CommissionConsumeEntity;
import com.zqkj.service.CommissionConsumeService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 佣金消费表
 */
@Controller
@RequestMapping("/integral/commissionconsume")
@Api(value = "佣金消费表", tags = { "activity 佣金消费表" })
public class CommissionConsumeController extends BaseController<CommissionConsumeService, CommissionConsumeEntity> {

}
