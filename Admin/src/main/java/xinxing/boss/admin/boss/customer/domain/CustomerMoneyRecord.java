package xinxing.boss.admin.boss.customer.domain;

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
/**
 * 采购商注资收支记录
 * @author lgy
 *
 */
@Entity
@Table(name="boss_customer_balance_record")
@DynamicInsert @DynamicUpdate
public class CustomerMoneyRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer customerId; //采购商id
	private BigDecimal fundBalance; //资金余额
	private Integer costType; //收支类型 1人工注资2系统注资 3消费4失败返还5人工扣款
	private String remark; //备注
	private BigDecimal cost;//成本
	private Date recordTime;//时间
	private String orderKey;//
	private String phone;//手机号
	private Double price;//价格
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique=true,nullable=false) 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="customerid", length=11) 
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	@Column(name="orderkey") 
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
