package com.xinxing.flow.shcedule.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.flow.core.transfer.impl.Transfer_YG_CQ;
import com.xinxing.flow.shcedule.CQ_QueryShcedule;
import com.xinxing.transfer.common.ftp.FtpUtil;
import com.xinxing.transfer.common.resource.Constants;
import com.xinxing.transfer.common.time.TimeUtils;
import com.xinxing.transfer.service.BossCustomerBalanceRecordService;

public class CQ_QueryShceduleImpl implements CQ_QueryShcedule {

	private static final Logger log = Logger.getLogger("CQ_Shcedule");

	@Autowired
	BossCustomerBalanceRecordService bossCustomerBalanceRecordService;

	@Autowired
	private Transfer_YG_CQ transfer_YG_CQ;
	private static Integer customerId = Integer.parseInt(Constants.getString("CQ_MerChant"));
	private static String customerName = Constants.getString("CQ_CustomerName");

	@Override
	public void doScheduleJob() throws Exception {
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

	public static void main(String[] args) {
		System.out.println(TimeUtils.date2FormateStr(new Date()));
	}

	@Override
	public void doScheduleJobText() {
		log.info("定时调度方法被调用了,时间:" + new Date());
		System.out.println("定时调度方法被调用了,时间:" + new Date());
	}
}
