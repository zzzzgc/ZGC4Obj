package xinxing.boss.admin.boss.provider.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 采购商订单唯一表联合主键类
 * @author Zhuan
 *
 */
public class CustomerOrderKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4353095990172801048L;
	private int customerId;
	private String orderKey;
	private Timestamp addTime;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addTime == null) ? 0 : addTime.hashCode());
		result = prime * result + customerId;
		result = prime * result
				+ ((orderKey == null) ? 0 : orderKey.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrderKey other = (CustomerOrderKey) obj;
		if (addTime == null) {
			if (other.addTime != null)
				return false;
		} else if (!addTime.equals(other.addTime))
			return false;
		if (customerId != other.customerId)
			return false;
		if (orderKey == null) {
			if (other.orderKey != null)
				return false;
		} else if (!orderKey.equals(other.orderKey))
			return false;
		return true;
	}
	
	
	
}
