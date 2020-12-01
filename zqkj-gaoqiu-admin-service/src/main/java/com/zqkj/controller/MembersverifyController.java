package com.zqkj.controller;

import com.zqkj.utils.*;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.MembersverifyEntity;
import com.zqkj.service.MembersverifyService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * 会员验证表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-10-12 15:32:13
 */
@Controller
@RequestMapping("/security/membersverify")
@Api(value = "", tags = { "security/membersverify " })
public class MembersverifyController extends BaseController<MembersverifyService, MembersverifyEntity> {


    @ResponseBody
    @RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "分页查询数据列表", notes = "参数为对像的变量,如参数为对像的变量,如{page:1,limit:10}")
    public R pageList(PageUtil<MembersverifyEntity> page,MembersverifyEntity entity) {
        // 查询列表数据
        if(entity != null && StringUtil.isEmpty(entity.getName())){
            entity.setName(null);
        }
        page = service.select(page, entity);
        return R.ok().putData(page.getList()).put("count", page.getCount());
    }


    /**
     * 导入会员列表
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importdata", method = { RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "导入EXCEL", notes = "参数为文件对象")
    public R importData(@RequestParam MultipartFile file){
        Integer count = service.importData(file);
        if(count == -1){
            return R.error(Content.STATUS_CODE_5005,"导入失败！数据与数据库中身份证号有重复请检查！");
        }
        if(count > 0){
            return R.ok("导入成功").putData(count);
        }else{
            return R.error(Content.STATUS_CODE_5005,"导入失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/backsave", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据", notes = "参数为json对像")
    @SysLog("保存数据")
    public R backSave(MembersverifyEntity entity) {
        if(ObjectUtil.isAllNull(entity)){
            return R.error(Content.STATUS_CODE_5005).put("count", 0);
        }
        Integer count = service.backSave(entity);
        if(count > 0){
            return R.ok().putData(entity).put("count", count);
        }else{
            return R.error(Content.STATUS_CODE_5005,"添加失败！已经存在该身份证号码请检查");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/backupdate", method = { RequestMethod.POST})
    @ApiOperation(value = "更新数据", notes = "参数为json对像")
    @SysLog("更新数据")
    public R backUpdate(MembersverifyEntity entity) {
        if(ObjectUtil.isAllNull(entity)){
            return R.error(Content.STATUS_CODE_5001).put("count", 0);
        }
        Integer count = service.backUpdate(entity);
        if(count > 0){
            return R.ok().putData(entity).put("count", count);
        }else{
            return R.error(Content.STATUS_CODE_5005,"修改失败！已经存在该身份证号码请检查");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/verifybin", method = {RequestMethod.POST})
    @ApiOperation(value = "验证绑定会员卡", notes = "")
    public R verifyBin(MembersverifyEntity entity) {
        return service.verifyBin(entity);
    }
}
