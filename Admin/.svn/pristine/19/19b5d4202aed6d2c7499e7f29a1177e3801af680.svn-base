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
*
*
*/
@Entity
@Table(name = "boss_charge_alarm")
@DynamicInsert
@DynamicUpdate
public class BossChargeAlarm extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer productId;// 产品id
	private String operator;// 运营商
	private String province;// 省份
	private Integer area;// 使用区域
	private Integer chargeNum;// 充值中笔数
	private Integer useTime;// 耗时(分)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "productid", unique = true, nullable = false)
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "operator", unique = true, nullable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "province", unique = true, nullable = false)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "area", unique = true, nullable = false)
	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	@Column(name = "chargenum", unique = true, nullable = false)
	public Integer getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(Integer chargeNum) {
		this.chargeNum = chargeNum;
	}

	@Column(name = "usetime", unique = true, nullable = false)
	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	public BossChargeAlarm() {
	}

	public void addAndUpdateTimeAndUserId() {
		addOrUpdate(this, true);
	}

	public void updateTimeAndUserId(BossChargeAlarm BossChargeAlarm) {
		addOrUpdate(BossChargeAlarm, false);
	}

	private void addOrUpdate(BossChargeAlarm BossChargeAlarm, Boolean isAdd) {
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
	}

	public BossChargeAlarm(Integer productId, String operator, String province, Integer area, Integer chargeNum, Integer useTime) {
		this.productId = productId;
		this.operator = operator;
		this.province = province;
		this.area = area;
		this.chargeNum = chargeNum;
		this.useTime = useTime;
	}
}
