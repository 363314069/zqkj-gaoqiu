package com.zqkj.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import java.nio.file.StandardOpenOption;

public class Base64Util {


    /**
     * base64编码转成图片文件
     *
     * @param base64 图片的base64编码
     * @param filePath 图片文件的保存路径
     *
     * @return
     * @throws Exception
     */
    public static boolean decryptByBase64(String base64, String filePath) throws Exception{
        if (base64 == null && filePath == null) {
            return false;
        }
        Files.write(Paths.get(filePath),Base64.decode(base64), StandardOpenOption.CREATE);
        return true;
    }

    /**
     * 在线图片转base64编码
     * 下载图片并转换成base64格式
     *
     * @param imageUrl 图片URL
     *
     * @return 图片base64编码
     */
    private String downLoadImageToBase64(String imageUrl) throws Exception{
        if(StringUtils.isBlank(imageUrl)){
            throw new Exception("人脸识别，人脸图片url不能为空");
        }
        //下载图片
        BufferedImage image =null;
        URL url = new URL(imageUrl);
        image = ImageIO.read(url);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String type = StringUtils.substring(imageUrl, imageUrl.lastIndexOf(".") + 1);
        ImageIO.write(image, type, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageString = Base64.encode(imageBytes);
        bos.close();

        if(StringUtils.isBlank(imageString)){
            throw new Exception("获取人脸图片base64编码失败");
        }
        return imageString;
    }



    /**
     * 本地图片转base64编码
     *
     * @param filePath 文件图片所在路径
     *
     * @return base64编码
     */
    public String imageToBase64(String filePath) throws Exception{
        if(StringUtils.isBlank(filePath)){
            return null;
        }
        String encode="";
        try{
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            encode = Base64.encode(bytes);
        }catch (Exception e){
            throw e;
        }
        return encode;
    }


    /**
     * 网络图片转换Base64的方法
     *
     * @param netImagePath     
     */
    private static void NetImageToBase64(String netImagePath) {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(netImagePath);
            final byte[] by = new byte[1024];
            // 创建链接
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = conn.getInputStream();
                        // 将内容读取内存中
                        int len = -1;
                        while ((len = is.read(by)) != -1) {
                            data.write(by, 0, len);
                        }
                        // 对字节数组Base64编码
                        BASE64Encoder encoder = new BASE64Encoder();
                        String strNetImageToBase64 = encoder.encode(data.toByteArray());
                        System.out.println("网络图片转换Base64:" + strNetImageToBase64);
                        // 关闭流
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
