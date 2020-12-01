package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "integral-Service",path = "/integral/integral")
public interface IntegralRemote {

    @RequestMapping(value = "/integralRecurrence", method = RequestMethod.POST)
    public String integralRecurrence(@RequestBody String jsonStr);
}
