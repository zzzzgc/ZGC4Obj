package xinxing.boss.admin.common.schedule.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.boss.other.domain.AlarmLinkman;
import xinxing.boss.admin.boss.other.service.AlarmLinkmanService;
import xinxing.boss.admin.common.msg.MsgUtils;
import xinxing.boss.admin.common.msg.QiYeMessage;
import xinxing.boss.admin.common.schedule.ChargeAlarmScheduleService;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class ChargeAlarmScheduleServiceImpl implements ChargeAlarmScheduleService {
	private static Logger log = LoggerFactory.getLogger(ChargeAlarmScheduleServiceImpl.class);
	@Autowired
	private AlarmLinkmanService alarmLinkmanService;

	@Override
	public void doScheduleJob() throws Exception {
		Long beginDate = new Date().getTime();
		log.error("updateCSV每月定时任务启动:" + DateUtils.dateFormat(new Date()));

		log.error("updateCSV每日定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (beginDate - new Date().getTime()) / 1000);
	}

	public void chargeAlarm() {
//		boolean isOK = false;
//		List<String> weiXinIds = new ArrayList<>();
//
//		if (isOK) { // 发短信
//			List<AlarmLinkman> alarmLinkman = alarmLinkmanService.search("from AlarmLinkman where status = 1");
//			// List<AlarmLinkman> alarmLinkman = alarmLinkmanService.getAlarmLinkman();
//			for (int i = 0, len = alarmLinkman.size(); i < len; i++) {
//				AlarmLinkman contacts = alarmLinkman.get(i);
//				if (contacts.getRole() == 0) {// 内部员工
//					if (StringUtils.contains(contacts.getType(), AlarmLinkman.bossTag)) {
//						MsgUtils.sendAlarmMsg(contacts.getPhone(), supplierName, count);
//						weiXinIds.add(contacts.getWeixinId());
//					}
//				} else {
//					// if(StringUtils.contains(contacts.getType(), bossTag)){
//					// MsgUtils.sendAlarmMsg(contacts.getPhone(), "供货商", count);
//					// }
//				}
//			}
//		}
//		if (weiXinIds.size() > 0) {
//			String qiyeMsg = QiYeMessage.getContinueFailMessage(Constants.getString("sys_boss_name"), supplierName, count);
//			QiYeMessage.sendQiYeMessage(qiyeMsg, StringUtils.join(weiXinIds, "|"));
//		}
	}
}
