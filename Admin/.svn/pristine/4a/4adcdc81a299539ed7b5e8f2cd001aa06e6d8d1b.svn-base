package xinxing.boss.admin.boss.provider.domain;

import java.io.Serializable;
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
@Table(name = "boss_provider_product")
@DynamicInsert @DynamicUpdate
public class ProviderProductInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int providerId;
	private String productName;
	private Double costPrice;
	private Integer categoryId;
	private String province;
	/*private String city;*/
	private Integer priority;
	private Integer status;
	private String mobileOperator;
	private Date addTime;
	private String remark;
	private String productCode;
	private String forbidCustomer;
	private String allowCustomer;
	private Integer isSecondChannel;
	private Integer isSendMsg;//是否发送短信0否1是
	private Date updateTime;//添加时间
	//有效期
	private String validityTime;
	@Column(name = "updatetime", unique = true, nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "issendmsg", unique = true, nullable = false)
	public Integer getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(Integer isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	@Column(name = "issecondchannel", unique = true, nullable = false)
	public Integer getIsSecondChannel() {
		return isSecondChannel;
	}

	public void setIsSecondChannel(Integer isSecondChannel) {
		this.isSecondChannel = isSecondChannel;
	}

	@Column(name = "forbidcustomer", unique = true, nullable = false)
	public String getForbidCustomer() {
		return forbidCustomer;
	}

	public void setForbidCustomer(String forbidCustomer) {
		this.forbidCustomer = forbidCustomer;
	}
	@Column(name = "allowcustomer", unique = true, nullable = false)
	public String getAllowCustomer() {
		return allowCustomer;
	}

	public void setAllowCustomer(String allowCustomer) {
		this.allowCustomer = allowCustomer;
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

	@Column(name = "providerid" ,length=11)
	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	@Column(name = "productname" ,length=50)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "costprice")
	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name = "categoryid" ,length=11)
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	@Column(name = "priority" ,length=2)
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(name = "status" ,length=2)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "mobileoperator" ,length=50)
	public String getMobileOperator() {
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator;
	}


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
	
	@Column(name = "productcode" ,length=1000)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	@Column(name = "validitytime" ,length=50)
	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}

}
