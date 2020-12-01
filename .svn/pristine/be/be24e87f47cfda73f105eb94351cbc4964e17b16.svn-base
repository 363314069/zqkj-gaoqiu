package com.zqkj.controller;

import com.zqkj.bean.FileBase64Bean;
import com.zqkj.utils.Base64Util;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;
import com.zqkj.utils.UploadImage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 
 * 业务包公用接口
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-02 11:14:20
 */
@Controller
@RequestMapping("/business/common")
@Api(value = "", tags = { "business/common " })
public class BusinessCommonController{

    @Value("${ueditor.uploadPath}")
    private String uploadPath;//获取上传文件路径

    /**
     * 图片上传
     */
    @ResponseBody
    @RequestMapping(value = "/uploadimg", method = {RequestMethod.POST})
    public R uploadImg(@RequestBody FileBase64Bean fileBase64Bean){
        String relativePath = File.separator + "courtImg" + File.separator + UUID.randomUUID() + "."+ fileBase64Bean.getSuffix();
        String path = uploadPath + relativePath;
        try {
            if(Base64Util.decryptByBase64(fileBase64Bean.getImageFile(),path)){
                return R.ok().putData(File.separator+ "upload" +relativePath);
            }else{
                return R.error(Content.STATUS_CODE_5007,"图片保存失败，请重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(Content.STATUS_CODE_5007,"图片保存异常，请重试！");
        }
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
            String relativePath = File.separator + "courtImg" + File.separator;
            String path = uploadPath + relativePath;
            String end = UploadImage.singleImageCourt(file,path);
            return R.ok().putData(end);
        }
    }
}


