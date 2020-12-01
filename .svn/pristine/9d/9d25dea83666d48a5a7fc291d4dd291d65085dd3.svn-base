package com.zqkj.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zqkj.dao.mapper.CommissionDao;
import com.zqkj.entity.CommissionEntity;
import com.zqkj.entity.UserExtendEntity;
import com.zqkj.remote.ActivityRemote;
import com.zqkj.remote.InviteRemote;
import com.zqkj.remote.OrderRemote;
import com.zqkj.service.CommissionService;
import com.zqkj.service.IntegralService;
import com.zqkj.service.UserExtendService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 佣金表
 */
@Service("commissionService")
public class CommissionServiceImpl extends BaseServiceImpl<CommissionDao, CommissionEntity> implements CommissionService {

    @Autowired
    private ActivityRemote activityRemote;

    @Autowired
    private InviteRemote inviteRemote;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private OrderRemote orderRemote;

    @Override
    public R addCommission(CommissionEntity commissionEntity,Integer sum) {
        System.out.println("进入会员购买佣金接口");
        //查询邀请人
        String inviter = inviteRemote.selectInviter(commissionEntity.getCreator());
        JSONObject inviterJson = new JSONObject(inviter);
        JSONObject userJson = inviterJson.getJSONObject("data");
        if(userJson != null && !StringUtil.isEmpty(userJson.getStr("invitedGuid"))){
            //查询用户是否为分销商类型
            String userJsonStr = inviteRemote.selectOne(userJson.getStr("invitedGuid"));
            JSONObject userJsonObj = new JSONObject(userJsonStr);
            JSONObject user = userJsonObj.getJSONObject("data");

            //查询产品表中数据佣金金额
            String activity = activityRemote.selectOne(commissionEntity.getProductGuid());
            JSONObject activityJson = new JSONObject(activity);
            JSONObject dataJson = activityJson.getJSONObject("data");
            //佣金不为0 同时为会员产品  进行记录
            if(dataJson.getInt("type") != null && dataJson.getInt("type") == 3 && user != null && user.getInt("type") == 1){//会员产品才执行,邀请人为分销商
                if(dataJson.getInt("commission") != null && dataJson.getInt("commission") > 0){
                    Integer commissionTot = dataJson.getInt("commission");
                    if(sum != null && sum > 0){
                        commissionTot = commissionTot*sum;
                    }
                    commissionEntity.setState(0);
                    commissionEntity.setType(0);
                    commissionEntity.setIntegral(commissionTot); //佣金金额
                    commissionEntity.setCreator(userJson.getStr("invitedGuid"));   //邀请人
                    //把数据插入到佣金记录表
                    int nRet = insertSelective(commissionEntity);
                    if(nRet > 0){
                        System.out.println("佣金记录添加成功");
                        UserExtendEntity userExtendEntity = new UserExtendEntity();
                        userExtendEntity.setType(1);
                        userExtendEntity.setUserGUID(commissionEntity.getCreator());
                        userExtendEntity = userExtendService.selectOne(userExtendEntity);
                        if(userExtendEntity == null){
                            UserExtendEntity userExtendInsert = new UserExtendEntity();
                            userExtendInsert.setState(1);
                            userExtendInsert.setExtLong(commissionEntity.getIntegral());
                            userExtendInsert.setType(1);
                            userExtendInsert.setUserGUID(commissionEntity.getCreator());
                            userExtendService.insertSelective(userExtendInsert);
                        }else{
                            userExtendEntity.setExtLong(commissionEntity.getIntegral()+userExtendEntity.getExtLong());
                            userExtendService.updateByPrimaryKeySelective(userExtendEntity);
                        }
                        System.out.println("个人扩展表添加数据成功");
                        return R.ok().putData(commissionEntity);
                    }else{
                        System.out.println("佣金添加数据失败");
                        return R.error(Content.STATUS_CODE_5005,"添加数据失败");
                    }
                }else{
                    System.out.println("佣金金额为零");
                    return R.ok().putData("佣金金额为零");
                }
            }else{
                System.out.println("该产品不是会员产品");
                return R.ok().putData("该产品不是会员产品");
            }
        }else {
            System.out.println("没有邀请人");
            return R.ok().putData("没有邀请人");
        }
    }

    @Override
    public R selectCommissionSum(Integer state) {
        /*Long commissionSum = 0L;//佣金
        CommissionEntity commissionEntity = new CommissionEntity();
        commissionEntity.setState(state);
        commissionEntity.setCreator(BaseContentHandler.getUserGuid());
        commissionSum = dao.selectCommissionSum(commissionEntity);*/

        /*//查询积分，暂时没有该业务，增加积分，需要对这部分处理
        IntegralEntity integralEntity = new IntegralEntity();
        integralEntity.setCreator(BaseContentHandler.getUserGuid());
        Long integralSum = integralService.selectIntegralSum(integralEntity);*/

        //查询个人扩展表
        UserExtendEntity userExtendEntity = new UserExtendEntity();
        userExtendEntity.setUserGUID(BaseContentHandler.getUserGuid());
        List<UserExtendEntity> userList = userExtendService.select(userExtendEntity);

        int balance = 0;//余额
        int gold = 0;//金币
        int commissionSum = 0;//佣金
        for(UserExtendEntity user : userList){
            if(user.getType() == 4){
                balance = user.getExtLong();
            }else if(user.getType() == 5){
                gold = user.getExtLong();
            }else if(user.getType() == 1){
                commissionSum = user.getExtLong();
            }
        }
        Map map = new HashMap();

        map.put("balance",balance);
        map.put("gold",gold);
        map.put("commission",commissionSum);
        /*if(commissionSum == null){
            map.put("commissionSum",0);
        }else{
            map.put("commissionSum",commissionSum);
        }*/
        return R.ok().putData(map);
    }

    @Override
    public R analysisCommission(CommissionEntity commissionEntity,Integer sum) {
        System.out.println("进入拥金接口");
        //查询订单是否已经产生佣金，一个订单只能生成一次佣金（防刷单）
        CommissionEntity selectCommission = new CommissionEntity();
        selectCommission.setSourceGuid(commissionEntity.getSourceGuid());
        selectCommission = selectOne(selectCommission);
        if(selectCommission == null){
            //查询产品表中数据佣金金额
            String activity = activityRemote.selectOne(commissionEntity.getProductGuid());
            JSONObject activityJson = new JSONObject(activity);
            JSONObject dataJson = activityJson.getJSONObject("data");

            //佣金不为0 进行记录
            if(dataJson.getInt("commission") != null && dataJson.getInt("commission") > 0){
                Integer commissionTot = dataJson.getInt("commission");
                if(sum != null && sum > 0){
                    commissionTot = commissionTot*sum;
                }

                commissionEntity.setState(0);
                commissionEntity.setType(0);
                commissionEntity.setIntegral(commissionTot); //佣金金额
                //把数据插入到佣金记录表
                int nRet = insertSelective(commissionEntity);
                if(nRet > 0){
                    System.out.println("佣金记录添加成功");
                    UserExtendEntity userExtendEntity = new UserExtendEntity();
                    userExtendEntity.setType(1);
                    userExtendEntity.setUserGUID(commissionEntity.getCreator());
                    userExtendEntity = userExtendService.selectOne(userExtendEntity);
                    if(userExtendEntity == null){
                        UserExtendEntity userExtendInsert = new UserExtendEntity();
                        userExtendInsert.setState(1);
                        userExtendInsert.setExtLong(commissionEntity.getIntegral());
                        userExtendInsert.setType(1);
                        userExtendInsert.setUserGUID(commissionEntity.getCreator());
                        userExtendService.insertSelective(userExtendInsert);
                    }else{
                        userExtendEntity.setExtLong(commissionEntity.getIntegral()+userExtendEntity.getExtLong());
                        userExtendService.updateByPrimaryKeySelective(userExtendEntity);
                    }
                    System.out.println("个人扩展表添加数据成功");
                    return R.ok().putData(commissionEntity);
                }else{
                    System.out.println("佣金添加数据失败");
                    return R.error(Content.STATUS_CODE_5005,"添加数据失败");
                }

                /*//佣金小于俩百直接发红包，大于存入佣金记录 start
                //判断金额范围是否在1~200之间，是的话直接提现
                if(commissionTot >= 100 && commissionTot <= 20000){
                    commissionEntity.setState(2);
                    //commissionEntity.setCashOrderGuid(orderGuid);   需要订单回调时候传入订单guid
                    commissionEntity.setIntegral(dataJson.getInt("commission")); //佣金金额
                    //把数据插入到佣金记录表
                    int nRetWithdrawal = insertSelective(commissionEntity);
                    if(nRetWithdrawal > 0) {
                        System.out.println("佣金记录添加成功(提现)");
                        //提现
                        Map<String,Object> mapCommission = new HashMap<>();
                        mapCommission.put("userGuid",commissionEntity.getCreator());
                        mapCommission.put("totalAmount",commissionTot);
                        String returnMsg = orderRemote.callbackWithdrawal(JSON.toJSONString(mapCommission));
                        cn.hutool.json.JSONObject orderJson = new cn.hutool.json.JSONObject(returnMsg);
                        if(orderJson.getInt("code") == 0){
                            commissionEntity.setCashOrderGuid(orderJson.getStr("data"));
                            updateByPrimaryKeySelective(commissionEntity);
                            System.out.println(returnMsg);
                            System.out.println("提现完成");
                            return R.ok().putData(commissionEntity);
                        }else{
                            System.out.println(returnMsg);
                            System.out.println("提现失败");
                            commissionEntity.setState(0);
                            updateByPrimaryKeySelective(commissionEntity);
                            return R.ok().putData(commissionEntity);
                        }
                    }else{
                        System.out.println("佣金添加数据失败");
                        return R.error(Content.STATUS_CODE_5005,"添加数据失败");
                    }
                }else{
                    commissionEntity.setState(0);
                    commissionEntity.setType(0);
                    commissionEntity.setIntegral(commissionTot); //佣金金额
                    //把数据插入到佣金记录表
                    int nRet = insertSelective(commissionEntity);
                    if(nRet > 0){
                        System.out.println("佣金记录添加成功");
                        UserExtendEntity userExtendEntity = new UserExtendEntity();
                        userExtendEntity.setType(1);
                        userExtendEntity.setUserGUID(commissionEntity.getCreator());
                        userExtendEntity = userExtendService.selectOne(userExtendEntity);
                        if(userExtendEntity == null){
                            UserExtendEntity userExtendInsert = new UserExtendEntity();
                            userExtendInsert.setState(1);
                            userExtendInsert.setExtLong(commissionEntity.getIntegral());
                            userExtendInsert.setType(1);
                            userExtendInsert.setUserGUID(commissionEntity.getCreator());
                            userExtendService.insertSelective(userExtendInsert);
                        }else{
                            userExtendEntity.setExtLong(commissionEntity.getIntegral()+userExtendEntity.getExtLong());
                            userExtendService.updateByPrimaryKeySelective(userExtendEntity);
                        }
                        System.out.println("个人扩展表添加数据成功");
                        return R.ok().putData(commissionEntity);
                    }else{
                        System.out.println("佣金添加数据失败");
                        return R.error(Content.STATUS_CODE_5005,"添加数据失败");
                    }
                }
                //佣金小于俩百直接发红包，大于存入佣金记录 end*/
            }else{
                System.out.println("佣金金额为零");
                return R.ok().putData("佣金金额为零");
            }
        }else{
            System.out.println("该订单已经生成佣金请勿重复生成");
            return R.error(Content.STATUS_CODE_5008,"该订单已经生成佣金请勿重复生成");
        }
    }

    @Override
    @Transactional
    public boolean cashWithdrawal(String strJson) {
        try {
            JSONObject jsonObject = new JSONObject(strJson);
            String userGuid = jsonObject.getStr("userGuid");      //用户guid
            String orderGuid = jsonObject.getStr("orderGuid");
            Integer totalAmount = jsonObject.getInt("totalAmount");   //总金额
            Integer state = jsonObject.getInt("state");   //状态  1表示需要审核   2表示直接发放成功
            JSONArray guids = jsonObject.getJSONArray("guids");
            CommissionEntity commissionEntity = new CommissionEntity();
            for(int i=0;i<guids.size();i++){
                Example example = new Example(CommissionEntity.class);
                example.createCriteria().andEqualTo("guid", guids.get(i));
                commissionEntity.setState(state);
                commissionEntity.setCashOrderGuid(orderGuid);
                updateByExampleSelective(commissionEntity, example);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    public boolean conduct(String orderGuid) {
        try {
            CommissionEntity commissionEntity = new CommissionEntity();
            Example example = new Example(CommissionEntity.class);
            example.createCriteria().andEqualTo("cashOrderGuid", orderGuid);
            commissionEntity.setState(2);
            int nret = updateByExampleSelective(commissionEntity, example);
            if(nret > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
}
