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
@Table(name = "boss_fail_reason_config")
@DynamicInsert @DynamicUpdate
public class FailReason implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String FlowPoolInfoKey;
	private String operator;
	private String remark;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "failreasonkey" ,length=30)
	public String getFailReasonKey() {
		return FlowPoolInfoKey;
	}

	public void setFailReasonKey(String FlowPoolInfoKey) {
		this.FlowPoolInfoKey = FlowPoolInfoKey;
	}
	@Column(name = "operator" ,length=10)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "remark" ,length=30)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
