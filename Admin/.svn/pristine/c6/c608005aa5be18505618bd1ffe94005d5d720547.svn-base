package xinxing.boss.admin.boss.other.domain;

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

import xinxing.boss.admin.common.utils.filter.HtmlFilter;

/**
 * 订单信息表
 * @author lgy
 *
 */
@Entity
@Table(name="boss_order_resend_record")
@DynamicInsert @DynamicUpdate
public class BossOrderResendRecord implements Serializable {
	public static Integer STATUS_NEW = 1;
	public static Integer STATUS_CHAEGE = 2;
	public static Integer STATUS_SUCCESS = 3;
	public static Integer STATUS_FAIL = 4;
	public static Integer STATUS_WAIT = 5;
	public static Integer STATUS_HANDLE = 6;
	public static Integer STATUS_AUDIT = 7;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;          
	private Integer customerId;//采购商id，采购商信息记录表id
	private String orderKey;//采购商订单号,要求对方系统唯一
	private String phone;//要充值的手机号码
	private Integer categoryId;//要充值的产品id,产品类别信息id
	private Date submitTime;//提交时间到供货商的时间
	private Integer status;//状态1新增2充值中3充值成功4充值失败5等待确认6需要手工处理7平账审核
	private String failReason;//失败原因
	private Date failTime;//确认失败时间
	private Date successTime;//确认成功时间
	private BigDecimal price;//单价
	private Integer successId;//充值成功的供应商id
	private Integer callbackStatus;//
	private String province;//号码省份
	private String city;//号码城市
	private Integer isSecondChannel;//是否启用二次通道，0否1启用
	private Date receiveTime;//接收时间
	private Date callbackTime;//回调时间
	private String beyoudOperation;//号码所属运营商
	private String providerKey;//供应商流水号
	private Integer chargeCount;//充值次数
	private Integer priority;//订单优先级
	private BigDecimal cost;//成本
	private Integer isBlack; //
	private Integer providerCategoryId;// 供应商产品id
	private Integer handleStatus;//1.失败转手工 2.人工转手工 3.供应商回应异常转手工 4.无订单手工 5.超时手工 6.运营商地市关闭转手工 7.未完成订单转手工
	private BigDecimal weiXinPrice; //微信端的售价
	private Integer finalStatus;//最终的结果
	private Integer orderId;
	@Column(name="orderid")
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Column(name="finalstatus")
	public Integer getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(Integer finalStatus) {
		this.finalStatus = finalStatus;
	}
	@Column(name="handlestatus")
	public Integer getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique=true,nullable=false) 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="customerid")
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	@Column(name="orderkey")
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="categoryid")
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name="submittime")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="failreason")
	public String getFailReason() {
		String result = HtmlFilter.filterHtml(failReason);
		return result;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	@Column(name="failtime")
	public Date getFailTime() {
		return failTime;
	}

	public void setFailTime(Date failTime) {
		this.failTime = failTime;
	}
	@Column(name="successtime")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	@Column(name="price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Column(name="providerid")
	public Integer getSuccessId() {
		return successId;
	}

	public void setSuccessId(Integer successId) {
		this.successId = successId;
	}
	@Column(name="callbackstatus")
	public Integer getCallbackStatus() {
		return callbackStatus;
	}

	public void setCallbackStatus(Integer callbackStatus) {
		this.callbackStatus = callbackStatus;
	}
	@Column(name="province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="issecondchannel")
	public Integer getIsSecondChannel() {
		return isSecondChannel;
	}

	public void setIsSecondChannel(Integer isSecondChannel) {
		this.isSecondChannel = isSecondChannel;
	}
	@Column(name="receivetime")
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	@Column(name="callbacktime")
	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}
	@Column(name="operator")
	public String getBeyoudOperation() {
		return beyoudOperation;
	}

	public void setBeyoudOperation(String beyoudOperation) {
		this.beyoudOperation = beyoudOperation;
	}
	@Column(name="providerkey")
	public String getProviderKey() {
		return providerKey;
	}

	public void setProviderKey(String providerKey) {
		this.providerKey = providerKey;
	}
	@Column(name="chargecount",length=2)
	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}
	@Column(name="priority",length=2)
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	@Column(name="cost")
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name="isblack")
	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}
	@Column(name="providercategoryid")
	public Integer getProviderCategoryId() {
		return providerCategoryId;
	}
	public void setProviderCategoryId(Integer providerCategoryId) {
		this.providerCategoryId = providerCategoryId;
	}
	@Column(name="weixinprice")
	public BigDecimal getWeiXinPrice() {
		return weiXinPrice;
	}
	public void setWeiXinPrice(BigDecimal weiXinPrice) {
		this.weiXinPrice = weiXinPrice;
	}

	
	
}
