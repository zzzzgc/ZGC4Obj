package xinxing.boss.admin.common.schedule.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.service.BossOrderResendRecordService;
import xinxing.boss.admin.common.schedule.SyncStatus2ResendOrderScheduleService;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class SyncStatus2ResendOrderScheduleServiceImpl implements SyncStatus2ResendOrderScheduleService {
	private static final Logger log = Logger.getLogger(SyncStatus2ResendOrderScheduleServiceImpl.class);
	@Autowired
	private BossOrderResendRecordService bossOrderResendRecordService;
	@Autowired
	private OrderInfoService orderInfoService;

	@Override
	public void doScheduleJob() throws Exception {

		syncStatus2ResendOrder(bossOrderResendRecordService, DateUtils.addMonths(new Date(), -2));
	}

	public static void syncStatus2ResendOrder(BossOrderResendRecordService bossOrderResendRecordService, Date startTime) {
		long startLong = System.currentTimeMillis();
		log.info("同步到OrderResendRecord定时任务开始");
		String sql = "update  boss_order_resend_record r,boss_order o set r.finalStatus = o.status where   r.successTime >='"
				+ DateUtils.formatDate(startTime) + "' and r.successTime >='" + DateUtils.formatDate(startTime)
				+ "' and   r.orderId = o.id and r.finalStatus!=3 and r.finalStatus!=4 and o.status in (3,4) ";
		log.info(sql);
		bossOrderResendRecordService.saveOrUpdate(sql);
		log.info("同步到OrderResendRecord定时任务结束,耗时:" + (System.currentTimeMillis() - startLong) / 1000);
	}
}
