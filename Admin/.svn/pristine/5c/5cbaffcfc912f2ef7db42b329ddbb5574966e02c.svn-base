package xinxing.boss.admin.boss.provider.domain;

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
@Table(name = "boss_provider_data_analyze")
@DynamicInsert
@DynamicUpdate
public class ProviderDataAnalyze {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer providerId;//供应商id
	//成本总额
	private Double totalMoney = 0d;
	//成功成本
	private Double successMoney = 0d;
	private Integer dianxinNum = 0;
	private Integer liantongNum = 0;
	private Integer yidongNum = 0;
	//订单总笔数
	private Integer totalNum = 0;
	//成功笔数
	private Integer successNum = 0;
	//成功率 successNum/totalNum
	private Double successRate = 0d;
	//耗时 总时间/totalNum
	private Long useTime = 0L;
	private Date time;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "providerid", unique = true, nullable = false)
	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	@Column(name = "totalmoney", unique = true, nullable = false)
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "successmoney", unique = true, nullable = false)
	public Double getSuccessMoney() {
		return successMoney;
	}

	public void setSuccessMoney(Double successMoney) {
		this.successMoney = successMoney;
	}

	@Column(name = "dianxinnum", unique = true, nullable = false)
	public Integer getDianxinNum() {
		return dianxinNum;
	}

	public void setDianxinNum(Integer dianxinNum) {
		this.dianxinNum = dianxinNum;
	}

	@Column(name = "liantongnum", unique = true, nullable = false)
	public Integer getLiantongNum() {
		return liantongNum;
	}

	public void setLiantongNum(Integer liantongNum) {
		this.liantongNum = liantongNum;
	}

	@Column(name = "yidongnum", unique = true, nullable = false)
	public Integer getYidongNum() {
		return yidongNum;
	}

	public void setYidongNum(Integer yidongNum) {
		this.yidongNum = yidongNum;
	}

	@Column(name = "totalnum", unique = true, nullable = false)
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Column(name = "successnum", unique = true, nullable = false)
	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

	@Column(name = "successrate", unique = true, nullable = false)
	public Double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
	}

	@Column(name = "usetime", unique = true, nullable = false)
	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	@Column(name = "time", unique = true, nullable = false)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	//保留小数点2位
	public ProviderDataAnalyze format2decimalPoint() {
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.0000");
		this.totalMoney = new Double(df.format(totalMoney).toString());
		this.successMoney = new Double(df.format(successMoney).toString());
		this.successRate = new Double(df2.format(successRate).toString());
		return this;
	}

	public ProviderDataAnalyze addPda(ProviderDataAnalyze pda) {
		this.totalMoney = this.totalMoney + pda.getTotalMoney();
		this.successMoney = this.successMoney + pda.getSuccessMoney();
		this.dianxinNum = this.dianxinNum + pda.getDianxinNum();
		this.liantongNum = this.liantongNum + pda.getLiantongNum();
		this.yidongNum = this.yidongNum + pda.getYidongNum();
		this.totalNum = this.totalNum + pda.getTotalNum();
		this.successNum = this.successNum + pda.getSuccessNum();
		this.useTime = this.useTime + pda.getUseTime();
		return this;
	}

}
