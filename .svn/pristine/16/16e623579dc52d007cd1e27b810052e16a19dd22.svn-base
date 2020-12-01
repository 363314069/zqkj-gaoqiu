package com.zqkj.service.impl;

import cn.hutool.json.JSONObject;
import com.zqkj.entity.UserExtendEntity;
import com.zqkj.remote.ActivityRemote;
import com.zqkj.service.UserExtendService;
import com.zqkj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.GoldDao;
import com.zqkj.entity.GoldEntity;
import com.zqkj.service.GoldService;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-22 11:21:50
 */
@Service("goldService")
public class GoldServiceImpl extends BaseServiceImpl<GoldDao, GoldEntity> implements GoldService {

    @Autowired
    private ActivityRemote activityRemote;

    @Autowired
    private UserExtendService userExtendService;

    @Override
    public Integer productCashBack(GoldEntity goldEntity) {
//保障这俩个参数不为空才可以：用户guid   Creator     //订单guid   SourceGuid   //数量sum   sum

        //根据产品GUID查询到产品
        String activity = activityRemote.selectOne(goldEntity.getProductGuid());
        JSONObject activityJson = new JSONObject(activity);
        JSONObject dataJson = activityJson.getJSONObject("data");
        //判断该产品是否有金币返现
        if(dataJson.getInt("backGoldSum") != null && dataJson.getInt("backGoldSum") > 0){
            int goldInsert = 0;
            for(int i=0;i<goldEntity.getSum();i++){
                //有金币返现  获取金币数量插入gold表中
                GoldEntity goldEntityInsert = new GoldEntity();
                goldEntityInsert.setSourceGuid(goldEntity.getSourceGuid());
                goldEntityInsert.setCreator(goldEntity.getCreator());
                goldEntityInsert.setType(1);
                goldEntityInsert.setState(1);
                goldEntityInsert.setGold(dataJson.getInt("backGoldSum"));//金币数量
                goldInsert = insertSelective(goldEntityInsert);
                //插入成功后修改用户扩展表记录用户金币数量
                UserExtendEntity userExtendEntity = new UserExtendEntity();
                userExtendEntity.setType(5);//5金币
                userExtendEntity.setUserGUID(goldEntityInsert.getCreator());
                userExtendEntity = userExtendService.selectOne(userExtendEntity);
                if(userExtendEntity == null){
                    UserExtendEntity userExtendInsert = new UserExtendEntity();
                    userExtendInsert.setState(1);
                    userExtendInsert.setExtLong(goldEntityInsert.getGold());
                    userExtendInsert.setType(5);
                    userExtendInsert.setUserGUID(goldEntityInsert.getCreator());
                    userExtendService.insertSelective(userExtendInsert);
                }else{
                    userExtendEntity.setExtLong(goldEntityInsert.getGold()+userExtendEntity.getExtLong());
                    userExtendService.updateByPrimaryKeySelective(userExtendEntity);
                }
                System.out.println("个人扩展表添加数据成功");
            }
            return goldInsert;
        }else{
            //没有金币返现  直接return
            return 0;
        }
    }
}
