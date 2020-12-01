package com.zqkj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zqkj.entity.GoldEntity;
import com.zqkj.entity.GoldconsumeEntity;
import com.zqkj.service.GoldService;
import com.zqkj.service.GoldconsumeService;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.UserExtendDao;
import com.zqkj.entity.UserExtendEntity;
import com.zqkj.service.UserExtendService;

import java.util.List;


/**
 * 
 * 用户扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
@Service("userExtendService")
public class UserExtendServiceImpl extends BaseServiceImpl<UserExtendDao, UserExtendEntity> implements UserExtendService {


    @Autowired
    private GoldconsumeService goldconsumeService;

    @Autowired
    private GoldService goldService;

    @Override
    public R cancelOrderBalance(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String orderGuid = jsonObject.getString("orderGuid");//订单guid
        String userGuid = jsonObject.getString("userGuid");//用户guid
        Integer orderMoney = jsonObject.getInteger("payMoney");//订单金额

        //根据订单guid查询是否使用了金币
        GoldconsumeEntity goldconsumeEntity = new GoldconsumeEntity();
        goldconsumeEntity.setSourceGuid(orderGuid);
        goldconsumeEntity = goldconsumeService.selectOne(goldconsumeEntity);

        if(goldconsumeEntity != null){
            GoldEntity goldEntity = new GoldEntity();
            goldEntity.setSourceGuid(orderGuid);
            goldEntity.setType(2);
            goldEntity.setState(1);
            goldEntity.setGold(goldconsumeEntity.getGold());
            goldService.insertSelective(goldEntity);

            //查询用户扩展表中是否有金币数据，没有添加，有进行修改。原则使用了金币扣款不可能没有金币数据，防止报错
            UserExtendEntity userGold = new UserExtendEntity();
            userGold.setUserGUID(userGuid);
            userGold.setType(5);
            userGold = selectOne(userGold);
            if(userGold == null){
                UserExtendEntity userGoldInsert = new UserExtendEntity();
                userGoldInsert.setUserGUID(userGuid);
                userGoldInsert.setType(5);
                userGoldInsert.setState(1);
                userGoldInsert.setExtLong(goldconsumeEntity.getGold());
                insertSelective(userGoldInsert);
            }else{
                userGold.setExtLong(userGold.getExtLong() + goldconsumeEntity.getGold());
                updateByPrimaryKeySelective(userGold);
            }
        }

        Integer state = jsonObject.getInteger("state");//订单订单支付状态，如果支付了进行余额添加，没有支付判断是否使用了金币增加对应金币   1支付  0未支付

        if(state != null && state == 1 && orderMoney != null){
            UserExtendEntity userExtendEntity = new UserExtendEntity();
            userExtendEntity.setUserGUID(userGuid);
            userExtendEntity.setType(4);
            userExtendEntity = selectOne(userExtendEntity);
            if(userExtendEntity == null){
                UserExtendEntity userExtendInsert = new UserExtendEntity();
                userExtendInsert.setUserGUID(userGuid);
                userExtendInsert.setType(4);
                userExtendInsert.setState(1);
                userExtendInsert.setExtLong(orderMoney);
                int nRet = insertSelective(userExtendInsert);
                if(nRet > 0){
                    return R.ok().putData(userExtendInsert);
                }else{
                    return R.error(Content.STATUS_CODE_5005,"添加失败");
                }
            }else{
                userExtendEntity.setExtLong(userExtendEntity.getExtLong()+orderMoney);
                int nRet = updateByPrimaryKeySelective(userExtendEntity);
                if(nRet > 0){
                    return R.ok().putData(userExtendEntity);
                }else{
                    return R.error(Content.STATUS_CODE_5005,"修改失败");
                }
            }
        }else{
            return R.ok();
        }
    }

    @Override
    public Integer useGold(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String orderGuid = jsonObject.getString("orderGuid");//订单guid
        String userGuid = jsonObject.getString("userGuid");//用户guid
        Integer orderMoney = jsonObject.getInteger("orderMoney");//订单金额
        Integer goldSum = jsonObject.getInteger("goldSum");//使用金币数量

        if(goldSum > 0 && orderMoney > 0){
            //查询用户的金币
            UserExtendEntity userExtendEntity = new UserExtendEntity();
            userExtendEntity.setType(5);
            userExtendEntity.setUserGUID(userGuid);
            userExtendEntity = selectOne(userExtendEntity);

            //判断用户金币是否大于使用金币
            if(userExtendEntity != null && userExtendEntity.getExtLong() >= goldSum){
                //插入金币消费币数据
                GoldconsumeEntity goldconsumeEntity = new GoldconsumeEntity();
                goldconsumeEntity.setType(1);
                goldconsumeEntity.setState(1);
                goldconsumeEntity.setSourceGuid(orderGuid);
                goldconsumeEntity.setGold(goldSum);
                goldconsumeService.insertSelective(goldconsumeEntity);
                //修改用户扩展表金币数量
                userExtendEntity.setExtLong(userExtendEntity.getExtLong() - goldSum);
                updateByPrimaryKeySelective(userExtendEntity);
                //订单金额减去金币，返回差值
                Integer moneyValue = orderMoney - goldSum*100;
                return moneyValue;
            }else{
                return orderMoney;
            }
        }else{
            return orderMoney;
        }
    }

    @Override
    public Integer registerGiving(String userGuid) {
        Integer nRet = 0;
        UserExtendEntity userExtendEntity = new UserExtendEntity();
        userExtendEntity.setUserGUID(userGuid);
        userExtendEntity.setType(5);//金币
        userExtendEntity = selectOne(userExtendEntity);
        if(userExtendEntity == null){
            userExtendEntity = new UserExtendEntity();
            userExtendEntity.setUserGUID(userGuid);
            userExtendEntity.setExtLong(300);
            userExtendEntity.setType(5);
            userExtendEntity.setState(1);
            nRet = insertSelective(userExtendEntity);
        }else{
            userExtendEntity.setExtLong(userExtendEntity.getExtLong()+300);
            nRet = updateByPrimaryKeySelective(userExtendEntity);
        }
        return nRet;
    }

    @Override
    public Integer binVip(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String userGuid = jsonObject.getString("userGuid");//用户GUID
        String vipGuid = jsonObject.getString("vipGuid");//会员卡GUID
        UserExtendEntity userExtendEntity = new UserExtendEntity();
        userExtendEntity.setUserGUID(userGuid);
        userExtendEntity.setType(6);//会员卡类型
        userExtendEntity.setState(1);
        userExtendEntity.setExtStr(vipGuid);//会员卡GUID
        return insertSelective(userExtendEntity);
    }

    @Override
    public List<UserExtendEntity> selectList(UserExtendEntity extendEntity) {
        return dao.selectList(extendEntity);
    }
}
