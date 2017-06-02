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
/**
*
*
*/
@Entity
@Table(name = "boss_discount_change")
@DynamicInsert
@DynamicUpdate
public class DiscountChange extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;//编号
	private String name;//采购商或者供货商名称
	private String remark;//备注
	private Date addTime;//添加时间
	private Integer addUserId;//添加用户id
	private Integer productId;//产品id
	private BigDecimal beforeDiscount;//原折扣
	private BigDecimal afterDiscount;//现折扣
	private String addUserName;//添加用户
	private Integer type;//类型0采购商1供应商

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "adduserid", unique = true, nullable = false)
	public Integer getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Integer addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "productid", unique = true, nullable = false)
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "beforediscount", unique = true, nullable = false)
	public BigDecimal getBeforeDiscount() {
		return beforeDiscount;
	}

	public void setBeforeDiscount(BigDecimal beforeDiscount) {
		this.beforeDiscount = beforeDiscount;
	}

	@Column(name = "afterdiscount", unique = true, nullable = false)
	public BigDecimal getAfterDiscount() {
		return afterDiscount;
	}

	public void setAfterDiscount(BigDecimal afterDiscount) {
		this.afterDiscount = afterDiscount;
	}

	@Column(name = "addusername", unique = true, nullable = false)
	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	@Column(name = "type", unique = true, nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public DiscountChange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountChange(String name, String remark, Date addTime, Integer addUserId,
			Integer productId, BigDecimal beforeDiscount, BigDecimal afterDiscount,
			String addUserName, Integer type) {
		this.name = name;
		this.remark = remark;
		this.addTime = addTime;
		this.addUserId = addUserId;
		this.productId = productId;
		this.beforeDiscount = beforeDiscount;
		this.afterDiscount = afterDiscount;
		this.addUserName = addUserName;
		this.type = type;
	}
	
}
