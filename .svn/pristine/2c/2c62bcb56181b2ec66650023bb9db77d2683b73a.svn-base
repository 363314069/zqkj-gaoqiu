package com.zqkj.controller;

import com.zqkj.entity.ActivityEntity;
import com.zqkj.service.ActivityService;
import com.zqkj.utils.*;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 活动表
 */
@Controller
@RequestMapping("/business/activity")
@Api(value = "活动表", tags = { "activity 活动表" })
public class ActivityController extends BaseController<ActivityService, ActivityEntity> {

    @Value("${ueditor.uploadPath}")
    private String uploadPath;//获取上传文件路径


    @ResponseBody
    @RequestMapping(value = "/saveActivity", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据", notes = "参数为json对像")
    @SysLog("保存数据")
    public R saveActivity(@RequestBody ActivityEntity entity) {
        entity.setId(null);
        entity.setGuid(null);
        Integer count = service.saveActivity(entity);
        return R.ok().putData(entity).put("count", count);
    }



    @ResponseBody
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
    @ApiOperation(value = "修改数据", notes = "参数为json对像")
    @SysLog("修改数据")
    public R updateActivity(@RequestBody ActivityEntity entity) {
        Integer count = service.updateActivity(entity);
        return R.ok().putData(entity).put("count", count);
    }


    /**
     * @function 单文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
    @ResponseBody
    public R UploadImage(@RequestParam(value = "imgs", required = false) MultipartFile file){
        if(file == null){
            return R.error(Content.STATUS_CODE_5002,"图片为空！");
        }else{
            String end = UploadImage.singleImage(file,uploadPath);
            return R.ok().putData(end);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectOne(@RequestBody String guid) {
        ActivityEntity userEntity = service.selectOneByGuid(guid);
        if(userEntity == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(userEntity);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET, RequestMethod.POST})
    public R selectList(@RequestBody ActivityEntity entity) {
        List<ActivityEntity> userEntity = service.selectList(entity);
        if(userEntity == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(userEntity);
        }
    }



    @ResponseBody
    @RequestMapping(value = "/notificaCallback", method = { RequestMethod.GET, RequestMethod.POST})
    public R notificationCallback(String sourceGuid,String userGuid,String orderNumber,String serialNumber,Integer sum) {
        return service.notificationCallback(sourceGuid,userGuid,orderNumber,serialNumber,sum);
    }


    /**
     * 活动回调通知
     */
    @ResponseBody
    @RequestMapping(value = "/notificaActivityCallback", method = { RequestMethod.GET, RequestMethod.POST})
    public R notificaActivityCallback(String sourceGuid,String userGuid,String orderNumber,String serialNumber,Integer sum, String orderGuid) {
        return service.notificaActivityCallback(sourceGuid,userGuid,orderNumber,serialNumber,sum,orderGuid);
    }


    /**
     * 会员产品回调通知
     */
    @ResponseBody
    @RequestMapping(value = "/notificaMemberAreaCallback", method = { RequestMethod.GET, RequestMethod.POST})
    public R notificaMemberAreaCallback(String sourceGuid,String userGuid,String orderNumber,String serialNumber,Integer sum) {
        return service.notificaMemberAreaCallback(sourceGuid,userGuid,orderNumber,serialNumber,sum);
    }

    /**
     * 酒店回调通知
     */
    @ResponseBody
    @RequestMapping(value = "/hotelcallback", method = { RequestMethod.GET, RequestMethod.POST})
    public R hotelCallback(String orderGuid,String creator) {
        return service.hotelCallback(orderGuid,creator);
    }

    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<ActivityEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }


    @ResponseBody
    @RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
    public R selectListPage(PageUtil<ActivityEntity> page, ActivityEntity entity) {
        // 查询列表数据
        page = service.selectListPage(page, entity);
        return R.ok().putData(page.getList()).put("count", page.getCount());
    }


    @ResponseBody
    @RequestMapping(value = "/selectGroupBuyingList", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "查询团购结束的数据")
    public R selectGroupBuyingList() {
        ActivityEntity entity = new ActivityEntity();
        List<ActivityEntity> list = service.selectGroupBuyingList(entity);
        if(list == null){
            return R.error(Content.STATUS_CODE_5004);
        }else{
            return R.ok().putData(list);
        }
    }
}
