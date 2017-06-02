package com.xinxing.transfer.common.email;

import javax.mail.MessagingException;

import com.xinxing.flow.utils.resource.a;

public class EmailUtils {

	/**
	 * 发送邮件信息
	 * 
	 * @param title
	 *            标题
	 * @param htmlContent
	 *            内容html格式
	 * @param accountName
	 *            发送邮箱名字【tmall<tmall@xxxx>】
	 * @param receiveAccount
	 *            接收账号，用逗号隔开
	 * @throws MessagingException
	 */
	public static void sendEmail(String title, String htmlContent,
			String accountName, String receiveAccount)
			throws MessagingException {
		MailSenderInfo mailInfo = new MailSenderInfo();
		String mailHost = a.getString("mail_host");
		mailInfo.setMailServerHost(mailHost);
		String mail_port = a.getString("mail_port");
		mailInfo.setMailServerPort(mail_port);
		mailInfo.setUserName(accountName); // 实际发送者
		mailInfo.setPassword("");// 您的邮箱密码
		mailInfo.setFromAddress(accountName); // 设置发送人邮箱地址
		mailInfo.setToAddress(receiveAccount); // 设置接受者邮箱地址
		mailInfo.setSubject(title);
		htmlContent += "<br>此封电子邮件为系统自动发出，请勿回复。";
		mailInfo.setContent(htmlContent);
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo); // 发送html格式
	}
}
