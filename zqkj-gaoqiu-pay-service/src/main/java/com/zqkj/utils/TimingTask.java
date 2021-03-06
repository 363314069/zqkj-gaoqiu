package com.zqkj.utils;

import com.zqkj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by zqkj
 * 定时任务
 */
@Component
@Controller
@EnableAsync
public class TimingTask {

    @Autowired
    private OrderService orderService;

    public final static long UPLOAD_Minute =  60000;

    public final static long UPLOAD_Minute_Time =  60000 * 10;


    /*//订单超时取消
    @Scheduled(fixedDelay = UPLOAD_Minute)
    public void scheduled1() {
        orderService.timeoutOrderCancel();
    }


    //球场订单待处理再次进行通知
    @Scheduled(fixedDelay = UPLOAD_Minute_Time)
    public void scheduled2() {
        orderService.untreatedOrderNotice();
    }


    //拼团结束后进行处理
    @Scheduled(fixedDelay = UPLOAD_Minute)
    public void scheduled3() {
        orderService.groupBuyingBusiness();
    }*/
}
