package xinxing.boss.admin.boss.provider.domain;

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

@Entity
@Table(name="boss_provider_balance_record")
@DynamicInsert @DynamicUpdate
public class ProviderMoneyRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer providerId; 
	private BigDecimal fundBalance; //余额
	private Integer costType; //1人工注资2系统注资3消费4失败返还5人工扣款
	private String remark; 
	private BigDecimal cost;//发生金额
	private Date recordTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique=true,nullable=false) 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="providerid", length=11) 
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	@Column(name="fundbalance") 
	public BigDecimal getFundBalance() {
		return fundBalance;
	}
	public void setFundBalance(BigDecimal fundBalance) {
		this.fundBalance = fundBalance;
	}
	@Column(name="costtype", length=3) 
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	@Column(name="remark", length=500) 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="cost") 
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	@Column(name="recordtime") 
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
	
}
