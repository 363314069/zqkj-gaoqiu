package com.zqkj.controller;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.CouponsdateteatimeEntity;
import com.zqkj.service.CouponsdateteatimeService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 
 * 商品每天T-time设置
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-17 17:31:53
 */
@Controller
@RequestMapping("/business/couponsdateteatime")
@Api(value = "", tags = { "business/couponsdateteatime " })
public class CouponsdateteatimeController extends BaseController<CouponsdateteatimeService, CouponsdateteatimeEntity> {
    /**
     * 批量修改添加
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatelist", method = {RequestMethod.POST})
    public R updateCouponsdateteatimeList(@RequestBody List<CouponsdateteatimeEntity> entities) {
        List<CouponsdateteatimeEntity> entityList = service.updateCouponsdateteatimeList(entities);
        if(entityList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(entityList);
        }
    }
}
