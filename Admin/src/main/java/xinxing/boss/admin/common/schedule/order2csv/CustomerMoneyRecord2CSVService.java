package xinxing.boss.admin.common.schedule.order2csv;

import java.util.Date;


public interface CustomerMoneyRecord2CSVService {
	/**
	 * 按天生成订单记录表
	 * @param number 
	 */
	public void getDayCustomerMoneyRecord2CSV(Date startTime,Date endTime, String fileName);
	
}
