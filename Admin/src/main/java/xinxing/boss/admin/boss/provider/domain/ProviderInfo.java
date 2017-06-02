package xinxing.boss.admin.boss.provider.domain;

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


@Entity
@Table(name = "boss_provider")
@DynamicInsert @DynamicUpdate
public class ProviderInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7773299863653604451L;

	private int id;
	private String providerName;
	private String providerNumber;
	private String providerContact;
	private String providerAddress;
	private Double discount;
	private String province;
	/*private String city;*/
	private Date addTime;
	private String remark;
	private Integer isCallback;
	private Integer status;
	private BigDecimal balance;
	private String supplier;
	private String forbinCustomer;
	private String allowCustomer;
	private BigDecimal sellsMoney;
	private String operator;
	private Double alarmBalance;
	private Integer allowLossMoney;
	
	
	@Column(name = "allowlossmoney", unique = true, nullable = false)
	public Integer getAllowLossMoney() {
		return allowLossMoney;
	}

	public void setAllowLossMoney(Integer allowLossMoney) {
		this.allowLossMoney = allowLossMoney;
	}

	@Column(name = "alarmbalance", unique = true, nullable = false)
	public Double getAlarmBalance() {
		return alarmBalance;
	}

	public void setAlarmBalance(Double alarmBalance) {
		this.alarmBalance = alarmBalance;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "providername" ,length=50)
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@Column(name = "providernumber" ,length=50)
	public String getProviderNumber() {
		return providerNumber;
	}

	public void setProviderNumber(String providerNumber) {
		this.providerNumber = providerNumber;
	}

	@Column(name = "providercontact" ,length=20)
	public String getProviderContact() {
		return providerContact;
	}

	public void setProviderContact(String providerContact) {
		this.providerContact = providerContact;
	}

	@Column(name = "provideraddress" ,length=100)
	public String getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	@Column(name = "discount" )
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "province" ,length=20)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	/*@Column(name = "city" ,length=20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}*/

	@Column(name = "addtime")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "remark" ,length=100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "iscallback" ,length=2)
	public int getIsCallback() {
		return isCallback;
	}

	public void setIsCallback(Integer isCallback) {
		this.isCallback = isCallback;
	}

	@Column(name = "status" ,length=2)
	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "balance",updatable=false)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "supplier" ,length=20)
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name = "forbincustomer" ,length=1000)
	public String getForbinCustomer() {
		return forbinCustomer;
	}

	public void setForbinCustomer(String forbinCustomer) {
		this.forbinCustomer = forbinCustomer;
	}

	@Column(name = "allowcustomer" ,length=1000)
	public String getAllowCustomer() {
		return allowCustomer;
	}

	public void setAllowCustomer(String allowCustomer) {
		this.allowCustomer = allowCustomer;
	}

	@Column(name = "sellsmoney",updatable=false)
	public BigDecimal getSellsMoney() {
		return sellsMoney;
	}

	public void setSellsMoney(BigDecimal sellsMoney) {
		this.sellsMoney = sellsMoney;
	}

	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
