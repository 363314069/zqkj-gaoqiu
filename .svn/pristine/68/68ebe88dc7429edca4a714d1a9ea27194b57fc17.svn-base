package com.zqkj.controller;

import com.zqkj.entity.WxBeingPushedEntity;
import com.zqkj.service.WxBeingPushedService;
import com.zqkj.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zqkj on 2019/9/25.
 */
@Controller
@RequestMapping("/security/wxpushed")
public class WxBeingPushedController {

    @Autowired
    private WxBeingPushedService wxBeingPushedService;

    /**
     * 后台群发消息
     * @return
     */
    @RequestMapping(value = "/beingpushed", method = RequestMethod.POST)
    @ApiOperation(value = "后台微信公众号群发消息")
    @ResponseBody
    public R beingPushed(WxBeingPushedEntity wxBeingPushedEntity) {
        int nRet = wxBeingPushedService.beingPushed(wxBeingPushedEntity);
        if(nRet > 0){
            return R.ok().putData(nRet);
        }else{
            return R.ok().putData(nRet);
        }
    }
}
