package com.zqkj.service.impl;

import cn.hutool.json.JSONObject;
import com.zqkj.dao.mapper.IntegralDao;
import com.zqkj.entity.IntegralEntity;
import com.zqkj.entity.UserExtendEntity;
import com.zqkj.remote.ActivityRemote;
import com.zqkj.service.IntegralService;
import com.zqkj.service.UserExtendService;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 积分表
 */
@Service("integralService")
public class IntegralServiceImpl extends BaseServiceImpl<IntegralDao, IntegralEntity> implements IntegralService {

    @Autowired
    private ActivityRemote activityRemote;

    @Autowired
    private UserExtendService userExtendService;

    @Override
    public Long selectIntegralSum(IntegralEntity integralEntity) {
        return dao.selectIntegralSum(integralEntity);
    }

    @Override
    public R integralRecurrence(String strJson) {
        JSONObject jsonObject = new JSONObject(strJson);
        //根据产品guid 查询 场地信息
        String integral = activityRemote.selectOneIntegral(jsonObject.getStr("productGuid"));
        JSONObject integralJson = new JSONObject(integral);
        JSONObject integralJsonData = integralJson.getJSONObject("data");
        if(integralJsonData != null ){
            if(integralJsonData.getInt("integralSum") != null && integralJsonData.getInt("integralSum") > 0){
                int IntegraSum = integralJsonData.getInt("integralSum") * jsonObject.getInt("sum");
                IntegralEntity integralEntity = new IntegralEntity();
                integralEntity.setSourceGuid(jsonObject.getStr("sourceGuid"));
                integralEntity.setIntegral(IntegraSum);
                integralEntity.setType(1);
                integralEntity.setState(1);
                integralEntity.setCreator(jsonObject.getStr("creator"));
                int nRet = insertSelective(integralEntity);
                if(nRet > 0){
                    UserExtendEntity userExtendEntity = new UserExtendEntity();
                    userExtendEntity.setType(2);
                    userExtendEntity.setUserGUID(jsonObject.getStr("creator"));
                    userExtendEntity = userExtendService.selectOne(userExtendEntity);
                    if(userExtendEntity == null){
                        UserExtendEntity userExtendInsert = new UserExtendEntity();
                        userExtendInsert.setState(1);
                        userExtendInsert.setExtLong(IntegraSum);
                        userExtendInsert.setType(2);
                        userExtendInsert.setUserGUID(jsonObject.getStr("creator"));
                        userExtendService.insertSelective(userExtendInsert);
                        System.out.println("个人扩展表添加订场返现积分成功");
                        return R.ok().putData(userExtendInsert);
                    }else{
                        userExtendEntity.setExtLong(IntegraSum+userExtendEntity.getExtLong());
                        userExtendService.updateByPrimaryKeySelective(userExtendEntity);
                        System.out.println("个人扩展表添加订场返现积分成功！！！");
                        return R.ok().putData(userExtendEntity);
                    }
                }
                System.out.println("订场返现积分添加数据失败");
                return R.error(Content.STATUS_CODE_5005,"订场返现积分添加数据失败");
            }
            System.out.println("该产品没有积分返现");
            return R.ok().putData("该产品没有积分返现");
        }
        System.out.println("没有查询到该产品");
        return R.ok().putData("没有查询到该产品");
    }
}
