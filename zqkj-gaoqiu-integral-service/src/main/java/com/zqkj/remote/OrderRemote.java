package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "pay-Service",path = "/pay/order")
public interface OrderRemote {

    @RequestMapping(value = "/callbackwithdrawal", method = {RequestMethod.POST})
    public String callbackWithdrawal(@RequestBody String strJson);
}
