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

@Entity
@Table(name = "boss_alarm_config")
@DynamicInsert
@DynamicUpdate
public class AlarmConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer providerId;
	private String operator;
	private String province;
	private Long timeOut;
	/*
	 * 最小未处理订单数
	 */
	private Integer minNumOfNotHandle;
	/*
	 * 最大未处理订单数 如果为null代表无穷大
	 */
	private Integer maxNumOfNotHandle;
	/*
	 * 0 一般 1 严重 2 特别严重,
	 */
	private Integer grade;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "providerid")
	public Integer getProviderId() {
		return providerId;
	}

	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "timeout")
	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	@Column(name = "minnumofnothandle")
	public Integer getMinNumOfNotHandle() {
		return minNumOfNotHandle;
	}

	public void setMinNumOfNotHandle(Integer minNumOfNotHandle) {
		this.minNumOfNotHandle = minNumOfNotHandle;
	}
	@Column(name = "maxnumofnothandle")
	public Integer getMaxNumOfNotHandle() {
		return maxNumOfNotHandle;
	}

	public void setMaxNumOfNotHandle(Integer maxNumOfNotHandle) {
		this.maxNumOfNotHandle = maxNumOfNotHandle;
	}

	@Column(name = "grade")
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

}
