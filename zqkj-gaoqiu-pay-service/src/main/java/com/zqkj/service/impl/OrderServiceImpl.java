package com.zqkj.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.IpKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.SendReadPackModel;
import com.zqkj.bean.HotelOrderParameteBean;
import com.zqkj.bean.OrderVipBean;
import com.zqkj.bean.SmsBean;
import com.zqkj.dao.mapper.OrderDao;
import com.zqkj.entity.*;
import com.zqkj.remote.*;
import com.zqkj.service.OrderExtendService;
import com.zqkj.service.OrderService;
import com.zqkj.service.PayMentrecordService;
import com.zqkj.utils.*;
import com.zqkj.utils.wxtemplate.PushMessage;
import com.zqkj.utils.wxtemplate.WechatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 订单表
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<OrderDao, OrderEntity> implements OrderService {


    //记录支付后球场未确认的订单guid  和通知次数
    public static Map<String,Integer> map = new HashMap<>();

    @Autowired
    private ActivityRemote activityRemote;

    @Autowired
    private WxPayUtils wxPayUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PayMentrecordService payMentrecordService;

    @Autowired
    private CommissionRemote commissionRemote;

    @Autowired
    private IntroductionRemote introductionRemote;

    @Autowired
    private ReservationdateRemote reservationdateRemote;

    @Autowired
    private UserRemote userRemote;

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private WxRemote wxRemote;

    @Autowired
    private CouponsuserRemote couponsuserRemote;

    @Autowired
    private UserExtendRemote userExtendRemote;

    @Autowired
    private GoldRemote goldRemote;

    @Autowired
    private OrderExtendService orderExtendService;

    @Autowired
    private IntegralRemote integralRemote;


    @Override
    public R addOrder(String goodsGuid,String inviterGuid,Integer sum,Integer[] typeArr,String userJson,String remark) {
        Integer productType = null;
        OrderEntity orderEntity = new OrderEntity();
        //根据商品guid查询，商品信息
        String json = activityRemote.selectOne(goodsGuid);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        if(code.equals(0)){
            //查询到商品，获取商品名称和guid
            JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
            orderEntity.setGoodsGuid(dataJson.getString("guid"));//商品guid
            orderEntity.setName(dataJson.getString("name"));//商品订场

            //暂时下单活动设置金额
            if(dataJson.getInteger("type") == 0){
                int priceTotal = 0;
                for(Integer t : typeArr){
                    if(t == 2){//会员价  活动价格
                        priceTotal += dataJson.getInteger("price");
                    }else{//正常价  活动原价
                        priceTotal += dataJson.getInteger("originalPrice");
                    }
                }
                orderEntity.setOrderMoney(priceTotal);//金额
                orderEntity.setTotalOrderMoney(priceTotal);//总金额
                productType = 0;
            }else{
                orderEntity.setOrderMoney(dataJson.getInteger("price")*sum);//金额
                orderEntity.setTotalOrderMoney(dataJson.getInteger("price")*sum);//总金额
            }
            orderEntity.setBuySum(sum);

            //判断是否是团购（新） 类型，如果是加入订单团购状态
            if(dataJson.getInteger("type") == 4){
                orderEntity.setGroupBuyingState(1);//1默认等待拼团完成状态
            }

            //设置订单参数
            orderEntity.setOpenid(BaseContentHandler.getOpenid());//用户openid
            orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
            orderEntity.setType(0);//0支付
            orderEntity.setPayType(0);//0微信支付
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            if(!StringUtil.isEmpty(remark)){
                orderEntity.setRemark(remark);
            }

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
            if(!StringUtil.isEmpty(dataJson.getString("callbackUrl"))){
                orderEntity.setCallbackUrl(dataJson.getString("callbackUrl").trim()+"&sourceGuid="+orderEntity.getGoodsGuid()+"&userGuid="+BaseContentHandler.getUserGuid()+"&orderNumber="+orderEntity.getOrderNumber()+"&serialNumber="+orderEntity.getSerialNumber());//回调url
            }

            //判断商品限购次数，超出次数不让进行下单购买
            if(dataJson.getInteger("buyNumber") != null && dataJson.getInteger("type") != 0){
                OrderEntity orderBuyNumber = new OrderEntity();
                orderBuyNumber.setGoodsGuid(dataJson.getString("guid"));
                orderBuyNumber.setUserGuid(BaseContentHandler.getUserGuid());
                orderBuyNumber.setType(0);//0支付  1退款
                orderBuyNumber.setState(1);//0失败  1成功
                int successOrderCount = selectCount(orderBuyNumber);
                if(successOrderCount >= dataJson.getInteger("buyNumber")){
                    return R.error(Content.STATUS_CODE_5004,"该产品可购买"+dataJson.getInteger("buyNumber")+"次，您已购买了"+successOrderCount+"次，不可再次进行购买！");
                }
            }

            if(dataJson.getInteger("price") > 0){
                orderEntity.setState(0);//支付失败状态   0失败
            }else{
                orderEntity.setState(1);//支付失败状态  1成功   金额为0 不需要支付
            }
        }else {
            return R.error(Content.STATUS_CODE_5004,"没有查询到此商品！");
        }

        //传入被邀请人guid
        if(!StringUtil.isEmpty(inviterGuid)){
            orderEntity.setInviterGuid(inviterGuid);
        }

        int nRet = insertSelective(orderEntity);
        //productType=0 为活动，活动需要在扩展表中插入报名人数
        if(productType != null && productType == 0){
            OrderExtendEntity orderExtendEntity = new OrderExtendEntity();
            orderExtendEntity.setExtStr(userJson);
            orderExtendEntity.setType(1);
            orderExtendEntity.setState(1);
            orderExtendEntity.setOrderGUID(orderEntity.getGuid());
            orderExtendService.insertSelective(orderExtendEntity);
        }
        if(nRet > 0){
            //如果订单表中是支付成功的，说明产品金额为0，直接进行回调
            if(orderEntity.getState() == 1){
                if(!StringUtil.isEmpty(orderEntity.getCallbackUrl())){
                    try {
                        String returnStr = HttpClient.doGet(orderEntity.getCallbackUrl()+"&orderGuid="+orderEntity.getGuid(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                        JSONObject getJson = JSONObject.parseObject(returnStr);
                        if(getJson.getInteger("code") == 0){
                            return R.ok().putData(orderEntity);
                        }else{
                            return R.error(Content.STATUS_CODE_5017,"会员卡绑定失败，请联系管理员！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("回调失败：" + orderEntity.getCallbackUrl());
                        return R.error(Content.STATUS_CODE_5017,"会员卡绑定失败，请联系管理员！");
                    }
                }else{
                    return R.ok().putData(orderEntity);
                }
            }
            return wxPayUtils.webPay(request,orderEntity);
            //return R.ok().putData(orderEntity);
        }else{
            return R.error(Content.STATUS_CODE_5005,"创建订单失败！");
        }
    }

    @Override
    public R addHotelOrder(HotelOrderParameteBean hotelOrderParameteBean) {
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
            if(orderEntity.getOrderMoney() > 0){
                orderEntity.setState(0);//支付失败状态   0失败
            }else{
                orderEntity.setState(1);//支付失败状态  1成功   金额为0 不需要支付
            }
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
            orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
            orderEntity.setType(0);//0支付
            orderEntity.setPayType(0);//0微信支付
            orderEntity.setBusinConfi(1);//1待确认
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            orderEntity.setRemark(hotelOrderParameteBean.getRemark());//备注
            orderEntity.setBuySum(hotelOrderParameteBean.getRoomTotal());//预定间数
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
    public R addIntroductionOrder(String introductionGuid,String date, String time, Integer price) {
        OrderEntity orderEntity = new OrderEntity();

        //先默认订场回调后续改进
        orderEntity.setCallbackUrl("http://wap.oneagles.com/business/teatime/introductioncallback");
        //根据场地guid和下单时间查询，是否有场地时间设置表数据
        /*String parameterJson = "{\"guid\":\"" + introductionGuid + "\",\"date\":\"" + date + "\"}";
        String reservationdateJson = reservationdateRemote.selectOne(parameterJson);
        JSONObject reservationdateObject = JSONObject.parseObject(reservationdateJson);
        Integer reservationdateCode = reservationdateObject.getInteger("code");
        JSONObject reservationdateData = JSONObject.parseObject(reservationdateObject.getString("data"));*/


        //根据商品（订场）guid查询，商品信息
        String json = introductionRemote.selectOne(introductionGuid);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
        if(code.equals(0)){
            orderEntity.setGoodsGuid(dataJson.getString("guid"));//场地guid
            orderEntity.setName(dataJson.getString("name"));//商品订场
            orderEntity.setDate(date);//日期
            orderEntity.setTime(time);//时间
            orderEntity.setBuySum(1);
            if(!StringUtil.isEmpty(dataJson.getString("callbackUrl"))){
                orderEntity.setCallbackUrl(dataJson.getString("callbackUrl").trim());//回调url
            }
            orderEntity.setOrderMoney(price);//金额
            if(orderEntity.getOrderMoney() > 0){
                orderEntity.setState(0);//支付失败状态   0失败
            }else{
                orderEntity.setState(1);//支付失败状态  1成功   金额为0 不需要支付
            }

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
        orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
        orderEntity.setType(0);//0支付
        orderEntity.setPayType(0);//0微信支付
        orderEntity.setBusinConfi(1);//1商家待确认
        orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
        orderEntity.setTotalOrderMoney(orderEntity.getOrderMoney());//总金额和金额一致，因为时间短设置价格不一样无法获取订场里的价格

        int nRet = insertSelective(orderEntity);
        if(nRet > 0){
            // wxPayUtils.webPay(request,orderEntity);
            return R.ok().putData(orderEntity);
        }else{
            return R.error(Content.STATUS_CODE_5005,"创建订单失败！");
        }
    }

    @Override
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
                if(orderMoney != 0) {
                	int updateNret = updateByPrimaryKeySelective(orderEntity);//使用优惠券后把支付金额改掉
  	                if(updateNret > 0){
	                    return wxPayUtils.webPay(request,orderEntity);
	                }else{
	                    return R.error(Content.STATUS_CODE_5007,"订单支付失败请联系管理员！");
	                }
                } else {
                	orderEntity.setState(1);
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
                if(orderMoney != 0) {
                	int updateNret = updateByPrimaryKeySelective(orderEntity);//使用金币后把支付金额改掉
	                if(updateNret > 0){
	                    return wxPayUtils.webPay(request,orderEntity);
	                }else{
	                    return R.error(Content.STATUS_CODE_5007,"订单支付失败请联系管理员！");
	                }
                } else {
                	orderEntity.setState(1);
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
            }else{
                return R.error(Content.STATUS_CODE_5007,"金币抵扣失败请重新支付！");
            }
        }else{
            return wxPayUtils.webPay(request,orderEntity);
        }
    }


    @Override
    public R addIntroductionOrderPay(String introductionGuid,String date, String time, Integer price,String[] couponsUserGuid,String remark,Integer buySum,Integer goldSum) {
        OrderEntity orderEntity = new OrderEntity();
        //先默认订场回调后续改进
        orderEntity.setCallbackUrl("http://wap.oneagles.com/business/teatime/introductioncallback");

        //根据商品（订场）guid查询，商品信息
        String json = introductionRemote.selectOne(introductionGuid);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
        if(code.equals(0)){
            orderEntity.setGoodsGuid(dataJson.getString("guid"));//场地guid
            orderEntity.setName(dataJson.getString("name"));//商品订场
            orderEntity.setDate(date);//日期
            orderEntity.setTime(time);//时间
            if(!StringUtil.isEmpty(dataJson.getString("callbackUrl"))){
                orderEntity.setCallbackUrl(dataJson.getString("callbackUrl").trim());//回调url
            }
            orderEntity.setOrderMoney(price);//金额  默认是总金额，支付时候减去优惠券后修改
            orderEntity.setTotalOrderMoney(price);//总金额  总金额和金额一致，因为时间短设置价格不一样无法获取订场里的价格，如果有使用优惠券，减去价格后更新支付金额OrderMoney
            if(orderEntity.getOrderMoney() > 0){
                orderEntity.setState(0);//支付失败状态   0失败
            }else{
                orderEntity.setState(1);//支付失败状态  1成功   金额为0 不需要支付
            }

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
        orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
        orderEntity.setType(0);//0支付
        orderEntity.setPayType(0);//0微信支付
        orderEntity.setBusinConfi(1);//1商家待确认
        orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
        orderEntity.setRemark(remark);
        orderEntity.setBuySum(buySum);
        int nRet = insertSelective(orderEntity);
        if(nRet > 0){
            return payIntroductionOrder(orderEntity.getGuid(),couponsUserGuid,goldSum,null);
            //return R.ok().putData(orderEntity);
        }else{
            return R.error(Content.STATUS_CODE_5005,"创建订单失败！");
        }
    }


    @Override
    public R jsapiPay(String orderGuid) {
        OrderEntity orderEntity = selectOneByGuid(orderGuid);
        if(orderEntity != null){
            //根据商品guid查询，商品信息
            String json = activityRemote.selectOne(orderEntity.getGoodsGuid());
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if(code.equals(0)){
                JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
                //判断商品限购次数，超出次数不让进行下单购买
                if(dataJson.getInteger("buyNumber") != null){
                    OrderEntity orderBuyNumber = new OrderEntity();
                    orderBuyNumber.setGoodsGuid(dataJson.getString("guid"));
                    orderBuyNumber.setUserGuid(BaseContentHandler.getUserGuid());
                    orderBuyNumber.setType(0);//0支付  1退款
                    orderBuyNumber.setState(1);//0失败  1成功
                    int successOrderCount = selectCount(orderBuyNumber);
                    if(successOrderCount >= dataJson.getInteger("buyNumber")){
                        return R.error(Content.STATUS_CODE_5004,"该产品可购买"+dataJson.getInteger("buyNumber")+"次，您已购买了"+successOrderCount+"次，不可再次进行购买！");
                    }
                }
            }else{
                return R.error(Content.STATUS_CODE_5004,"没有查询到该商品！");
            }
            return wxPayUtils.webPay(request,orderEntity);
        }else{
            return R.error(Content.STATUS_CODE_5004,"没有查询到订单！");
        }
    }

    @Override
    public R cancelOrder(Integer id,Integer state) {
        OrderEntity orderEntity = selectOneById(id);
        //查询订单退出优惠券
        String returnCoupStr = couponsuserRemote.cancelOrderCoupons(orderEntity.getGuid());
        JSONObject jsonObject = JSONObject.parseObject(returnCoupStr);
        Integer code = jsonObject.getInteger("code");
        System.out.println("订单使用的优惠券回退："+orderEntity.getGuid());
        System.out.println(returnCoupStr);
        if(code.equals(0)){
            //添加个人余额
            //if(orderEntity.getState() == 1){
                //如果支付后进行取消  订单金额退回到 个人余额中
                Map<String, Object> params = new HashMap<>();
                params.put("orderGuid",orderEntity.getGuid());
                params.put("payMoney",orderEntity.getPayMoney());
                params.put("userGuid",orderEntity.getCreator());
                params.put("state",orderEntity.getState());
                String orderJsonStr = JSON.toJSONString(params);
                String returnUser = userExtendRemote.cancelOrderBalance(orderJsonStr);
                JSONObject jsonUser = JSONObject.parseObject(returnUser);
                Integer userCode = jsonUser.getInteger("code");
                System.out.println("个人余额增加："+orderJsonStr);
                System.out.println(returnUser);
                if(userCode.equals(0)){
                    OrderEntity updateEntity = new OrderEntity();
                    updateEntity.setId(id);
                    updateEntity.setState(state);
                    int updateNret = updateByPrimaryKeySelective(updateEntity);
                    if(updateNret > 0){
                        return R.ok().putData(updateEntity);
                    }else{
                        return R.error(Content.STATUS_CODE_5007,"订单取消失败请重试！");
                    }
                }else{
                    return R.error(Content.STATUS_CODE_5007,"订单取消失败请重试！");
                }
            /*}else{
                //没有支付直接取消
                OrderEntity updateEntity = new OrderEntity();
                updateEntity.setId(id);
                updateEntity.setState(2);
                int updateNret = updateByPrimaryKeySelective(updateEntity);
                if(updateNret > 0){
                    return R.ok().putData(updateEntity);
                }else{
                    return R.error(Content.STATUS_CODE_5007,"订单取消失败请重试！");
                }
            }*/
        }else{
            return R.error(Content.STATUS_CODE_5007,"订单取消失败请联系管理员！");
        }
    }

    @Override
    @Transactional
    public String payNotify(String xmlMsg) {
        System.out.println("进入回调");
        Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);
        String resultCode = params.get("result_code");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        if (WxPayKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPartnerKey(), SignType.HMACSHA256)) {
            if (WxPayKit.codeIsOk(resultCode)) {
                //更新订单信息    判断订单信息是否已经更新
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderNumber(params.get("out_trade_no"));
                orderEntity = selectOne(orderEntity);
                if(orderEntity.getState().equals(0)){
                    try{
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        orderEntity.setPayMoney(Integer.valueOf(params.get("total_fee")));//支付金额
                        orderEntity.setPayTime(formatter.format(new Date()));//支付成功时间
                        orderEntity.setState(1);//支付成功
                        orderEntity.setBusinConfi(1);//4订场改为已支付    根据需要先付款后确认，所以改为待确认状态
                        int nRet = updateByPrimaryKeySelective(orderEntity);
                        System.out.println("订单已更新");
                        if(nRet > 0){
                            //添加流水记录
                            PayMentrecordEntity payMentrecordEntity = new PayMentrecordEntity();
                            payMentrecordEntity.setTransactionId(params.get("transaction_id"));//微信订单id
                            payMentrecordEntity.setUserGuid(orderEntity.getUserGuid());//用户guid
                            payMentrecordEntity.setOrderGuid(orderEntity.getGuid());//订单id
                            payMentrecordEntity.setPayMount(Integer.valueOf(params.get("total_fee")));//支付金额
                            payMentrecordEntity.setType(0);
                            payMentrecordEntity.setState(0);
                            payMentrecordEntity.setOrderNumber(orderEntity.getOrderNumber());//系统订单号
                            int payNRet = payMentrecordService.insertSelective(payMentrecordEntity);
                            System.out.println("流水一插入");
                            if(payNRet >0){
                                //邀请购买佣金记录
                                if(!StringUtil.isEmpty(orderEntity.getInviterGuid())){
                                    System.out.println("执行佣金接口");
                                    Map<String,Object> mapCommission = new HashMap<>();
                                    mapCommission.put("productGuid",orderEntity.getGoodsGuid());
                                    mapCommission.put("sourceGuid",orderEntity.getGuid());
                                    mapCommission.put("inviterGuid",orderEntity.getInviterGuid());
                                    mapCommission.put("sum",orderEntity.getBuySum());
                                    String returnmsg = commissionRemote.analysisCommission(JSON.toJSONString(mapCommission));
                                    System.out.println("佣金接口执行完成");
                                    System.out.println(returnmsg);
                                }
                                //购买会员产品返给上级的佣金记录(只有会员产品执行，不包含订场  date和time为空则不是订场产品)
                                if(StringUtil.isEmpty(orderEntity.getDate()) && StringUtil.isEmpty(orderEntity.getTime()) ){
                                    Map<String,Object> mapCommission = new HashMap<>();
                                    mapCommission.put("productGuid",orderEntity.getGoodsGuid());
                                    mapCommission.put("sourceGuid",orderEntity.getGuid());
                                    mapCommission.put("creator",orderEntity.getUserGuid());
                                    mapCommission.put("sum",orderEntity.getBuySum());
                                    commissionRemote.addCommission(JSON.toJSONString(mapCommission));
                                }

                                Map<String,Object> mapIntegral = new HashMap<>();
                                mapIntegral.put("productGuid",orderEntity.getGoodsGuid());
                                mapIntegral.put("sourceGuid",orderEntity.getGuid());
                                mapIntegral.put("creator",orderEntity.getUserGuid());
                                mapIntegral.put("sum",orderEntity.getBuySum());
                                String returnStrMsg = integralRemote.integralRecurrence(JSON.toJSONString(mapIntegral));
                                System.out.println("订场积分返现接口业务执行完毕");
                                System.out.println(returnStrMsg);


                                System.out.println("执行返现金币接口");
                                Map<String,Object> mapCommission = new HashMap<>();
                                mapCommission.put("productGuid",orderEntity.getGoodsGuid());//产品guid
                                mapCommission.put("sourceGuid",orderEntity.getGuid());//订单guid
                                mapCommission.put("userGuid",orderEntity.getUserGuid());//用户guid
                                mapCommission.put("sum",orderEntity.getBuySum());
                                String returnmsg = goldRemote.productCashBack(JSON.toJSONString(mapCommission));
                                System.out.println("返现金币接口执行完成");
                                System.out.println(returnmsg);

                                //产品进行回调
                                if(!StringUtil.isEmpty(orderEntity.getCallbackUrl()) && !StringUtil.isEmpty(orderEntity.getDate())){
                                    try {
                                        System.out.println("产品回调");
                                        String returnStr = HttpClient.doGet(orderEntity.getCallbackUrl()+"?creator="+orderEntity.getCreator()+"&orderGuid="+orderEntity.getGuid()+"&sum="+orderEntity.getBuySum(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                                        JSONObject getJson = JSONObject.parseObject(returnStr);
                                        System.out.println(returnStr);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        System.out.println("回调失败---：" + orderEntity.getCallbackUrl());
                                    }
                                }else if(!StringUtil.isEmpty(orderEntity.getCallbackUrl())){
                                    try {
                                        String returnStr = HttpClient.doGet(orderEntity.getCallbackUrl()+"&creator="+orderEntity.getCreator()+"&orderGuid="+orderEntity.getGuid()+"&sum="+orderEntity.getBuySum()+"&goodsGuid="+orderEntity.getGoodsGuid(),request.getHeader(Content.CONTEXT_KEY_USER_TOKEN));
                                        JSONObject getJson = JSONObject.parseObject(returnStr);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        System.out.println("回调失败：" + orderEntity.getCallbackUrl());
                                    }
                                }
                                // 发送通知等
                                Map<String, String> xml = new HashMap<String, String>(2);
                                xml.put("return_code", "SUCCESS");
                                xml.put("return_msg", "OK");
                                return WxPayKit.toXml(xml);
                            }else{
                                throw new Exception();
                            }
                        }else{
                            throw new Exception();
                        }
                    }catch (Exception e){
                        //事务回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        e.printStackTrace();
                        return null;
                    }
                }else{
                    // 订单状态不等于0，流水已经记录，订单状态已经更新
                    Map<String, String> xml = new HashMap<String, String>(2);
                    xml.put("return_code", "SUCCESS");
                    xml.put("return_msg", "OK");
                    return WxPayKit.toXml(xml);
                }
            }
        }
        return null;
    }

    @Override
    public PlaceOrderEntity selectOneActivity(String goodsGuid) {
        //根据商品guid查询，商品信息
        String json = activityRemote.selectOne(goodsGuid);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = jsonObject.getInteger("code");
        if(code.equals(0)){
            //查询到商品，获取商品名称和guid
            return JSONObject.toJavaObject(jsonObject.getJSONObject("data"),PlaceOrderEntity.class);
        }else {
            return null;
        }
    }

    @Override
    public R updateVipGuid(OrderVipBean orderVipBean) {
        Example example = new Example(OrderEntity.class);
        example.createCriteria().andEqualTo("guid", orderVipBean.getOrderGuid());
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setVipGuid(orderVipBean.getVipGuid());
        int count = updateByExampleSelective(orderEntity, example);
        return R.ok().putData(count);
    }

    @Override
    public PageUtil<OrderEntity> selectListPage(PageUtil<OrderEntity> pageUtil, OrderEntity record) {
        if(pageUtil.getPage() == 0){
            pageUtil.setPage(1);
        }
        record.setLimit(pageUtil.getLimit());
        record.setPage(((pageUtil.getPage()-1)*pageUtil.getLimit()));
        Page<OrderEntity> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
        if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
            page.setOrderBy(pageUtil.getOrderBy());
        }
        int count = dao.selectCount(record);
        List<OrderEntity> list = dao.selectListPage(record);
        for(OrderEntity order : list){
            String json = userRemote.selectOne(order.getUserGuid());
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if(code.equals(0)) {
                //查询到商品，获取商品名称和guid
                order.setUserEntity(JSONObject.toJavaObject(jsonObject.getJSONObject("data"), UserEntity.class));
            }
        }
        pageUtil = new PageUtil<OrderEntity>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
        pageUtil.setCount(count);
        pageUtil.setList(list);
        return pageUtil;
    }

    @Override
    public List<OrderEntity> queryList(OrderEntity record) {
        List<OrderEntity> list = dao.select(record);
        for(OrderEntity order : list){
            String json = userRemote.selectOne(order.getUserGuid());
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if(code.equals(0)) {
                //查询到商品，获取商品名称和guid
                order.setUserEntity(JSONObject.toJavaObject(jsonObject.getJSONObject("data"), UserEntity.class));
            }
        }
        return list;
    }

    @Override
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

    @Override
    @Transactional
    public R businessConfirm(OrderEntity orderEntity){
        try{
            if(orderEntity.getBusinConfi() != 4){//4商家修改订单时间
                orderEntity.setTime(null);
            }
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

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
                UserEntity userEntity = selectOneUser(order.getUserGuid());
                Date date = null;
                try {
                    date = sdf.parse(order.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String Details = order.getName()+"："+sdf.format(date)+" "+order.getTime();
                if(orderEntity.getBusinConfi() == 2){
                    smsBean.setTemplateId(603996);// 短信模板 ID，需要在短信应用中申请
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已确认您的订单。", "#000000"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(sdf.format(date)+" "+order.getTime(), "#000000"));
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
                    smsBean.setTemplateId(603995);// 短信模板 ID，需要在短信应用中申请
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已取消您的订单。", "#FF0000"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(sdf.format(date)+" "+order.getTime(), "#000000"));
                    params.put("keyword4", WechatTemplate.item(order.getName(), "#000000"));
                    params.put("remark", WechatTemplate.item("请重新下单", "#FF0000"));
                    WechatTemplate wechatTemplate = new WechatTemplate();
                    wechatTemplate.setTemplate_id("aRqjbTKbZc2Mc12n74YVSs1bIBFg_qrKLD-cKt7G3PY");   //微信模板id
                    wechatTemplate.setTouser(openId); //openId
                    wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/v1.1/my/my_order.html");
                    wechatTemplate.setData(params);
                    PushMessage.send_template_message(wechatTemplate,accessToken);
                }else if(orderEntity.getBusinConfi() == 4){
                    smsBean.setTemplateId(603992);// 短信模板 ID，需要在短信应用中申请
                    //设置消息模板
                    TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
                    //根据具体模板参数组装
                    params.put("first", WechatTemplate.item("您好，商家已修改您的订单。", "#FFCC33"));
                    params.put("keyword1", WechatTemplate.item(order.getSerialNumber(), "#000000"));
                    params.put("keyword2", WechatTemplate.item(userName, "#000000"));
                    params.put("keyword3", WechatTemplate.item(sdf.format(date)+" "+order.getTime(), "#000000"));
                    params.put("keyword4", WechatTemplate.item(order.getName(), "#000000"));
                    params.put("remark", WechatTemplate.item("请到我的订单中进行确认或联系商家！", "#FFCC33"));
                    WechatTemplate wechatTemplate = new WechatTemplate();
                    wechatTemplate.setTemplate_id("aRqjbTKbZc2Mc12n74YVSs1bIBFg_qrKLD-cKt7G3PY");   //微信模板id
                    wechatTemplate.setTouser(openId); //openId
                    wechatTemplate.setUrl(request.getScheme()+"://"+ request.getServerName() + "/wap/business/v1.1/my/my_order.html");
                    wechatTemplate.setData(params);
                    String code = PushMessage.send_template_message(wechatTemplate,accessToken);
                }

                String[] phoneNumbers = {userEntity.getPhone()};//电话
                    String[] params = {Details};//短信内容
                    SmsSingleSender ssender = new SmsSingleSender(smsBean.getAppid(), smsBean.getAppkey());
                    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                            smsBean.getTemplateId(), params, smsBean.getSmsSign(), "", "");
                    System.out.println(result);
                return R.ok().putData(order);
                /*cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject(result.toString());
                if(json.getInt("result") == 0){
                    return R.ok().putData(order);
                }else{
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return R.error(Content.STATUS_CODE_5324,"发送失败请重试！");
                }*/
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

    @Override
    @Transactional
    public OrderEntity cashWithdrawalOrder(Integer totalAmount,String[] commissionGuids) {
        try{
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            orderEntity.setOpenid(BaseContentHandler.getOpenid());//用户openid
            orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
            orderEntity.setName("佣金提现");//订单名称
            orderEntity.setOrderMoney(totalAmount);//订单金额
            orderEntity.setType(2);//2提现
            orderEntity.setState(0);//0失败
            orderEntity.setPayType(0);//0微信
            //payMoney   提现金额 提现成功后插入
            //payTime    提现时间 提现成功后插入
            orderEntity.setBusinConfi(1);  //后台审核确认状态  1待确认  2已确认  3商家拒绝  4已支付

            //设置流水号
            OrderEntity countOrder = new OrderEntity();
            int orderCount = selectCount(countOrder);
            orderCount = orderCount +1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //制定输出格式
            String serialNumber = "YJTX0"+sdf.format(new Date())+orderCount;
            orderEntity.setSerialNumber(serialNumber);  //流水号
            orderEntity.setGoodsGuid(serialNumber);//商品guid ，提现时候没有商品guid  设定为流水号
            int nRet = insertSelective(orderEntity);
            if(nRet > 0){
                Map<String,Object> mapCommission = new HashMap<>();
                mapCommission.put("guids",commissionGuids);
                mapCommission.put("userGuid",BaseContentHandler.getUserGuid());
                mapCommission.put("totalAmount",totalAmount);
                mapCommission.put("orderGuid",orderEntity.getGuid());
                mapCommission.put("state",1);
                String returnmsg = commissionRemote.cashWithdrawal(JSON.toJSONString(mapCommission));
                JSONObject jsonObject = JSONObject.parseObject(returnmsg);
                Integer code = jsonObject.getInteger("code");
                //JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
                if(code.equals(0)){
                    return orderEntity;
                }else{
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return null;
                }
            }else{
                return null;
            }
        }catch (Exception e){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }finally {
            return null;
        }
    }

    @Override
    @Transactional
    public R cashWithdrawal(String guid) {
        try{
            OrderEntity orderEntity = selectOneByGuid(guid);
            //获取提现人openid
            String user = userRemote.selectOne(orderEntity.getUserGuid());   //获取用户信息
            cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
            String openId = "";
            if(userJson.getInt("code") == 0){
                cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                openId = userData.getStr("openid");
            }else{
                return R.error(Content.STATUS_CODE_5006,"获取用户信息失败！");
            }
            //修改佣金表数据表示提现
            String returnmsg = commissionRemote.conduct(orderEntity.getGuid());
            JSONObject jsonObject = JSONObject.parseObject(returnmsg);
            Integer code = jsonObject.getInteger("code");
            if(code.equals(0)){
                String ip = IpKit.getRealIp(request);
                if(StringUtil.isEmpty(ip)){
                    ip = "127.0.0.1";
                }
                WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
                Map<String, String> params = SendReadPackModel.builder()
                        .nonce_str(WxPayKit.generateStr())
                        .mch_billno(orderEntity.getSerialNumber())
                        .mch_id(wxPayApiConfig.getMchId())
                        .wxappid(wxPayApiConfig.getAppId())
                        .send_name("九洲高尔夫")
                        //.re_openid(BaseContentHandler.getOpenid())
                        .re_openid(openId)
                        .total_amount(orderEntity.getOrderMoney().toString())//付款金额 单位分
                        .total_num("1") //红包发放总人数
                        .wishing("佣金提现")   //红包祝福语
                        .client_ip(ip)  //ip
                        .act_name("佣金提现")   //活动名称
                        .remark("点击领取")   //备注
                        .build().toMap();
                //.createSign(wxPayApiConfig.getPartnerKey(),SignType.HMACSHA256);
                String sign = getWechatPaySign(params, wxPayApiConfig.getPartnerKey());
                params.put("sign", sign);
                // String result = WechatUtils.sendHttpsWx(WxUrlEnum.SEND_RED_BAG.getCode(),params, wxPayApiConfig.getCertPath(), wxPayApiConfig.getMchId());
                String result = WxPayApi.sendRedPack(params,wxPayApiConfig.getCertPath(),wxPayApiConfig.getMchId());
                System.out.println(result);
                Map<String, String> resultMap = WxPayKit.xmlToMap(result);
                String resultCode = resultMap.get("result_code");
                if (WxPayKit.codeIsOk(resultCode)) {
                    //发放成功
                    System.out.println("发放成功！");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    orderEntity.setPayTime(formatter.format(new Date()));//提现成功时间
                    orderEntity.setState(1);
                    orderEntity.setPayMoney(orderEntity.getOrderMoney());
                    orderEntity.setBusinConfi(2);
                    updateByPrimaryKeySelective(orderEntity);
                    return R.ok().putData("发放成功");
                }
            }else{
                return R.error(Content.STATUS_CODE_5006,"修改佣金列表失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.error(Content.STATUS_CODE_5006,"操作失败");
    }

    @Override
    @Transactional
    public OrderEntity directlyWithdrawal(Integer totalAmount, String[] commissionGuids) {
        try{
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            orderEntity.setOpenid(BaseContentHandler.getOpenid());//用户openid
            orderEntity.setUserGuid(BaseContentHandler.getUserGuid());//用户guid
            orderEntity.setName("佣金提现");//订单名称
            orderEntity.setOrderMoney(totalAmount);//订单金额
            orderEntity.setType(2);//2提现
            orderEntity.setState(0);//0失败
            orderEntity.setPayType(0);//0微信
            //payMoney   提现金额 提现成功后插入
            //payTime    提现时间 提现成功后插入
            orderEntity.setBusinConfi(1);  //后台审核确认状态  1待确认  2已确认  3商家拒绝  4已支付

            //设置流水号
            OrderEntity countOrder = new OrderEntity();
            int orderCount = selectCount(countOrder);
            orderCount = orderCount +1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //制定输出格式
            String serialNumber = "YJTX0"+sdf.format(new Date())+orderCount;
            orderEntity.setSerialNumber(serialNumber);  //流水号
            orderEntity.setGoodsGuid(serialNumber);//商品guid ，提现时候没有商品guid  设定为流水号
            int nRet = insertSelective(orderEntity);
            if(nRet > 0){
                Map<String,Object> mapCommission = new HashMap<>();
                mapCommission.put("guids",commissionGuids);
                mapCommission.put("userGuid",BaseContentHandler.getUserGuid());
                mapCommission.put("totalAmount",totalAmount);
                mapCommission.put("orderGuid",orderEntity.getGuid());
                mapCommission.put("state",2);
                String returnmsg = commissionRemote.cashWithdrawal(JSON.toJSONString(mapCommission));
                JSONObject jsonObject = JSONObject.parseObject(returnmsg);
                Integer code = jsonObject.getInteger("code");
                //JSONObject dataJson = JSONObject.parseObject(jsonObject.getString("data"));
                if(code.equals(0)){
                    String ip = IpKit.getRealIp(request);
                    if(StringUtil.isEmpty(ip)){
                        ip = "127.0.0.1";
                    }
                    WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
                    Map<String, String> params = SendReadPackModel.builder()
                            .nonce_str(WxPayKit.generateStr())
                            .mch_billno(orderEntity.getSerialNumber())
                            .mch_id(wxPayApiConfig.getMchId())
                            .wxappid(wxPayApiConfig.getAppId())
                            .send_name("九洲高尔夫")
                            //.re_openid(BaseContentHandler.getOpenid())
                            .re_openid(BaseContentHandler.getOpenid())
                            .total_amount(orderEntity.getOrderMoney().toString())//付款金额 单位分
                            .total_num("1") //红包发放总人数
                            .wishing("佣金提现")   //红包祝福语
                            .client_ip(ip)  //ip
                            .act_name("佣金提现")   //活动名称
                            .remark("点击领取")   //备注
                            .build().toMap();
                    //.createSign(wxPayApiConfig.getPartnerKey(),SignType.HMACSHA256);
                    String sign = getWechatPaySign(params, wxPayApiConfig.getPartnerKey());
                    params.put("sign", sign);
                    // String result = WechatUtils.sendHttpsWx(WxUrlEnum.SEND_RED_BAG.getCode(),params, wxPayApiConfig.getCertPath(), wxPayApiConfig.getMchId());
                    String result = WxPayApi.sendRedPack(params,wxPayApiConfig.getCertPath(),wxPayApiConfig.getMchId());
                    System.out.println(result);
                    Map<String, String> resultMap = WxPayKit.xmlToMap(result);
                    String resultCode = resultMap.get("result_code");
                    if (WxPayKit.codeIsOk(resultCode)) {
                        return orderEntity;
                    }else{
                        return null;
                    }
                }else{
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return null;
                }
            }else{
                return null;
            }
        }catch (Exception e){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }finally {
            return null;
        }
    }

    @Override
    public String callbackWithdrawal(String strJson) {
        try{
            cn.hutool.json.JSONObject jsonObject = new cn.hutool.json.JSONObject(strJson);
            String userGuid = jsonObject.getStr("userGuid");      //用户guid
            Integer totalAmount = jsonObject.getInt("totalAmount");   //总金额

            //获取提现人openid
            String user = userRemote.selectOne(userGuid);   //获取用户信息
            cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
            String openId = "";
            if(userJson.getInt("code") == 0){
                cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                openId = userData.getStr("openid");
            }else{
                return null;
            }

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderNumber(WxPayKit.generateStr());//订单号
            orderEntity.setOpenid(openId);//用户openid
            orderEntity.setUserGuid(userGuid);//用户guid
            orderEntity.setName("佣金提现");//订单名称
            orderEntity.setOrderMoney(totalAmount);//订单金额
            orderEntity.setType(2);//2提现
            orderEntity.setState(0);//0失败
            orderEntity.setPayType(0);//0微信
            orderEntity.setCreator(userGuid);
            //payMoney   提现金额 提现成功后插入
            //payTime    提现时间 提现成功后插入
            //orderEntity.setBusinConfi(1);  //后台审核确认状态  1待确认  2已确认  3商家拒绝  4已支付

            //设置流水号
            OrderEntity countOrder = new OrderEntity();
            int orderCount = selectCount(countOrder);
            orderCount = orderCount +1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //制定输出格式
            String serialNumber = "YJTX0"+sdf.format(new Date())+orderCount;
            orderEntity.setSerialNumber(serialNumber);  //流水号
            orderEntity.setGoodsGuid(serialNumber);//商品guid ，提现时候没有商品guid  设定为流水号
            int nRet = insertSelective(orderEntity);
            if(nRet > 0){
                String ip = IpKit.getRealIp(request);
                if(StringUtil.isEmpty(ip)){
                    ip = "127.0.0.1";
                }
                WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();
                Map<String, String> params = SendReadPackModel.builder()
                        .nonce_str(WxPayKit.generateStr())
                        .mch_billno(orderEntity.getSerialNumber())
                        .mch_id(wxPayApiConfig.getMchId())
                        .wxappid(wxPayApiConfig.getAppId())
                        .send_name("九洲高尔夫")
                        //.re_openid(BaseContentHandler.getOpenid())
                        .re_openid(openId)
                        .total_amount(orderEntity.getOrderMoney().toString())//付款金额 单位分
                        .total_num("1") //红包发放总人数
                        .wishing("佣金提现")   //红包祝福语
                        .client_ip(ip)  //ip
                        .act_name("佣金提现")   //活动名称
                        .remark("点击领取")   //备注
                        .build().toMap();
                String sign = getWechatPaySign(params, wxPayApiConfig.getPartnerKey());
                params.put("sign", sign);
                String result = WxPayApi.sendRedPack(params,wxPayApiConfig.getCertPath(),wxPayApiConfig.getMchId());
                System.out.println(result);
                Map<String, String> resultMap = WxPayKit.xmlToMap(result);
                String resultCode = resultMap.get("result_code");
                if (WxPayKit.codeIsOk(resultCode)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    orderEntity.setPayTime(formatter.format(new Date()));//提现成功时间
                    orderEntity.setState(1);
                    orderEntity.setPayMoney(orderEntity.getOrderMoney());
                    int updateFlag = updateByPrimaryKeySelective(orderEntity);
                    if(updateFlag > 0){
                        return orderEntity.getGuid();
                    }else{
                        //事务回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return "";
                    }
                }else{
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return "";
                }
            }else{
                //事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "";
            }
        }catch (Exception e){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }finally {
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "";
        }
    }

    @Override
    public void timeoutOrderCancel() {
        List<OrderEntity> list = dao.selectTimeoutOrder();
        for(OrderEntity order : list){
            cancelOrder(order.getId(),3);//3超时自动取消
        }
    }

    @Override
    public void untreatedOrderNotice() {
        cn.hutool.json.JSONObject accessTokenJson = new cn.hutool.json.JSONObject(wxRemote.getAccessToken());    //获取accessToken
        String accessToken = accessTokenJson.getStr("data");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //制定输出格式
        List<OrderEntity> orderList = dao.selectOrderFailsNotify();
        for(OrderEntity order : orderList){
            Integer count = map.get(order.getGuid());
            if(count == null){//如果没有 或者小于3 就进行通知管理人员
                String user = userRemote.selectOne(order.getUserGuid());   //获取用户信息
                cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
                String userName = "";
                String userPhone = "";
                if(userJson.getInt("code") == 0){
                    cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                    userName = userData.getStr("name");
                    userPhone = userData.getStr("phone");
                }
                String date = "";
                try {
                    date = sdf.format(sdf.parse(order.getDate()));
                }catch (Exception e){
                    System.out.println("时间格式转换异常");
                }
                String pushDate = date + " "+order.getTime();
                //oEHsgw37cbGxPa18O_ILA3e-C3wM   燕姐
                //oEHsgwzCt5KsqjcFjMF5OOrXsM4Q   晖哥
                noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgw37cbGxPa18O_ILA3e-C3wM",pushDate,order.getRemark(),userPhone);
                noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgwzCt5KsqjcFjMF5OOrXsM4Q",pushDate,order.getRemark(),userPhone);
                if(count == null){
                    map.put(order.getGuid(),1);
                }else{
                    map.put(order.getGuid(),count + 1);
                }
            }else{
                if(count < 3){ //小于3 继续通知
                    String user = userRemote.selectOne(order.getUserGuid());   //获取用户信息
                    cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
                    String userName = "";
                    String userPhone = "";
                    if(userJson.getInt("code") == 0){
                        cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                        userName = userData.getStr("name");
                        userPhone = userData.getStr("phone");
                    }
                    String date = "";
                    try {
                        date = sdf.format(sdf.parse(order.getDate()));
                    }catch (Exception e){
                        System.out.println("时间格式转换异常");
                    }
                    String pushDate = date + " "+order.getTime();
                    //oEHsgw37cbGxPa18O_ILA3e-C3wM   燕姐
                    //oEHsgwzCt5KsqjcFjMF5OOrXsM4Q   晖哥
                    noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgw37cbGxPa18O_ILA3e-C3wM",pushDate,order.getRemark(),userPhone);
                    noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgwzCt5KsqjcFjMF5OOrXsM4Q",pushDate,order.getRemark(),userPhone);
                    map.put(order.getGuid(),count + 1);
                }else if(count == 3){  //已经通知了三次，订单还是未处理，通知总后台人员。
                    String user = userRemote.selectOne(order.getUserGuid());   //获取用户信息
                    cn.hutool.json.JSONObject userJson = new cn.hutool.json.JSONObject(user);
                    String userName = "";
                    String userPhone = "";
                    if(userJson.getInt("code") == 0){
                        cn.hutool.json.JSONObject userData = new cn.hutool.json.JSONObject(userJson.getStr("data"));
                        userName = userData.getStr("name");
                        userPhone = userData.getStr("phone");
                    }
                    String date = "";
                    try {
                        date = sdf.format(sdf.parse(order.getDate()));
                    }catch (Exception e){
                        System.out.println("时间格式转换异常");
                    }
                    String pushDate = date + " "+order.getTime();
                    //oEHsgw37cbGxPa18O_ILA3e-C3wM   燕姐
                    //oEHsgwzCt5KsqjcFjMF5OOrXsM4Q   晖哥
                    noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgw37cbGxPa18O_ILA3e-C3wM",pushDate,order.getRemark(),userPhone);
                    noticeLeader(order.getName(),order.getSerialNumber(),accessToken,userName,"oEHsgwzCt5KsqjcFjMF5OOrXsM4Q",pushDate,order.getRemark(),userPhone);
                    map.put(order.getGuid(),count + 1);
                }
            }

        }

        Iterator<Map.Entry<String,Integer>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Integer> entry = it.next();
            String key = entry.getKey();
            boolean flag = orderList.stream().filter(m->m.getGuid().equals(key)).findAny().isPresent();//false 为订单集合中不包含（管理人员已处理，可以从map中删除）
            if(!flag){
                it.remove();
            }
        }
    }

    @Override
    public void groupBuyingBusiness() {
        //查询结束的团购数据列表
        String listGroupBuyingStr = activityRemote.selectGroupBuyingList();
        JSONObject listGroupBuyingJson = JSONObject.parseObject(listGroupBuyingStr);
        Integer code = listGroupBuyingJson.getInteger("code");
        if(code.equals(0)){
            //查询到团购数据，获取商品名称和guid
            JSONArray jsonArray = listGroupBuyingJson.getJSONArray("data");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                //根据结束的团购进行  查询团购订单是否完成
                OrderEntity orderSelect = new OrderEntity();
                orderSelect.setGoodsGuid(jsonObj.getString("guid"));
                orderSelect.setType(0);
                orderSelect.setState(1);
                orderSelect.setGroupBuyingState(1);
                List<OrderEntity> orderList = select(orderSelect);
                List<String> updateOrderList = orderList.stream().map(e -> e.getGuid()).collect(Collectors.toList());
                //判断团购订单量是否达到，该拼团邀请人数
                if(orderList != null && orderList.size() >= jsonObj.getInteger("buyNumber")){
                    //达到拼团要求，进行订单状态修改，修改为2拼团已完成
                    //批量修改
                    OrderEntity orderUpdate = new OrderEntity();
                    orderUpdate.setGroupBuyingState(2);//2拼团已完成
                    Example exampleOrderUpdate = new Example(getClazz());
                    Example.Criteria criteriaOrderUpdate = exampleOrderUpdate.createCriteria();
                    criteriaOrderUpdate.andIn("guid", updateOrderList);
                    int nRetCount = updateByExampleSelective(orderUpdate,exampleOrderUpdate);
                }else if(orderList != null && orderList.size() > 0){
                    //未达到拼团邀请，进行订单退款操作，并进行订单状态修改，修改为3拼团失败已退款
                    //批量修改
                    /*OrderEntity orderUpdate = new OrderEntity();
                    orderUpdate.setGroupBuyingState(3);//3拼团失败已退款
                    Example exampleOrderUpdate = new Example(getClazz());
                    Example.Criteria criteriaOrderUpdate = exampleOrderUpdate.createCriteria();
                    criteriaOrderUpdate.andIn("guid", updateOrderList);
                    int nRetCount = updateByExampleSelective(orderUpdate,exampleOrderUpdate);*/
                    for(OrderEntity orderRefund : orderList){
                        if(orderRefund.getPayMoney() != null && orderRefund.getPayMoney() > 0){
                            String refundMsg = wxPayUtils.refund(orderRefund);
                            System.out.println(refundMsg);
                            orderRefund.setGroupBuyingState(3);
                            orderRefund.setType(1);
                            updateByPrimaryKeySelective(orderRefund);
                        }else{
                            orderRefund.setGroupBuyingState(3);
                            updateByPrimaryKeySelective(orderRefund);
                        }
                    }
                }
            }
        }
    }


    public String noticeLeader(String productsName,String serialNumber,String accessToken,String userName, String openId,String pushDate,String orderRemark,String phone){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        //设置消息模板
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        //根据具体模板参数组装
        params.put("first", WechatTemplate.item("有新预定请及时处理", "#FF0000"));   //标题
        params.put("keyword1", WechatTemplate.item(serialNumber, "#FF0000"));           //订单编号
        params.put("keyword2", WechatTemplate.item(userName+"预定"+productsName+"，开球时间："+pushDate+"，预订人电话："+phone+",击球人信息："+orderRemark, "#FF0000")); //详情
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("TP_EHQ50gpwDmjndVVHobMoadk4bKVLqWSDTvXXqbfE");   //微信模板id
        wechatTemplate.setTouser(openId); //openId
        wechatTemplate.setUrl("http://wap.oneagles.com/admin/wap/H5_order_list.html");
        wechatTemplate.setData(params);
        String code = PushMessage.send_template_message(wechatTemplate,accessToken);
        return code;
    }


    public static void main(String[] args) {
        if (WxPayKit.codeIsOk("SUCCESS")) {
            System.out.println(222);
        }else{
            System.out.println(333);
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


    //微信公众现金发放，IJPay接口调用签名错误，改用次方法生成签名
    public static String getWechatPaySign(Map<String, String> parameters, String payKey) {
        SortedMap<String,String> sort = new TreeMap<String,String>(parameters);
        StringBuffer sb = new StringBuffer();
        // 所有参与传参的参数按照ACCSII排序（升序）
        Set es = sort.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"serialVersionUID".equals(k)) {
                sb.append(k + "=" + v + "&");
                //System.err.println(k + "\t" + v);
            }
        }
        sb.append("key=" + payKey);
        return SecureUtil.md5(sb.toString()).toUpperCase();
    }
}