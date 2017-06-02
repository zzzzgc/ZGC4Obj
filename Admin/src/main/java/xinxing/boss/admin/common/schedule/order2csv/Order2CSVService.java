package xinxing.boss.admin.common.schedule.order2csv;

import java.util.Date;


public interface Order2CSVService {

	/**
	 * 按天生成订单记录表
	 * @param number 
	 */
	public void getCustomerDayOrder2CSV(Date startTime,Date endTime, String fileName);
	
	/**
	 * 按月生成订单表
	 */
	public void getCustomerMonthOrder2CSV();
	
}
