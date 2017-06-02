package xinxing.boss.admin.boss.customer.domain;

import java.text.DecimalFormat;
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
@Table(name = "boss_customer_data_analyze")
@DynamicInsert
@DynamicUpdate
public class CustomerDataAnalyze {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer customerId;
	//销售总额
	private Double totalMoney = 0d;
	//成功销售额
	private Double successMoney = 0d;
	//成本
	private Double successCost = 0d;
	//利润 successMoney-successCost
	private Double earn = 0d;
	private Integer dianxinNum = 0;
	private Integer liantongNum = 0;
	private Integer yidongNum = 0;
	//订单总笔数
	private Integer totalNum = 0;
	private Integer successNum = 0;
	//成功率 successNum/totalNum
	private Double successRate = 0d;
	//销售毛利率 earn/successMoney
	private Double maoEarn = 0d;
	//耗时 总时间/totalNum
	private Long useTime = 0L;
	private Date time ;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public CustomerDataAnalyze addPda(CustomerDataAnalyze pda) {
		this.totalMoney = this.totalMoney + pda.getTotalMoney();
		this.successMoney = this.successMoney + pda.getSuccessMoney();
		this.successCost = this.successCost + pda.getSuccessCost();
		this.earn = this.successMoney - this.successCost;
		this.dianxinNum = this.dianxinNum + pda.getDianxinNum();
		this.liantongNum = this.liantongNum + pda.getLiantongNum();
		this.yidongNum = this.yidongNum + pda.getYidongNum();
		this.totalNum = this.totalNum + pda.getTotalNum();
		this.successNum = this.successNum + pda.getSuccessNum();
		this.useTime = this.useTime + pda.getUseTime();
		return this;
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

	

	@Column(name = "customerid")
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "totalmoney")
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "successmoney")
	public Double getSuccessMoney() {
		return successMoney;
	}

	public void setSuccessMoney(Double successMoney) {
		this.successMoney = successMoney;
	}

	@Column(name = "successcost")
	public Double getSuccessCost() {
		return successCost;
	}

	public void setSuccessCost(Double successCost) {
		this.successCost = successCost;
	}

	@Column(name = "earn")
	public Double getEarn() {
		return earn;
	}

	public void setEarn(Double earn) {
		this.earn = earn;
	}

	@Column(name = "dianxinnum")
	public Integer getDianxinNum() {
		return dianxinNum;
	}

	public void setDianxinNum(Integer dianxinNum) {
		this.dianxinNum = dianxinNum;
	}

	@Column(name = "liantongnum")
	public Integer getLiantongNum() {
		return liantongNum;
	}

	public void setLiantongNum(Integer liantongNum) {
		this.liantongNum = liantongNum;
	}

	@Column(name = "yidongnum")
	public Integer getYidongNum() {
		return yidongNum;
	}

	public void setYidongNum(Integer yidongNum) {
		this.yidongNum = yidongNum;
	}

	@Column(name = "totalnum")
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Column(name = "successnum")
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	@Column(name = "successrate")
	public Double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
	}
	@Column(name = "maoearn")
	public Double getMaoEarn() {
		return maoEarn;
	}

	public void setMaoEarn(Double maoEarn) {
		this.maoEarn = maoEarn;
	}

	@Column(name = "usetime")
	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	//double保留小数点2位
	public CustomerDataAnalyze format2decimalPoint() {
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.0000");
		this.totalMoney = new Double(df.format(totalMoney).toString());
		this.successMoney = new Double(df.format(successMoney).toString());
		this.successCost = new Double(df.format(successCost).toString());
		this.earn = new Double(df.format(earn).toString());
		this.maoEarn = new Double(df2.format(maoEarn).toString());
		this.successRate = new Double(df2.format(successRate).toString());
		return this;
	}
}
