package com.zqkj.controller;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.entity.ApplyEntity;
import com.zqkj.service.ActivityService;
import com.zqkj.service.ApplyService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.R;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 报名表
 */
@Controller
@RequestMapping("/business/apply")
@Api(value = "报名表", tags = { "activity 报名表" })
public class ApplyController extends BaseController<ApplyService, ApplyEntity> {

    @Autowired
    protected ApplyService applyService;

    @ResponseBody
    @RequestMapping(value = "/activityapply", method = RequestMethod.POST)
    @ApiOperation(value = "活动报名", notes = "参数为json对像")
    @SysLog("活动报名")
    public R activityApply(String activityGuid) {
        //查询人员是否已经报过名  不可重复报名
        ApplyEntity applyEntity = new ApplyEntity();
        applyEntity.setUserGuid(BaseContentHandler.getUserGuid());
        applyEntity.setActivityGuid(activityGuid);
        List<ApplyEntity> applyEntityList = applyService.select(applyEntity);
        if(applyEntityList.size() > 0){
            return R.error("您已经报名，不可重复报名！");
        }
        applyEntity.setType(0);
        applyEntity.setState(0);
        Integer count = applyService.insertSelective(applyEntity);
        if(count > 0){
            return R.ok();
        }else{
            return R.error("报名失败");
        }
    }
}
