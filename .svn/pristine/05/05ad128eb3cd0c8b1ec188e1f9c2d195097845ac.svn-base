package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "integral-Service",path = "/integral/commission")
public interface CommissionRemote {

    @RequestMapping(value = "/savecommission", method = RequestMethod.POST)
    public String addCommission(@RequestBody String jsonStr);

    @RequestMapping(value = "/analysiscommission", method = RequestMethod.POST)
    public String analysisCommission(@RequestBody String jsonStr);

    @RequestMapping(value = "/cashwithdrawal", method = RequestMethod.POST)
    public String cashWithdrawal(@RequestBody String jsonStr);

    @RequestMapping(value = "/conduct", method = RequestMethod.POST)
    public String conduct(@RequestBody String orderGuid);
}
