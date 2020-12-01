package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "integral-Service",path = "/integral/userextend")
public interface UserExtendRemote {

    //取消订单余额增加
    @RequestMapping(value = "/cancelorderbalance", method = RequestMethod.POST)
    public String cancelOrderBalance(@RequestBody String jsonStr);


    //订单金币抵扣
    @RequestMapping(value = "/usegold", method = RequestMethod.POST)
    public String useGold(@RequestBody String jsonStr);
}
