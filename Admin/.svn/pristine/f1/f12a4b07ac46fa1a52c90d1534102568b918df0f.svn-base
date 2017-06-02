package xinxing.boss.admin.boss.other.domain;

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
*地市开关失败配置表
*
*/
@Entity
@Table(name = "boss_operator_close_config")
@DynamicInsert
@DynamicUpdate
public class BossOperatorCloseConfig extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer customerId;// 采购商id
	private String province;// 省份
	private String city;// 城市
	private Integer size;// 流量规格
	private Integer status;// 状态0冻结1正常
	private Integer failStatus;// 失败状态1直接失败2缓存
	private Integer area;// 失败状态1直接失败2缓存

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "customerid", unique = true, nullable = false)
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "province", unique = true, nullable = false)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", unique = true, nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "size", unique = true, nullable = false)
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Column(name = "status", unique = true, nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "failstatus", unique = true, nullable = false)
	public Integer getFailStatus() {
		return failStatus;
	}

	public void setFailStatus(Integer failStatus) {
		this.failStatus = failStatus;
	}
	
	@Column(name = "area", unique = true, nullable = false)
	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public BossOperatorCloseConfig() {
	}

	//
	// public void addAndUpdateTimeAndUserId() {
	// addOrUpdate(this, true);
	// }
	//
	// public void updateTimeAndUserId(BossOperatorCloseConfig BossOperatorCloseConfig) {
	// addOrUpdate(BossOperatorCloseConfig, false);
	// }
	//
	// private void addOrUpdate(BossOperatorCloseConfig BossOperatorCloseConfig, Boolean isAdd) {
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

	public BossOperatorCloseConfig(Integer customerId, String province, String city, Integer size, Integer status, Integer failStatus, Integer area) {
		this.customerId = customerId;
		this.province = province;
		this.city = city;
		this.size = size;
		this.status = status;
		this.failStatus = failStatus;
		this.area = area;
	}
}
