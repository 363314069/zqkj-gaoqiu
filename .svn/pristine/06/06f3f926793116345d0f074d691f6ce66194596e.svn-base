package com.zqkj.utils;


import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zqkj on 2019/9/28.
 */
public class UploadImage {

    public static String singleImage(MultipartFile file,String uploadPath){
        //获取文件的后缀名
        String savePath = File.separator+new SimpleDateFormat("yyyyMMdd").format(new Date())+File.separator;
        File saveFile = new File(uploadPath + "/img/" +savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        String suffixName = file.getOriginalFilename();
        String filename = UUID.randomUUID()+suffixName.substring(suffixName.indexOf("."));
        File newFile = new File(uploadPath + "/img/" +savePath+filename);
        try {
            file.transferTo(newFile);  //拷贝文件，性能高效，比原先的方便

            //压缩图片
            Thumbnails.of(uploadPath + "/img/" +savePath+filename)//"原图文件的路径"
                    .scale(1f)//图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小
                    .outputQuality(0.5f)//图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差
                    .toFile(uploadPath + "/img/" +savePath+filename);//"压缩后文件的路径"
            return File.separator + "upload"+File.separator+"img"+savePath+filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String singleImageCourt(MultipartFile file,String uploadPath){
        //获取文件的后缀名
        String savePath = File.separator+new SimpleDateFormat("yyyyMMdd").format(new Date())+File.separator;
        File saveFile = new File(uploadPath + "/img/" +savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        String suffixName = file.getOriginalFilename();
        String filename = UUID.randomUUID()+suffixName.substring(suffixName.indexOf("."));
        File newFile = new File(uploadPath + "/img/" +savePath+filename);
        try {
            file.transferTo(newFile);  //拷贝文件，性能高效，比原先的方便

            //压缩图片
            Thumbnails.of(uploadPath + "/img/" +savePath+filename)//"原图文件的路径"
                    .scale(1f)//图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小
                    .outputQuality(0.5f)//图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差
                    .toFile(uploadPath + "/img/" +savePath+filename);//"压缩后文件的路径"
            return File.separator + "upload"+File.separator+ "courtImg"+File.separator+"img"+savePath+filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
