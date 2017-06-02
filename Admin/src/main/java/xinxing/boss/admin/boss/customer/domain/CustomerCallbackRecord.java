package xinxing.boss.admin.boss.customer.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 采购商产品信息表
 * @author lgy
 *
 */
@Entity
@Table(name="boss_customer_callback")
@DynamicInsert @DynamicUpdate
public class CustomerCallbackRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orderId; 
	private String orderKey; 
	private String callbackAddress; 
	private Integer status; 
	private String callbackData; 
	private Integer callbackTimes; 
	private String callbackTime; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique=true,nullable=false) 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="orderid", length=11)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Column(name="orderkey", length=50)
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	@Column(name="callbackaddress", length=500)
	public String getCallbackAddress() {
		return callbackAddress;
	}

	public void setCallbackAddress(String callbackAddress) {
		this.callbackAddress = callbackAddress;
	}
	@Column(name="status", length=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="callbackdata", length=500)
	public String getCallbackData() {
		return callbackData;
	}

	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}
	@Column(name="callbacktimes", length=2)
	public Integer getCallbackTimes() {
		return callbackTimes;
	}

	public void setCallbackTimes(Integer callbackTimes) {
		this.callbackTimes = callbackTimes;
	}
	@Column(name="callbacktime", length=20)
	public String getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(String callbackTime) {
		this.callbackTime = callbackTime;
	}
}
