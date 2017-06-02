package xinxing.boss.admin.boss.providerBalance.domain;

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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 注资审核
 * @author 
 *
 */
@Entity
@Table(name = "boss_provider_balance_audit")
@DynamicInsert
@DynamicUpdate
public class ProviderBalanceAudit {
	
	private Integer id;
	private Integer providerId;
	private String providerName;
	private BigDecimal balance;
	private Integer type;
	private Date addTime;
	private String addUser;
	private Integer status;
	private String auditUser;
	private Date auditTime;
	private String remark;
	private String tickUrl;
	private String  reviewName; //添加复核人
	private Date reviewTime; //添加复核时间
	private Integer reviewStatus; //添加复核状态
	

	public ProviderBalanceAudit() {
	}
	
	/**
	 * 增加注资审核
	 * @param customerId
	 * @param balance
	 * @param type
	 * @param addTime
	 * @param addUser
	 * @param remark
	 */
	public ProviderBalanceAudit(Integer providerId, BigDecimal balance,String addUser, String remark,String tickUrl) {
		this.providerId = providerId;
		this.balance = balance;
		this.type = 1;
		this.addTime = new Date();
		this.addUser = addUser;
		this.remark = remark;
		this.tickUrl = tickUrl;
	}
	
	/**
	 * 审核注资记录
	 * @param status
	 * @param auditUser
	 * @param auditTime
	 * @param remark
	 */
	public ProviderBalanceAudit(Integer id,Integer status, String auditUser, Date auditTime,
			String remark) {
		this.id = id;
		this.status = status;
		this.auditUser = auditUser;
		this.auditTime = auditTime;
		this.remark = remark;
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
	
	@Column(name = "balance", length=50)
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Column(name = "`type`", length=50)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "addtime", length=50)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@Column(name = "adduser", length=50)
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	
	@Column(name = "status", length=50,columnDefinition="INT default 0")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "audituser", length=50)
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	
	@Column(name = "audittime", length=50)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name = "remark", length=50)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "tickurl", length=50)
	public String getTickUrl() {
		return tickUrl;
	}
	public void setTickUrl(String tickUrl) {
		this.tickUrl = tickUrl;
	}

	@Column(name = "providerid", length=50)
	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	@Transient
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	@Column(name = "reviewname", length=50)
	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}
	@Column(name = "reviewtime", length=50)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	@Column(name = "reviewstatus", length=2)
	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	
	
}
