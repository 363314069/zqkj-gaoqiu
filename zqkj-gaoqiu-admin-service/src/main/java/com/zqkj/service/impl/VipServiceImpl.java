package com.zqkj.service.impl;

import com.zqkj.bean.OrderVipBean;
import com.zqkj.dao.mapper.VipDao;
import com.zqkj.entity.VipEntity;
import com.zqkj.remote.OrderRemote;
import com.zqkj.service.VipService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 会员卡
 */
@Service("vipService")
public class VipServiceImpl extends BaseServiceImpl<VipDao, VipEntity> implements VipService {

    @Autowired
    private OrderRemote orderRemote;


    @Override
    public synchronized R bindingVipCode(VipEntity vipEntity) {


        vipEntity.setEndTime(fiveYears());
        //生成卡号  Code 86000011 + 100001
        Integer code = dao.selectMaxCode(vipEntity.getAreaCode());
        if(code == null){
            vipEntity.setCode(100001);
        }else{
            vipEntity.setCode(code + 1);
        }
        vipEntity.setType(0);
        vipEntity.setState(1);
        //支付成功后回调没有用户
        if(StringUtil.isEmpty(vipEntity.getCreator())){
            vipEntity.setUserGuid(BaseContentHandler.getUserGuid());
        }else{
            vipEntity.setUserGuid(vipEntity.getCreator());
        }
        this.insertSelective(vipEntity);
        OrderVipBean orderVipBean = new OrderVipBean();
        orderVipBean.setOrderGuid(vipEntity.getOrderGuid());
        orderVipBean.setVipGuid(vipEntity.getGuid());
        String updateOrder = orderRemote.updateVipGuid(orderVipBean);
        System.out.println("updateOrder："+updateOrder);
        return R.ok().putData(vipEntity);
    }

    @Override
    public R weeksCard(VipEntity vipEntity) {
        vipEntity.setEndTime("2020-10-30 23:59:59");
        //生成卡号  Code 86000011 + 100001
        Integer code = dao.selectMaxCode(vipEntity.getAreaCode());
        if(code == null){
            vipEntity.setCode(100001);
        }else{
            vipEntity.setCode(code + 1);
        }
        vipEntity.setType(1);
        vipEntity.setState(1);
        //支付成功后回调没有用户
        if(StringUtil.isEmpty(vipEntity.getCreator())){
            vipEntity.setUserGuid(BaseContentHandler.getUserGuid());
        }else{
            vipEntity.setUserGuid(vipEntity.getCreator());
        }
        this.insertSelective(vipEntity);
        OrderVipBean orderVipBean = new OrderVipBean();
        orderVipBean.setOrderGuid(vipEntity.getOrderGuid());
        orderVipBean.setVipGuid(vipEntity.getGuid());
        String updateOrder = orderRemote.updateVipGuid(orderVipBean);
        System.out.println("updateOrder："+updateOrder);
        return R.ok().putData(vipEntity);
    }


    public static String fiveYears(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 5); //年份减5
        String start =format.format(c.getTime());
        return start;
    }
}
