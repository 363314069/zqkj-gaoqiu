package com.zqkj.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
/**
 * 消息模板
 * @author yinfu
 *
 */
public class TemplateCodeUtil {
	private static final String TEMPLATE_CODE_PATH = "config/template.properties";
	private static Props props = null;
	static {
		try {
			props = new Props(TEMPLATE_CODE_PATH, CharsetUtil.CHARSET_UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTitle(Integer code){
		return getText(code.toString());
	}
	
	public static String getText(Integer code){
		return getText(code.toString());
	}
	public static String getText(String code){
		String msg = props.getProperty(code);
		if(msg == null)
			msg = getText(9001);
		return props.getProperty(code);
	}
	public static void main(String[] args) {
		System.err.println(TemplateCodeUtil.getTitle(20010));//标题
		System.err.println(TemplateCodeUtil.getText(20011));//内容
	}
	
}
