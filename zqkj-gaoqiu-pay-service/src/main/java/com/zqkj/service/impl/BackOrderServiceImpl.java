package com.zqkj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ijpay.core.kit.WxPayKit;
import com.zqkj.bean.BackOrderParameteBean;
import com.zqkj.bean.SmsBean;
import com.zqkj.dao.mapper.OrderDao;
import com.zqkj.entity.*;
import com.zqkj.remote.*;
import com.zqkj.service.BackOrderService;
import com.zqkj.utils.*;
import com.zqkj.utils.wxtemplate.PushMessage;
import com.zqkj.utils.wxtemplate.WechatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 订单表
 */
@Service("backOrderService")
public class BackOrderServiceImpl extends BaseServiceImpl<OrderDao, OrderEntity> implements BackOrderService {

    @Autowired
    private ActivityRemote activityRemote;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CouponsuserRemote couponsuserRemote;

    @Autowired
    private UserExtendRemote userExtendRemote;

    @Autowired
    private IntroductionRemote introductionRemote;

    @Autowired
    private UserRemote userRemote;

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private WxRemote wxRemote;

    @Override
    public R addHotelOrder(BackOrderParameteBean hotelOrderParameteBean) {
        OrderEntity orderEntity = new OrderEntity();
        //根据商品guid查询，商品信息
        String json = activityRemote.selectOne(hotelOrderParameteBean.getActivityGuid());
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        if(code.equals(0)){
            //查询到商品，获取商品名称和guid
            JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
            orderEntity.setGoodsGuid(dataJson.getString("guid"));//商品guid
            orderEntity.setName(dataJson.getString("name"));//商品名称
            if(!StringUtil.isEmpty(dataJson.getString("callbackUrl"))){
                orderEntity.setCallbackUrl(dataJson.getString("callbackUrl").trim());//回调url
            }
            //计算总金额
            int totalPrice = dataJson.getInteger("price") * hotelOrderParameteBean.getRoomTotal() * hotelOrderParameteBean.getDateTotal();
            orderEntity.setOrderMoney(totalPrice);//金额  默认是总金额，支付时候减去优惠券后修改
            orderEntity.setTotalOrderMoney(totalPrice);//总金额  总金额和金额一致，因为时间短设置价格不一样无法获取订场里的价格，如果有使用优惠券，减去价格后更新支付金额OrderMoney

            OrderEntity countOrder = new OrderEntity();
            int orderCount = selectCount(countOrder);
            orderCount = orderCount +1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //制定输出格式
            //获取首字母前缀
            //字母+0+日期+序号  表示手动字母  字母+1+日期+序号  表示ID生成
            if(StringUtil.isEmpty(dataJson.getString("prefix"))){
                //如果等于空的话，根据产品ID生成前缀
                orderEntity.setSerialNumber(idToStr(dataJson.getInteger("id"))+"0"+sdf.format(new Date())+orderCount);//流水号
            }else{
                orderEntity.setSerialNumber(dataJson.getString("prefix")+"1"+sdf.format(new Date())+orderCount);//流水号
            }
            //设置订单参数
            orderEntity.setOpenid(BaseContentHandler.getOpenid());//用户openid
            orderEntity.setUserGuid(hotelOrderParameteBean.getUserGuid());//用户guid
            orderEntity.setType(0);//0支付
            orderEntity.setState(4);
            orderEntity.setPayType(0);//0微信支付
            orderEntity.setBusinConfi(5);//2商家已确认
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            orderEntity.setRemark(hotelOrderParameteBean.getRemark());//备注
            orderEntity.setBuySum(hotelOrderParameteBean.getRoomTotal());//预定间数
            orderEntity.setCreator(hotelOrderParameteBean.getUserGuid());
            int nRet = insertSelective(orderEntity);
            if(nRet > 0){
                return payIntroductionOrder(orderEntity.getGuid(),hotelOrderParameteBean.getCouponsUserGuid(),null,dataJson.getInteger("price"));
            }else{
                return R.error(Content.STATUS_CODE_5005,"创建订单失败！");
            }
        }else {
            return R.error(Content.STATUS_CODE_5004,"没有查询到此商品！");
        }
    }


    @Override
    public R addIntroductionOrderPay(BackOrderParameteBean ionorderOrderParameteBean) {
        OrderEntity orderEntity = new OrderEntity();
        //先默认订场回调后续改进
        orderEntity.setCallbackUrl("http://wap.oneagles.com/business/teatime/introductioncallback");

        //根据商品（订场）guid查询，商品信息
        String json = introductionRemote.selectOne(ionorderOrderParameteBean.getIntroductionGuid());
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
        if(code.equals(0)){
            orderEntity.setGoodsGuid(dataJson.getString("guid"));//场地guid
            orderEntity.setName(dataJson.getString("name"));//商品订场
            orderEntity.setDate(ionorderOrderParameteBean.getDate());//日期
            orderEntity.setTime(ionorderOrderParameteBean.getTime());//时间
            if(!StringUtil.isEmpty(dataJson.getString("callbackUrl"))){
                orderEntity.setCallbackUrl(dataJson.getString("callbackUrl").trim());//回调url
            }
            orderEntity.setOrderMoney(ionorderOrderParameteBean.getPrice());//金额  默认是总金额，支付时候减去优惠券后修改
            orderEntity.setTotalOrderMoney(ionorderOrderParameteBean.getPrice());//总金额  总金额和金额一致，因为时间短设置价格不一样无法获取订场里的价格，如果有使用优惠券，减去价格后更新支付金额OrderMoney

            OrderEntity countOrder = new OrderEntity();
            int orderCount = selectCount(countOrder);
            orderCount = orderCount +1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //制定输出格式
            //获取首字母前缀
            //字母+0+日期+序号  表示手动字母  字母+1+日期+序号  表示ID生成
            if(StringUtil.isEmpty(dataJson.getString("prefix"))){
                //如果等于空的话，根据产品ID生成前缀
                orderEntity.setSerialNumber(idToStr(dataJson.getInteger("id"))+"0"+sdf.format(new Date())+orderCount);//流水号
            }else{
                orderEntity.setSerialNumber(dataJson.getString("prefix")+"1"+sdf.format(new Date())+orderCount);//流水号
            }
        }else {
            return R.error(Content.STATUS_CODE_5004,"没有查询到此商品！");
        }
        //设置订单参数
        orderEntity.setOpenid(BaseContentHandler.getOpenid());//用户openid
        orderEntity.setUserGuid(ionorderOrderParameteBean.getUserGuid());//用户guid
        orderEntity.setType(0);//0支付
        orderEntity.setState(4);
        orderEntity.setPayType(0);//0微信支付
        orderEntity.setBusinConfi(5);//2商家已确认
        orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
        orderEntity.setRemark(ionorderOrderParameteBean.getRemark());
        orderEntity.setBuySum(ionorderOrderParameteBean.getBuySum());
        orderEntity.setCreator(ionorderOrderParameteBean.getUserGuid());
        int nRet = insertSelective(orderEntity);
        if(nRet > 0){
            return payIntroductionOrder(orderEntity.getGuid(),ionorderOrderParameteBean.getCouponsUserGuid(),ionorderOrderParameteBean.getGoldSum(),null);
        }else{
            return R.error(Content.STATUS_CODE_5005,"创建订单失败！");
        }
    }


    @Override
    @Transactional
    public R businessConfirm(OrderEntity orderEntity){
        try{
            int nRet = updateByPrimaryKeySelective(orderEntity);
            OrderEntity order = selectOneById(orderEntity.getId());
            if(nRet > 0){
                //微信公众号消息推送
                String user = userRemote.selectOne(order.getUserGuid());   //获取用户信息
                cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
                String openId = "";
                String userName = "";
                if(userJson.getInt("code") == 0){
                    cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                    openId = userData.getStr("openid");
                    userName = userData.getStr("name");
                }
                cn.hutool.json.JSONObject accessTokenJson = new cn.hutool.json.JSONObject(wxRemote.getAccessToken());    //获取accessToken
                String accessToken = accessTokenJson.getStr("data");

                cn.hutool.json.JSONObject remarkJson = new cn.hutool.json.JSONObject(order.getRemark());    //获取accessToken
                if(orderEntity.getBusinConfi() == 2){
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已确认您的订单。", "#000000"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(remarkJson.getStr("startEndDate"), "#000000"));
                    params.put("keyword4", WechatTemplate.item(order.getName(), "#000000"));
                    params.put("remark", WechatTemplate.item("请按预定时间进行使用！", "#000000"));
                    WechatTemplate wechatTemplate = new WechatTemplate();
                    wechatTemplate.setTemplate_id("aRqjbTKbZc2Mc12n74YVSs1bIBFg_qrKLD-cKt7G3PY");   //微信模板id
                    wechatTemplate.setTouser(openId); //openId
                    wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/v1.1/my/my_order.html");
                    wechatTemplate.setData(params);
                    String code = PushMessage.send_template_message(wechatTemplate,accessToken);
                }else if(orderEntity.getBusinConfi() == 3){
                    //查询订单退出优惠券
                    String returnCoupStr = couponsuserRemote.cancelOrderCoupons(order.getGuid());
                    JSONObject jsonObject = JSONObject.parseObject(returnCoupStr);
                    Integer code = jsonObject.getInteger("code");
                    System.out.println("订单使用的优惠券回退："+order.getGuid());
                    System.out.println(returnCoupStr);
                    if(code.equals(0)) {
                        //如果支付后进行取消  订单金额退回到 个人余额中
                        Map<String, Object> params = new HashMap<>();
                        params.put("orderGuid", order.getGuid());
                        params.put("payMoney", order.getPayMoney());
                        params.put("userGuid", order.getCreator());
                        params.put("state", order.getState());
                        String orderJsonStr = JSON.toJSONString(params);
                        String returnUser = userExtendRemote.cancelOrderBalance(orderJsonStr);
                    }
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已取消您的订单。", "#FF0000"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(remarkJson.getStr("startEndDate"), "#000000"));
                    params.put("keyword4", WechatTemplate.item(order.getName(), "#000000"));
                    params.put("remark", WechatTemplate.item("请重新下单", "#FF0000"));
                    WechatTemplate wechatTemplate = new WechatTemplate();
                    wechatTemplate.setTemplate_id("aRqjbTKbZc2Mc12n74YVSs1bIBFg_qrKLD-cKt7G3PY");   //微信模板id
                    wechatTemplate.setTouser(openId); //openId
                    wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/v1.1/my/my_order.html");
                    wechatTemplate.setData(params);
                    PushMessage.send_template_message(wechatTemplate,accessToken);
                }else if(orderEntity.getBusinConfi() == 4){
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已修改您的订单。", "#FFCC33"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(remarkJson.getStr("startEndDate"), "#000000"));
                    params.put("keyword4", WechatTemplate.item(order.getName(), "#000000"));
                    params.put("remark", WechatTemplate.item("请到我的订单中进行确认或联系商家！", "#FFCC33"));
                    WechatTemplate wechatTemplate = new WechatTemplate();
                    wechatTemplate.setTemplate_id("aRqjbTKbZc2Mc12n74YVSs1bIBFg_qrKLD-cKt7G3PY");   //微信模板id
                    wechatTemplate.setTouser(openId); //openId
                    wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/v1.1/my/my_order.html");
                    wechatTemplate.setData(params);
                    String code = PushMessage.send_template_message(wechatTemplate,accessToken);
                }
                return R.ok().putData(order);
            }else{
                return R.error(Content.STATUS_CODE_5001,"更新失败");
            }
        }catch (Exception e){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return R.error(Content.STATUS_CODE_5001,"异常更新失败");
        }
    }


    public R payIntroductionOrder(String orderGuid,String[] couponsUserGuid,Integer goldSum,Integer price) {
        OrderEntity orderEntity = selectOneByGuid(orderGuid);
        //查询所选的优惠券，并进行这些优惠券和订单绑定，同时把订单价格传入，计算后返回优惠的价格，然后进行支付优惠后的金额
        /*
         *业务处理有问题，优惠卷和金币可以共同使
         */

        if(couponsUserGuid != null && couponsUserGuid.length > 0){
            Map<String, Object> params = new HashMap<>();
            params.put("orderGuid",orderGuid);
            params.put("orderMoney",orderEntity.getTotalOrderMoney());
            params.put("couponsUserGuid",couponsUserGuid);
            if(price != null){
                params.put("unitPrice",price);
            }else{
                int unitPrice = (int) (orderEntity.getOrderMoney() / orderEntity.getBuySum() + 0.5);
                params.put("unitPrice",unitPrice);
            }
            String jsonStr = JSON.toJSONString(params);
            String returnStr = couponsuserRemote.payOrderBindingCoupons(jsonStr);
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            Integer code = jsonObject.getInteger("code");
            System.out.println("传入值："+jsonStr);
            System.out.println("返回值："+returnStr);
            if(code.equals(0)){
                Integer orderMoney = jsonObject.getInteger("data");
                orderEntity.setOrderMoney(orderMoney);
                orderEntity.setState(4);
                int updateNret = updateByPrimaryKeySelective(orderEntity);//使用优惠券后把支付金额改掉
                if(updateNret > 0){
                    try {
                        System.out.println("产品回调");
                        String returnStrs = HttpClient.doGet(orderEntity.getCallbackUrl()+"?creator="+orderEntity.getCreator()+"&orderGuid="+orderEntity.getGuid()+"&sum="+orderEntity.getBuySum(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                        JSONObject getJson = JSONObject.parseObject(returnStrs);
                        System.out.println(getJson);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return R.ok("支付成功").putData(orderEntity).put("type", 1);
                }else{
                    return R.error(Content.STATUS_CODE_5007,"订单支付失败请联系管理员！");
                }
            }else{
                return R.error(Content.STATUS_CODE_5007,"优惠券抵扣失败请重新支付！");
            }
        }else if(goldSum != null && goldSum > 0){
            //使用金币数量大于0 进入处理金币业务
            Map<String, Object> params = new HashMap<>();
            params.put("orderGuid",orderGuid);//订单guid
            params.put("userGuid",orderEntity.getUserGuid());//用户guid
            params.put("orderMoney",orderEntity.getTotalOrderMoney());//订单金额
            params.put("goldSum",goldSum);//使用金币数量
            String jsonStr = JSON.toJSONString(params);
            System.out.println("金币抵扣传入值："+jsonStr);
            String returnStr = userExtendRemote.useGold(jsonStr);
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            Integer code = jsonObject.getInteger("code");
            System.out.println("金币抵扣返回值："+returnStr);
            if(code.equals(0)){
                Integer orderMoney = jsonObject.getInteger("data");
                orderEntity.setOrderMoney(orderMoney);
                orderEntity.setState(4);
                int updateNret = updateByPrimaryKeySelective(orderEntity);//使用优惠券后把支付金额改掉
                if(updateNret > 0){
                    try {
                        System.out.println("产品回调");
                        String returnStrs = HttpClient.doGet(orderEntity.getCallbackUrl()+"?creator="+orderEntity.getCreator()+"&orderGuid="+orderEntity.getGuid()+"&sum="+orderEntity.getBuySum(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                        JSONObject getJson = JSONObject.parseObject(returnStrs);
                        System.out.println(getJson);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return R.ok("支付成功").putData(orderEntity).put("type", 1);
                }else{
                    return R.error(Content.STATUS_CODE_5007,"订单支付失败请联系管理员！");
                }
            }else{
                return R.error(Content.STATUS_CODE_5007,"金币抵扣失败请重新支付！");
            }
        }else{
            orderEntity.setState(4);
            int updateNret = updateByPrimaryKeySelective(orderEntity);//使用优惠券后把支付金额改掉
            if(updateNret > 0){
                try {
                    System.out.println("产品回调");
                    String returnStrs = HttpClient.doGet(orderEntity.getCallbackUrl()+"?creator="+orderEntity.getCreator()+"&orderGuid="+orderEntity.getGuid()+"&sum="+orderEntity.getBuySum(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                    JSONObject getJson = JSONObject.parseObject(returnStrs);
                    System.out.println(getJson);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return R.ok("支付成功").putData(orderEntity).put("type", 1);
            }else{
                return R.error(Content.STATUS_CODE_5007,"订单支付失败请联系管理员！");
            }
        }
    }

    public UserEntity selectOneUser(String userGuid) {
        //根据商品guid查询，商品信息
        String json = userRemote.selectOne(userGuid);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        if(code.equals(0)){
            //查询到商品，获取商品名称和guid
            return JSONObject.toJavaObject(jsonObject.getJSONObject("data"), UserEntity.class);
        }else {
            return null;
        }
    }

    //根据ID转字符串
    public static String idToStr(int i) {
        StringBuilder str = new StringBuilder();
        while (i > 25) {
            str.append((char)(i % 26 + 65));
            i = i / 26;
        }
        str.append((char)(i + 65));
        return str.toString();
    }

    //根据字符串转ID
    public static int strToId(String str) {
        char[] c = str.toCharArray();
        int k = 1;
        int v = 0;
        for (int i = 0; i < c.length; i++) {
            v = v + (c[i] - 65) * k;
            k = k * 26;
        }
        return v;
    }
}
