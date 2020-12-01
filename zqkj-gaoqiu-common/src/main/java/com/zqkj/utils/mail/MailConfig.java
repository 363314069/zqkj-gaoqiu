package com.zqkj.utils.mail;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
/**
 * 邮箱配置类mail.setting
 * @author yinfu
 */
public class MailConfig {
	private static final String PATH = "config/mail.setting";
	private static Setting  setting  = null;
	private MailConfig(){ }
	
	static {
		try {
			setting = new Setting(PATH, CharsetUtil.CHARSET_UTF_8, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取邮件发件人
	public static String toUserFrom(){
		return setting.getStr("from");
	}
}
