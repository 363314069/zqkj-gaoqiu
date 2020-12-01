package com.zqkj.controller;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.ReservationEntity;
import com.zqkj.service.ReservationService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 
 * 球场表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 10:49:45
 */
@Controller
@RequestMapping("/business/reservation")
@Api(value = "", tags = { "business/reservation " })
public class ReservationController extends BaseController<ReservationService, ReservationEntity> {

    /**
     * 查询订场详细
     * @param guid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectonebyguid", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectOneByGuid(String guid) {
        ReservationEntity reservationEntity = service.selectReservationByGuid(guid);
        if(reservationEntity == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(reservationEntity);
        }
    }


    /**
     * 条件查询球场
     * @param reservationEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectReservationList(ReservationEntity reservationEntity) {
        List<ReservationEntity> reservationList = service.selectReservationList(reservationEntity);
        if(reservationList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(reservationList);
        }
    }



    /**
     * 条件查询返回场地价格最低的List
     * @param reservationEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectLowestPrice", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectLowestPrice(ReservationEntity reservationEntity) {
        List<ReservationEntity> reservationList = service.selectLowestPrice(reservationEntity);
        if(reservationList == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(reservationList);
        }
    }
}
