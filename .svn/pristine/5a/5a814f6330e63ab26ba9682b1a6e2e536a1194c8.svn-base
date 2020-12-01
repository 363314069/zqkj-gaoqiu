package com.zqkj.utils.mail;

import java.util.Collection;

import com.zqkj.utils.StringUtil;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.GlobalMailAccount;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailUtil;
/**
 * 邮件工具类，基于javax.mail封装 继承cn.hutool.extra.mail.MailUtil
 * @author yinfu
 */

public class MailUtils extends MailUtil{


	/**
	 * 使用配置文件中设置的账户发送邮件，发送单个或多个收件人<br>
	 * 多个收件人、抄送人、密送人可以使用逗号“,”分隔，也可以通过分号“;”分隔
	 * @param to 收件人，可以使用逗号“,”分隔，也可以通过分号“;”分隔
	 * @param cc 抄送人，可以使用逗号“,”分隔，也可以通过分号“;”分隔
	 * @param bcc 密送人，可以使用逗号“,”分隔，也可以通过分号“;”分隔
	 * @param subject 标题
	 * @param content 正文
	 * @param isHtml 是否为HTML
	 */
	public static void send(String to, String cc, String bcc, String subject, String content, boolean isHtml) {
		sendMail(StringUtil.splitAddress(to), StringUtil.splitAddress(cc), StringUtil.splitAddress(bcc), subject, content, isHtml);
	}
	
	
	/**
	 * 发送邮件给多人
	 * @param mailAccount 邮件认证对象
	 * @param tos 收件人列表
	 * @param ccs 抄送人列表，可以为null或空
	 * @param bccs 密送人列表，可以为null或空
	 * @param subject 标题
	 * @param content 正文
	 * @param isHtml 是否为HTML格式
	 */
	public static void sendMail(Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, boolean isHtml) {
		//MailAccount mailAccount, 
		final Mail mail = Mail.create(GlobalMailAccount.INSTANCE.getAccount());
		//可选抄送人
		if(CollUtil.isNotEmpty(ccs)) {
			mail.setCcs(ccs.toArray(new String[ccs.size()]));
		}
		//可选密送人
		if(CollUtil.isNotEmpty(bccs)) {
			mail.setBccs(bccs.toArray(new String[bccs.size()]));
		}
		//发件人
		mail.setTos(tos.toArray(new String[tos.size()]));
		//邮件主题
		mail.setTitle(subject);
		//邮件内容
		mail.setContent(content);
		//是否HTML
		mail.setHtml(isHtml);
		//发送
		mail.send();
	}
	
	
	
}
