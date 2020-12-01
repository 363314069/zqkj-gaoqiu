package com.zqkj.service;

import com.zqkj.entity.UserExtendEntity;
import com.zqkj.utils.R;

import java.util.List;

/**
 * 
 * 用户扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
public interface UserExtendService extends BaseService<UserExtendEntity> {

    //取消订单余额增加
    public R cancelOrderBalance(String jsonStr);

    //订单金币抵扣   返回订单金额减去金币后的差值
    public Integer useGold(String jsonStr);

    //新用户注册送300金币
    public Integer registerGiving(String userGuid);

    //验证会员绑定会员卡
    public Integer binVip(String jsonStr);

    //条件查询返回所有字段
    public List<UserExtendEntity> selectList(UserExtendEntity extendEntity);
}
