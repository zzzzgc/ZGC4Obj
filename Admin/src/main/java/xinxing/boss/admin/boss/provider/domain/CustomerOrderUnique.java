package xinxing.boss.admin.boss.provider.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="boss_customer_flowkey")
@DynamicInsert @DynamicUpdate
@IdClass(value=CustomerOrderKey.class)
public class CustomerOrderUnique implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customerId;
	private String orderKey;
	private Date addTime;
	
	@Id
	@Column(name = "customerid", unique = true, nullable = false)
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	@Id
	@Column(name = "orderkey", unique = true, nullable = false)
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	@Column(name = "addtime")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
}
