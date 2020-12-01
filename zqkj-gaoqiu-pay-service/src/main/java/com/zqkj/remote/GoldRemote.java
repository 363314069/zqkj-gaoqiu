package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "integral-Service",path = "/integral/gold")
public interface GoldRemote {

    @RequestMapping(value = "/productcashback", method = RequestMethod.POST)
    public String productCashBack(@RequestBody String jsonStr);

}
