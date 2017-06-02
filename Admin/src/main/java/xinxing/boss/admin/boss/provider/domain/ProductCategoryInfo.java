package xinxing.boss.admin.boss.provider.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "boss_product_category")
@DynamicInsert @DynamicUpdate
public class ProductCategoryInfo {
	private int id;
	private Integer pid;
	private String categoryName;
	private Integer status; //1 使用中 2已下架
	private Integer productNum;
	private Integer productUnit;//1M2G3元
	private Integer productType;//1流量包2话费
	private Integer isSecondChannel;
	private String operator;//运营商,通用，移动，联通，电信
	private String province; //所属省份
	private Integer area; //0 本省、1 全国
	private Double standarPrice;//标准价
	private Integer isLimit;//是否限价
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	@Column(name = "islimit")
	public Integer getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Integer isLimit) {
		this.isLimit = isLimit;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "pid", length=4)
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "categoryname", length=50)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "status", length=2)
	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "productnum", length=10)
	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	@Column(name = "productunit", length=3)
	public Integer getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(Integer productUnit) {
		this.productUnit = productUnit;
	}

	@Column(name = "producttype", length=3)
	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	@Column(name = "issecondchannel", length=2)
	public Integer getIsSecondChannel() {
		return isSecondChannel;
	}

	public void setIsSecondChannel(Integer isSecondChannel) {
		this.isSecondChannel = isSecondChannel;
	}

	@Column(name = "operator", length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "province", length=20)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "area", length=2)
	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	@Column(name = "standarprice")
	public Double getStandarPrice() {
		return standarPrice;
	}

	public void setStandarPrice(Double standarPrice) {
		this.standarPrice = standarPrice;
	}

	public ProductCategoryInfo(Integer pid, String categoryName, Integer status, Integer productNum, Integer productUnit, Integer productType,
			Integer isSecondChannel, String operator, String province, Integer area, Double standarPrice) {
		this.pid = pid;
		this.categoryName = categoryName;
		this.status = status;
		this.productNum = productNum;
		this.productUnit = productUnit;
		this.productType = productType;
		this.isSecondChannel = isSecondChannel;
		this.operator = operator;
		this.province = province;
		this.area = area;
		this.standarPrice = standarPrice;
	}

	public ProductCategoryInfo() {
	}
	
}
