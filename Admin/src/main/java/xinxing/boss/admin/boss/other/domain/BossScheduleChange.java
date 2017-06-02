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

import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

/**
*
*
*/
@Entity
@Table(name = "boss_schedule_change")
@DynamicInsert
@DynamicUpdate
public class BossScheduleChange extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer tableType;// 对应表1采购商2供应商3采购商产品4供应商产品
	private Integer tableId;// 表id
	private Integer type;// 修改类型1开通2冻结3更改折扣
	private Integer status;// 是否生效0否1是
	private Date changeTime;// 修改时间
	private BigDecimal changeNum;// 折扣数
	private Date addTime;//
	private Integer addUser;//
	private String remark;//
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
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

	@Column(name = "tabletype", unique = true, nullable = false)
	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	@Column(name = "tableid", unique = true, nullable = false)
	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	@Column(name = "type", unique = true, nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "status", unique = true, nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "changetime", unique = true, nullable = false)
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	@Column(name = "changenum", unique = true, nullable = false)
	public BigDecimal getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(BigDecimal changeNum) {
		this.changeNum = changeNum;
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

	public BossScheduleChange() {
	}

	public void addAndUpdateTimeAndUserId() {
		addOrUpdate(this, true);
	}

	public void updateTimeAndUserId() {
		addOrUpdate(this, false);
	}

	private void addOrUpdate(BossScheduleChange bossScheduleChange, Boolean isAdd) {
		User user = UserUtil.getCurrentUser();
		if (user == null) {
			throw new RuntimeException("用户未登录");
		}
		Date date = new Date();
		// this.updateTime = date;
		// this.updateUser = user.getId();
		if (isAdd) {
			this.addTime = date;
			this.addUser = user.getId();
		}
	}

	public BossScheduleChange(Integer tableType, Integer tableId, Integer type, Integer status, Date changeTime, BigDecimal changeNum, Date addTime,
			Integer addUser) {
		this.tableType = tableType;
		this.tableId = tableId;
		this.type = type;
		this.status = status;
		this.changeTime = changeTime;
		this.changeNum = changeNum;
		this.addTime = addTime;
		this.addUser = addUser;
	}
}
