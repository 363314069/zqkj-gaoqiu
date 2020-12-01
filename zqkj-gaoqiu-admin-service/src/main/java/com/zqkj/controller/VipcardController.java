package com.zqkj.controller;

import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.UploadFile;
import com.zqkj.utils.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqkj.entity.VipcardEntity;
import com.zqkj.service.VipcardService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * 新版会员卡
 * @author zqkj
 * @email root@qq.com
 * @date 2020-10-15 10:28:43
 */
@Controller
@RequestMapping("/security/vipcard")
@Api(value = "", tags = { "security/vipcard " })
public class VipcardController extends BaseController<VipcardService, VipcardEntity> {

    @Value("${ueditor.uploadPath}")
    private String uploadPath;//获取上传文件路径

    /**
     * @function 单文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public R uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            return R.error(Content.STATUS_CODE_5002, "文件为空！");
        } else {
            String end = UploadFile.singleFile(file, uploadPath);
            return R.ok().putData(end);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/listByGuids", method = {RequestMethod.POST})
    @ApiOperation(value = "根据数组Guid批量查询数据", notes = "参数为数组如[1,2,3,4]")
    public R listByGuids(String[] guids) {
        Example example = new Example(getClazz());
        Example.Criteria criteria = example.createCriteria();
        List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
        criteria.andIn("guid", guidList);
        List<VipcardEntity> list = service.selectByExample(example);
        return R.ok().putData(list);
    }
}
