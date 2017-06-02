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
import javax.persistence.Transient;

import org.apache.poi.hpsf.SummaryInformation;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import xinxing.boss.admin.boss.order.domain.OrderInfo;

/**
 * 广东数据分析图;
 * 
 */
@Entity
@Table(name = "boss_gd_data_analyze")
@DynamicInsert
@DynamicUpdate
public class BossGdDataAnalyze extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private String city;// 城市
	private Integer totalNum = 0;// 订单笔数
	private Integer successNum = 0;// 成功笔数
	private BigDecimal totalCost = new BigDecimal(0);// 订单成本
	private BigDecimal successCost = new BigDecimal(0);// 成功成本
	private BigDecimal totalNumRate = new BigDecimal(0);// 订单笔数占比
	private BigDecimal successNumRate = new BigDecimal(0);// 成功笔数占比
	private BigDecimal totalCostRate = new BigDecimal(0);// 订单金额占比
	private BigDecimal successCostRate = new BigDecimal(0);// 成功金额占比
	private Integer dayTotalNum = 0;// 当天订单笔数
	private Integer daySuccessNum = 0;// 当天成功笔数
	private BigDecimal dayTotalCost = new BigDecimal(0);// 当天订单成本
	private BigDecimal daySuccessCost = new BigDecimal(0);// 当天成功成本
	private Integer area;// 使用区域
	private String remark;// 备注
	private Date addTime;

	@Column(name = "daytotalnum")
	public Integer getDayTotalNum() {
		return dayTotalNum;
	}

	public void setDayTotalNum(Integer dayTotalNum) {
		this.dayTotalNum = dayTotalNum;
	}

	@Column(name = "daysuccessnum")
	public Integer getDaySuccessNum() {
		return daySuccessNum;
	}

	public void setDaySuccessNum(Integer daySuccessNum) {
		this.daySuccessNum = daySuccessNum;
	}

	@Column(name = "daytotalcost")
	public BigDecimal getDayTotalCost() {
		return dayTotalCost;
	}

	public void setDayTotalCost(BigDecimal dayTotalCost) {
		this.dayTotalCost = dayTotalCost;
	}

	@Column(name = "daysuccesscost")
	public BigDecimal getDaySuccessCost() {
		return daySuccessCost;
	}

	public void setDaySuccessCost(BigDecimal daySuccessCost) {
		this.daySuccessCost = daySuccessCost;
	}

	@Column(name = "addtime")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "area")
	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
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

	@Column(name = "city", unique = true, nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "totalnum", unique = true, nullable = false)
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Column(name = "totalcost", unique = true, nullable = false)
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	@Column(name = "successnum", unique = true, nullable = false)
	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

	@Column(name = "successcost", unique = true, nullable = false)
	public BigDecimal getSuccessCost() {
		return successCost;
	}

	public void setSuccessCost(BigDecimal successCost) {
		this.successCost = successCost;
	}

	@Transient
	public BigDecimal getTotalNumRate() {
		return totalNumRate;
	}

	public void setTotalNumRate(BigDecimal totalNumRate) {
		this.totalNumRate = totalNumRate;
	}

	@Transient
	public BigDecimal getSuccessNumRate() {
		return successNumRate;
	}

	public void setSuccessNumRate(BigDecimal successNumRate) {
		this.successNumRate = successNumRate;
	}

	@Transient
	public BigDecimal getTotalCostRate() {
		return totalCostRate;
	}

	public void setTotalCostRate(BigDecimal totalCostRate) {
		this.totalCostRate = totalCostRate;
	}

	@Transient
	public BigDecimal getSuccessCostRate() {
		return successCostRate;
	}

	public void setSuccessCostRate(BigDecimal successCostRate) {
		this.successCostRate = successCostRate;
	}

	@Column(name = "remark", unique = true, nullable = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BossGdDataAnalyze() {
	}

	public BossGdDataAnalyze(String city, Integer area, Integer totalNum, BigDecimal totalCost, Integer successNum, BigDecimal successCost,
			BigDecimal totalNumRate, BigDecimal successNumRate, BigDecimal totalCostRate, BigDecimal successCostRate, String remark) {
		this.area = area;
		this.city = city;
		this.totalNum = totalNum;
		this.totalCost = totalCost;
		this.successNum = successNum;
		this.successCost = successCost;
		this.totalNumRate = totalNumRate;
		this.successNumRate = successNumRate;
		this.totalCostRate = totalCostRate;
		this.successCostRate = successCostRate;
		this.remark = remark;
	}

	public void add(Integer status, BigDecimal cost) {
		if (status == OrderInfo.Status.SUCCESS.status) {
			this.successNum++;
			this.successCost = this.successCost.add(cost);
		}
		this.totalNum++;
		this.totalCost = this.totalCost.add(cost);
	}

	public void getRate() {
		if (daySuccessCost.doubleValue() != 0) // BigDecimal.ROUND_HALF_UP
			this.successCostRate = this.successCost.divide(daySuccessCost, 4, BigDecimal.ROUND_HALF_UP);
		if (daySuccessNum != 0)
			this.successNumRate = new BigDecimal(successNum).divide(new BigDecimal(daySuccessNum), 4, BigDecimal.ROUND_HALF_UP);
		if (dayTotalCost.doubleValue() != 0)
			this.totalCostRate = this.totalCost.divide(dayTotalCost, 4, BigDecimal.ROUND_HALF_UP);
		if (dayTotalNum != 0)
			this.totalNumRate = new BigDecimal(totalNum).divide(new BigDecimal(dayTotalNum), 4, BigDecimal.ROUND_HALF_UP);
	}

	public void count(BossGdDataAnalyze bossGdDataAnalyze) {
		this.totalNum += bossGdDataAnalyze.getTotalNum();
		this.successNum += bossGdDataAnalyze.getSuccessNum();
		this.totalCost = this.totalCost.add(bossGdDataAnalyze.getTotalCost());
		this.successCost = this.successCost.add(bossGdDataAnalyze.getSuccessCost());
		
		this.dayTotalNum += bossGdDataAnalyze.getDayTotalNum();
		this.daySuccessNum += bossGdDataAnalyze.getDaySuccessNum();
		this.dayTotalCost = this.dayTotalCost.add(bossGdDataAnalyze.getDayTotalCost());
		this.daySuccessCost = this.daySuccessCost.add(bossGdDataAnalyze.getDaySuccessCost());
	}

	public void createDayData(int dayTotalNum, BigDecimal dayTotalCost, int daySuccessNum, BigDecimal daySuccessCost) {
		this.dayTotalNum = dayTotalNum;
		this.dayTotalCost = dayTotalCost;
		this.daySuccessCost = daySuccessCost;
		this.daySuccessNum = daySuccessNum;
	}
}
