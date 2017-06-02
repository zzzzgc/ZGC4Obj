package xinxing.boss.admin.boss.other.domain;

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

import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;


/**
*
*
*/
@Entity
@Table(name = "boss_invoice_audit")
@DynamicInsert
@DynamicUpdate
public class BossInvoiceAudit extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer orderId;// 订单id
	private Integer auditStatus;// 审核状态0未审核1审核通过2审核不通过
	private Date auditTime;// 审核时间
	private Integer auditUser;// 审核人
	private String remark;// 备注
	private Date addTime;// 添加时间
	private Integer addUser;// 添加用户
	private Date updateTime;// 更新时间
	private Integer updateUser;// 更新用户

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

	@Column(name = "auditstatus", unique = true, nullable = false)
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "audittime", unique = true, nullable = false)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "audituser", unique = true, nullable = false)
	public Integer getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Integer auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "remark", unique = true, nullable = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "addtime", unique = true, nullable = false)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "adduser", unique = true, nullable = false)
	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	@Column(name = "updatetime", unique = true, nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "updateuser", unique = true, nullable = false)
	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public BossInvoiceAudit() {
	}

	public void addAndUpdateTimeAndUserId() {
		addOrUpdate(this, true);
	}

	public void updateTimeAndUserId(BossInvoiceAudit BossInvoiceAudit) {
		addOrUpdate(BossInvoiceAudit, false);
	}

	private void addOrUpdate(BossInvoiceAudit BossInvoiceAudit, Boolean isAdd) {
		User user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new RuntimeException("用户未登录");
		}
		Date date = new Date();
		this.updateTime = date;
		this.updateUser = user.getId();
		if (isAdd) {
			this.addTime = date;
			this.addUser = user.getId();
		}
	}
}
