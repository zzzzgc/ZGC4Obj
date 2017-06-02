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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 充值信息流水记录表;
 * 
 */
@Entity
@Table(name = "boss_provider_charge")
@DynamicInsert
@DynamicUpdate
public class BossProviderCharge extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer orderId;// 订单id，订单信息表id
	private Integer productId;// 充值的产品id供货商产品信息id   providerCategoryId
	private Integer providerId;// 供应商id
	private String phone;// 充值的手机号码
	private BigDecimal cost;// 充值的成本
	private Integer categoryId;// 要充值的产品id产品类别信息id
	private Date callTime;// 回调时间    receiveTime
	private Integer status;// 状态  状态1新增2充值中3充值成功4充值失败5等待确认6需要手工处理7平账审核
	private String orderCode;// 供货商返回的订单号 无.
	private String failReason;// 失败原因   
	private String poolNum;// 流量池编号
	private Integer sizeValue;// 流量池大小
	
	private Integer orderStatus;
	private String orderReason;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orderid", unique = true, nullable = false)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "phone", unique = true, nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "productid", unique = true, nullable = false)
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "cost", unique = true, nullable = false)
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name = "categoryid", unique = true, nullable = false)
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "calltime", unique = true, nullable = false)
	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	@Column(name = "status", unique = true, nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "ordercode", unique = true, nullable = false)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "failreason", unique = true, nullable = false)
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Column(name = "providerid", unique = true, nullable = false)
	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	@Column(name = "poolnum", unique = true, nullable = false)
	public String getPoolNum() {
		return poolNum;
	}

	public void setPoolNum(String poolNum) {
		this.poolNum = poolNum;
	}

	@Column(name = "sizevalue", unique = true, nullable = false)
	public Integer getSizeValue() {
		return sizeValue;
	}

	public void setSizeValue(Integer sizeValue) {
		this.sizeValue = sizeValue;
	}
	
	@Transient
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Transient
	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public BossProviderCharge() {
	}


	public BossProviderCharge(Integer orderId, String phone, Integer productId, BigDecimal cost, Integer categoryId, Date callTime, Integer status,
			String orderCode, String failReason, Integer providerId, String poolNum, Integer sizeValue) {
		this.orderId = orderId;
		this.phone = phone;
		this.productId = productId;
		this.cost = cost;
		this.categoryId = categoryId;
		this.callTime = callTime;
		this.status = status;
		this.orderCode = orderCode;
		this.failReason = failReason;
		this.providerId = providerId;
		this.poolNum = poolNum;
		this.sizeValue = sizeValue;
	}
}
