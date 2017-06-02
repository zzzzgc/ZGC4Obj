package xinxing.boss.admin.boss.other.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
*
*
*/
@Entity
@Table(name = "boss_provider_same")
@DynamicInsert
@DynamicUpdate
public class BossProviderSame extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private String providerIds;// 供应商id
	private BigDecimal alarmBalance;// 报警金额
	private String supplier;//简称,如GDYD
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	@Column(name = "providerids", unique = true, nullable = false)
	public String getProviderIds() {
		return providerIds;
	}

	public void setProviderIds(String providerIds) {
		this.providerIds = providerIds;
	}

	@Column(name = "alarmbalance", unique = true, nullable = false)
	public BigDecimal getAlarmBalance() {
		return alarmBalance;
	}

	public void setAlarmBalance(BigDecimal alarmBalance) {
		this.alarmBalance = alarmBalance;
	}

	public BossProviderSame() {
	}

	// public void addAndUpdateTimeAndUserId() {
	// addOrUpdate(this, true);
	// }
	//
	// public void updateTimeAndUserId() {
	// addOrUpdate(this, false);
	// }
	//
	// private void addOrUpdate(BossProviderSame bossProviderSame, Boolean isAdd) {
	// User user = UserUtil.getCurrentUser();
	// if (user == null) {
	// throw new RuntimeException("用户未登录");
	// }
	// Date date = new Date();
	// this.updateTime = date;
	// this.updateUser = user.getId();
	// if (isAdd) {
	// this.addTime = date;
	// this.addUser = user.getId();
	// }
	// }

	public BossProviderSame(String providerIds, BigDecimal alarmBalance) {
		this.providerIds = providerIds;
		this.alarmBalance = alarmBalance;
	}
	public BossProviderSame(String providerIds, BigDecimal alarmBalance,String supplier) {
		this.providerIds = providerIds;
		this.alarmBalance = alarmBalance;
		this.supplier = supplier;
	}
}
