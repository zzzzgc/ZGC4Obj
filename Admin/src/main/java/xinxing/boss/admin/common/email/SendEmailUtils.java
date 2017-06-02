package xinxing.boss.admin.common.email;

import javax.mail.MessagingException;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.common.utils.constants.Constants;

public class SendEmailUtils {
	
	/**
	 * 发送采购商邮件
	 * @param title 标题
	 * @param htmlContent  内容html格式
	 * @throws MessagingException  
	 */
	public static void sendTmallWarnEmail(String title,String htmlContent,String email) throws MessagingException {
		MailSenderInfo mailInfo = new MailSenderInfo();
		String mailHost=Constants.getString("mail_host");
		mailInfo.setMailServerHost(mailHost);
		String mail_port=Constants.getString("mail_port");
		mailInfo.setMailServerPort(mail_port);
		/*mailInfo.setValidate(true);*/
		mailInfo.setUserName("【心行】"); // 实际发送者
		mailInfo.setPassword("");// 您的邮箱密码
		mailInfo.setFromAddress("xinxing"); // 设置发送人邮箱地址
		mailInfo.setToAddress(email); // 设置接受者邮箱地址
		mailInfo.setSubject(title);
		mailInfo.setContent(htmlContent);
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo); // 发送html格式
	}
	
	/**
	 * 构造采购商邮件内容
	 * @param failChargeList
	 * @return
	 */
	public static String tmallWarnEmailContentBuilder(CustomerInfo customerInfo){
			StringBuilder mailContent = new StringBuilder();
			mailContent.append("&nbsp;&nbsp;贵公司于"+customerInfo.getAddTime()+"在我司流量包采购账号已开通！帐号为:"+customerInfo.getLoginName()+",密码为:"+customerInfo.getLoginPaw()+" 登录网址：http://customer.llczxt.com/a/login");
			mailContent.append("<br>本邮件由系统自动发送，请勿直接回复。");
			return mailContent.toString();
	}
	
}
