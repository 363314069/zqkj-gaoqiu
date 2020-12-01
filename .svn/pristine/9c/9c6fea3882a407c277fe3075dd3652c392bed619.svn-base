package com.zqkj.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;

public class ConfigUtil {
	
	private static final String STATUS_CODE_PATH = "config/conf.properties";
	private static Props props = null;
	static {
		try {
			props = new Props(STATUS_CODE_PATH, CharsetUtil.CHARSET_UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(Object key){
		return props.get(key);
	}
	
	/**
	 * 服務器上传目录地址
	 * @return
	 */
	public static String ftpUploadDirectory(){
		return props.getStr("ftp.upload.directory");
	}
	
	/**
	 * 服務器上传图片目录地址
	 * @return
	 */
	public static String ftpImagesDirectory(){
		return props.getStr("ftp.images.directory");
	}
	
	
	/**
	 * 服務器上传视频目录地址
	 * @return
	 */
	public static String ftpVideoDirectory(){
		return props.getStr("ftp.video.directory");
	}
	
	
	/**
	 * FTP域名或者IP
	 * @return
	 */
	public static String ftpHost(){
		return props.getStr("ftp.host");
	}
	
	/**
	 * FTP服務器端口
	 * @return
	 */
	public static int ftpPort(){
		return props.getInt("ftp.port");
	}
	
	/**
	 * FTP服務器账号
	 * @return
	 */
	public static String ftpUser(){
		return props.getStr("ftp.user");
	}
	
	/**
	 * FTP服務器密码
	 * @return
	 */
	public static String ftpPassWord(){
		return props.getStr("ftp.password");
	}
	
	

	
	public static void main(String[] args) {
		System.err.println(props.getInt("ftp.port"));
	}
	

}
