package xinxing.boss.admin.common.schedule.order2csv.util;

import java.util.Map;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;

public class OrderInfo2CSVBuilder {

	static String[] fields = new String[] { "orderKey", "customerId", "customerName", "beyoudOperation", "province", "city", "phone", "price",
			"status", "receiveTime", "categoryName", "productScale"};
	/**
	 * 构建订单其他信息
	 * @param info
	 * @param c
	 * @return
	 */
	public static String buildOICSV(OrderInfo info,CustomerInfo c,Map<Integer, ProductCategoryInfo> pcim){
		String pn =  pcim.get(info.getCategoryId()) == null ? "" : pcim.get(info.getCategoryId()).getCategoryName();
		return info.getOrderKey() + "," +
			   info.getCustomerId() + "," +
			   c.getCustomerName() + "," +
			   info.getBeyoudOperation() + "," +
			   info.getProvince() + "," + 
			   info.getCity()+ "," + 
			   info.getPhone()+ "," + 
			   info.getPrice() + "," + 
			   (info.getStatus()==3?"成功" :(info.getStatus()==4?"失败":"充值中"))  + "," + 
			   info.getReceiveTime() + "," + 
			   info.getCategoryId()  + ","  +
			   pn
				;
	}
	
	
}
