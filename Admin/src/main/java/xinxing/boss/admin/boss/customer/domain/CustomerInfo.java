package xinxing.boss.admin.boss.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 采购商信息记录
 * 
 * @author lgy
 * 
 */
@Entity
@Table(name = "boss_customer")
@DynamicInsert
@DynamicUpdate
public class CustomerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String customerName;
	private String customerNumber;
	private String customerContact;
	private BigDecimal balance;
	private Date addTime;
	private String remark;
	private String callbackAddress;
	private String devKey; //开发者密钥
	private String customerAddress;
	private String loginName;
	private String loginPaw;
	private Integer status;
	private Integer tickType;
	private String allowIp;
	private Integer priority; //优先级
	private String cusSimpleName;
	private Integer isBackCost;
	private String email;
	private Integer isAutoConfig;//是否向下兼容
	private Integer isManualCallback;//是否手工回调
	private Integer alarmBalance;
	private Integer configLevel; //兼容等级
	private Integer isAlarm;//是否触发报警
	//是否发送短信0否1是 @author wuzl
	private Integer isSendMsg;
	@Column(name = "isalarm", length=11)
	public Integer getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(Integer isAlarm) {
		this.isAlarm = isAlarm;
	}

	@Column(name = "alarmbalance", length=11)
	public Integer getAlarmBalance() {
		return alarmBalance;
	}

	public void setAlarmBalance(Integer alarmBalance) {
		this.alarmBalance = alarmBalance;
	}

	@Column(name = "allowip", length=1000)
	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}
	@Column(name = "priority", length=2)
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "customername", length=50)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customernumber", length=50)
	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Column(name = "customercontact", length=20)
	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	@Column(name = "balance",updatable=false)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "addtime")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "remark", length=100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "callbackaddress", length=500)
	public String getCallbackAddress() {
		return callbackAddress;
	}

	public void setCallbackAddress(String callbackAddress) {
		this.callbackAddress = callbackAddress;
	}

	@Column(name = "devkey", length=100)
	public String getDevKey() {
		return devKey;
	}

	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}

	@Column(name = "customeraddress", length=100)
	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Column(name = "loginname", length=100)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "loginpaw", length=100)
	public String getLoginPaw() {
		return loginPaw;
	}

	public void setLoginPaw(String loginPaw) {
		this.loginPaw = loginPaw;
	}

	@Column(name = "status", length=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "ticktype", length=2)
	public Integer getTickType() {
		return tickType;
	}

	public void setTickType(Integer tickType) {
		this.tickType = tickType;
	}
	
	@Column(name = "cussimplename", length=20)
	public String getCusSimpleName() {
		return cusSimpleName;
	}

	public void setCusSimpleName(String cusSimpleName) {
		this.cusSimpleName = cusSimpleName;
	}

	@Column(name = "isbackcost", length=20)
	public Integer getIsBackCost() {
		return isBackCost;
	}

	public void setIsBackCost(Integer isBackCost) {
		this.isBackCost = isBackCost;
	}
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "isautoconfig", length=2)
	public Integer getIsAutoConfig() {
		return isAutoConfig;
	}

	public void setIsAutoConfig(Integer isAutoConfig) {
		this.isAutoConfig = isAutoConfig;
	}
	@Column(name = "ismanualcallback", length=2)
	public Integer getIsManualCallback() {
		return isManualCallback;
	}

	public void setIsManualCallback(Integer isManualCallback) {
		this.isManualCallback = isManualCallback;
	}
	
	
	@Column(name = "configlevel")
	public Integer getConfigLevel() {
		return configLevel;
	}

	public void setConfigLevel(Integer configLevel) {
		this.configLevel = configLevel;
	}
	@Column(name = "issendmsg", unique = true, nullable = false)
	public Integer getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(Integer isSendMsg) {
		this.isSendMsg = isSendMsg;
	}
	@Override
	public String toString() {
		return "CustomerInfo [id=" + id + ", customerName=" + customerName
				+ ", customerNumber=" + customerNumber + ", customerContact="
				+ customerContact + ", balance=" + balance + ", addTime="
				+ addTime + ", remark=" + remark + ", callbackAddress="
				+ callbackAddress + ", devKey=" + devKey + ", customerAddress="
				+ customerAddress + ", loginName=" + loginName + ", loginPaw="
				+ loginPaw + ", status=" + status + ", tickType=" + tickType
				+ ", allowIp=" + allowIp + ", priority=" + priority
				+ ", cusSimpleName=" + cusSimpleName + ", isBackCost="
				+ isBackCost + ", email=" + email + ", isAutoConfig="
				+ isAutoConfig + ", isManualCallback=" + isManualCallback
				+ ", alarmBalance=" + alarmBalance + ", isSendMsg" + isSendMsg + "]";
	}
	
	
}
