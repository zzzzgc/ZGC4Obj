package test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.xinxing.flow.core.transfer.impl.Transfer_YG_CQ;
import com.xinxing.transfer.common.encry.MD5_HexUtil;
import com.xinxing.transfer.common.ftp.FtpUtil;
import com.xinxing.transfer.common.http.HttpUtils;
import com.xinxing.transfer.common.resource.Constants;
import com.xinxing.transfer.common.time.TimeUtils;
import com.xinxing.transfer.common.txt.TxtUtil;
import com.xinxing.transfer.service.BossCustomerBalanceRecordService;

public class Demo extends test {
	private static final Logger log = Logger.getLogger(Demo.class);

	@Autowired
	Transfer_YG_CQ transfer_YG_CQ;
	@Autowired
	BossCustomerBalanceRecordService bossCustomerBalanceRecordService;

	@Test
	public void send() {
		// 向优狗发送订单请求
		HashMap<String, Object> sendMap = new HashMap<>();
		sendMap.put("CTMID", "600012");
		sendMap.put("CTMORDID", "123456Testqq");
		sendMap.put("CTMTIME", "2017-03-28 10:16:10");
		sendMap.put("PLAYACC", "18820036966");
		sendMap.put("PDTVALUE", "10");
		sendMap.put("CTMRETURL", "http://106.14.18.108:28084/CQExclusive/SCQCallback.do");
		sendMap.put("URPROVID", "0");
		sendMap.put("ISP", "YD");
		sendMap.put("ROAMING_TYPE", "1");
		sendMap.put("INTECMD", "LLCZ");// 包括INTECMD

		String signStr = HttpUtils.getStrByMapOrderByABC(sendMap) + Constants.getString("CQ_Key");
		String sign = MD5_HexUtil.md5Hex(signStr).toLowerCase();
		System.out.println("------------------------------------------------------------------");
		System.out.println("SIGN:" + sign);
		System.out.println("------------------------------------------------------------------");

		sendMap.put("SIGN", sign);// 包括SIGN
		String param = HttpUtils.getStrByMapOrderByABC(sendMap);
		System.out.println("sendMap:" + param);
		System.out.println("------------------------------------------------------------------");
	}

	@SuppressWarnings("all")
	@Test
	public void query() {
		HashMap<String, Object> sendMap = new HashMap<>();
		sendMap.put("CTMID", "600012");
		sendMap.put("CTMORDID", "123456Test11");
		sendMap.put("INTECMD", "DDCX");// 包括INTECMD

		String signStr = HttpUtils.getStrByMapOrderByABC(sendMap) + Constants.getString("CQ_Key");
		String sign = MD5_HexUtil.md5Hex(signStr).toLowerCase();
		System.out.println("------------------------------------------------------------------");
		System.out.println("SIGN:" + sign);
		System.out.println("------------------------------------------------------------------");

		sendMap.put("SIGN", sign);
		String param = HttpUtils.getStrByMapOrderByABC(sendMap);
		System.out.println("sendMap:" + param);
		System.out.println("------------------------------------------------------------------");
	}

	@SuppressWarnings("all")
	@Test
	public void YGCallbackTransfer() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("FlowKey", "123456Testqq");
		map.put("OrderKey", "123456Testqq");
		map.put("Phone", "18820036966");
		map.put("OrderStatus", "Success");
		map.put("FailReason", "");

		String strByMapOrderByABC = HttpUtils.getStrByMapOrderByABC(map);
		String VerifyCode = MD5_HexUtil.md5Hex(strByMapOrderByABC).toUpperCase();
		map.put("VerifyCode", VerifyCode);
		String strByMapOrderByABC2 = HttpUtils.getStrByMapOrderByABC(map);
		System.out.println("param:" + strByMapOrderByABC2);
	}

	@SuppressWarnings("all")
	@Test
	public void transferCallbackCQ() {
		try {
			HashMap<String, Object> map = new HashMap<>();

			map.put("INTECMD", "TZCZ");// 文件头中存在INTECMD(借口套接字)
			map.put("TRADESTATUS", "1");// 交易状态码
			// map.put("TRADEERROR", status); // 交易错误码
			map.put("CTMID", Constants.getString("CQ_MerChant")); // 商户编号
			map.put("CTMORDID", "58a87bef3efcac13efd6"); // 商户交易流水号
			map.put("TRDSN", "58a87bef3efcac13efd6"); // 接入方流水号
			map.put("TRDREQTIME", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:dd")); // 接入方完成时间
			// map.put("TRDACTVALUE", value);// 接入方实际完成充值面值

			String signStr = HttpUtils.getStrByMapOrderByABC(map) + Constants.getString("CQ_Key");
			String SIGN = MD5_HexUtil.md5Hex(signStr).toLowerCase();
			System.out.println("------------------------------------------------------------------");
			System.out.println("SIGN:" + SIGN);
			System.out.println("------------------------------------------------------------------");

			map.put("SIGN", SIGN);
			String param = HttpUtils.getStrByMapOrderByABC(map);
			System.out.println("sendMap:" + param);
			System.out.println("------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("all")
	@Test
	public void TestDDA() {
		try {
			Integer customerId = Integer.parseInt(Constants.getString("CQ_MerChant"));
			String customerName = Constants.getString("CQ_CustomerName");

			log.info("--------------CQ每日流水日志信息获取----------------");
			StringBuilder sb = new StringBuilder();
			// 每日早上7点获取一次流水(昨天的)
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parse = simpleDateFormat.parse("2017-3-23");
			Date now = parse;// 今天
			Date yesterday = TimeUtils.addDayForNow(now, -1);// 昨天

			Date startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(yesterday) + " 00:00:00");
			Date endTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(now) + " 00:00:00");

			// 获取每日余额等信息
			sb.append(transfer_YG_CQ.bossCustomerBalanceRecord4Day(bossCustomerBalanceRecordService, customerName, customerId, now));

			// 获取订单流水
			sb.append(transfer_YG_CQ.YG_DDA2CQ_DDA(customerId, startTime, endTime));

			TxtUtil.createTXT(sb, Constants.getString("CQ_downloadFile"), "优狗充值 " + DateFormatUtils.format(yesterday, "yyyyMMdd") + ".txt", yesterday, log);

			log.info("获取内容:" + sb);
			/*
			 * Integer customerId =
			 * Integer.parseInt(Constants.getString("CQ_MerChant")); String
			 * customerName = Constants.getString("CQ_CustomerName");
			 * StringBuilder sb = new StringBuilder();
			 * 
			 * SimpleDateFormat simpleDateFormat = new
			 * SimpleDateFormat("yyyy-MM-dd"); Date parse =
			 * simpleDateFormat.parse("2017-3-23");
			 * 
			 * DateFormatManager dateFormatManager = new
			 * DateFormatManager("yyyy-MM-dd"); Date parse =
			 * dateFormatManager.parse("2017-03-24"); Date now = parse;// ceshi
			 * Date yesterday = TimeUtils.addDayForNow(now, -1);// 昨天
			 * 
			 * Date startTime =
			 * TimeUtils.getDateByStr(TimeUtils.date2FormateStr(yesterday) +
			 * " 00:00:00"); Date endTime =
			 * TimeUtils.getDateByStr(TimeUtils.date2FormateStr(now) +
			 * " 00:00:00");
			 * 
			 * // 获取每日余额等信息
			 * sb.append(transfer_YG_CQ.bossCustomerBalanceRecord4Day(
			 * bossCustomerBalanceRecordService, customerName, customerId,
			 * now));
			 * 
			 * // 获取订单流水 sb.append(transfer_YG_CQ.YG_DDA2CQ_DDA(customerId,
			 * startTime, endTime));
			 * 
			 * TxtUtil.createTXT(sb, Constants.getString("CQ_downloadFile"),
			 * "优狗充值 " + DateFormatUtils.format(yesterday, "yyyyMMdd") + ".txt",
			 * yesterday, log);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void aaa() {

		String path="/";
		String addr="106.14.18.108";
		int port=20;
		String username="cq";
		String password="TaS12Bb";
		try {
			// 建立ftp连接
			// FTPClient ftp = new FTPHTTPClient(addr, port, username,password);
			FTPClient ftp = new FTPClient();
			int reply;
			ftp.connect(addr);
			log.info("连接到：" + addr + ":" + port);
			log.info(ftp.getReplyString());
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP目标服务器积极拒绝.");
				System.exit(1);
				log.info("-----------------OK--------------------");
			} else {
				ftp.login(username, password);
				ftp.enterLocalPassiveMode();
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftp.changeWorkingDirectory(path);
				log.info("已连接：" + addr + ":" + port);
				
				log.info("-----------------OK--------------------");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info(ex.getMessage());
			log.info("-----------------OOOO--------------------");
		}
	}

	@Test
	public void testtxt() throws UnsupportedEncodingException {
		//TODO 文件名乱码
		Integer customerId = Integer.parseInt(Constants.getString("CQ_MerChant"));
		String customerName = Constants.getString("CQ_CustomerName");
		log.info("--------------CQ每日流水日志信息获取----------------");
		StringBuilder sb = new StringBuilder();
		// 每日早上7点获取一次流水(昨天的)
		Date now = new Date();// 今天
		Date yesterday = TimeUtils.addDayForNow(now, -1);// 昨天

		Date startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(yesterday) + " 00:00:00");
		Date endTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(now) + " 00:00:00");

		// 获取每日余额等信息
		sb.append(transfer_YG_CQ.bossCustomerBalanceRecord4Day(bossCustomerBalanceRecordService, customerName, customerId, now));

		// 获取订单流水
		sb.append(transfer_YG_CQ.YG_DDA2CQ_DDA(customerId, startTime, endTime));


		String port = Constants.getString("CQ_FTP4Ip");
		String username = Constants.getString("CQ_FTP4User");
		String passwd = Constants.getString("CQ_FTP4Passwd");

		String fileName = "优狗充值 " + DateFormatUtils.format(yesterday, "yyyyMMdd") + ".txt";
		String path = DateFormatUtils.format(yesterday,  File.separator+"yyyy" + File.separator + "MM" + File.separator + "dd");
		byte[] data = sb.toString().getBytes("GBK");
		
		FtpUtil ftp = new FtpUtil("/", username, passwd, port);
		if(ftp.saveInFTP("/", fileName, data)){
			log.info("CQ订单流水报表提交成功");
		}else{
			log.info("CQ订单流水报表提交失败");
		}
	}
	
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	@Test
	public void constants() throws UnsupportedEncodingException {
		Locale locale = new Locale("zh", "CN");
		ReloadableResourceBundleMessageSource relo = messageSource;
		String message = relo.getMessage("CQ_CustomerName", null, null, locale);
		System.out.println(message);
		System.out.println(Constants.getString("CQ_CustomerName"));
		System.out.println(relo.getMessage("CQ_CustomerName", null,null, locale));
		System.out.println(com.xinxing.flow.utils.resource.a.getString("CQ_CustomerName"));
	}
	
	@Test
	public void testaaa() throws Exception{
		Integer customerId = Integer.parseInt(Constants.getString("CQ_MerChant"));
		String customerName = Constants.getString("CQ_CustomerName");
		log.info("--------------CQ每日流水日志信息获取----------------");
		StringBuilder sb = new StringBuilder();
		// 每日早上7点获取一次流水(昨天的)
		Date now = new Date();// 今天
		Date yesterday = TimeUtils.addDayForNow(now, -1);// 昨天

		Date startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(yesterday) + " 00:00:00");
		Date endTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(now) + " 00:00:00");

		// 获取每日余额等信息
		sb.append(transfer_YG_CQ.bossCustomerBalanceRecord4Day(bossCustomerBalanceRecordService, customerName, customerId, now));

		// 获取订单流水
		sb.append(transfer_YG_CQ.YG_DDA2CQ_DDA(customerId, startTime, endTime));

		String port = Constants.getString("CQ_FTP4Ip");
		String username = Constants.getString("CQ_FTP4User");
		String passwd = Constants.getString("CQ_FTP4Passwd");

		String fileName = "zroedog " + DateFormatUtils.format(yesterday, "yyyyMMdd") + ".txt";
		//String path = DateFormatUtils.format(yesterday, "yyyy" + File.separator + "MM" + File.separator + "dd");
		byte[] data = sb.toString().getBytes("GBK");
		FtpUtil ftp = new FtpUtil("/", username, passwd, port);
		if(ftp.saveInFTP("/", fileName, data)){
			log.info("CQ订单流水报表提交成功");
		}else{
			log.info("CQ订单流水报表提交失败");
		}
	}
}
