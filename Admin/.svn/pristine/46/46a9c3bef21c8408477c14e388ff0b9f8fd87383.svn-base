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

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 采购商产品信息表
 * @author lgy
 *
 */
@Entity
@Table(name="boss_customer_product")
@DynamicInsert @DynamicUpdate
public class CustomerProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer customerId; 
	private Integer categoryId; 
	private String productName; 
	private BigDecimal sellPrice; 
	private Integer status; 
	private String mobileOperator; 
	
	public CustomerProductInfo(CustomerProductInfo customerProductInfo) {
		super();
		this.id = customerProductInfo.getId();
		this.customerId = customerProductInfo.getCustomerId();
		this.categoryId = customerProductInfo.getCategoryId();
		this.productName = customerProductInfo.getProductName();
		this.sellPrice = customerProductInfo.getSellPrice();
		this.status = customerProductInfo.getStatus();
		this.mobileOperator = customerProductInfo.getMobileOperator();
	}

	
	public CustomerProductInfo() {
		super();
	}


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
	@Column(name="categoryid", length=11) 
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name="productname", length=100) 
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(name="sellprice") 
	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	@Column(name="status", length=2) 
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="mobileoperator", length=50) 
	public String getMobileOperator() {
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator;
	}


	
	
	
	
}
