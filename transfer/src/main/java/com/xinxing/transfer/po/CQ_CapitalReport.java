package com.xinxing.transfer.po;
/**
 * CQ的资金报账流水实体
 * @author Administrator
 *
 */
public class CQ_CapitalReport {
	String operator;// 运营商
	String province;// 省份
	String denomination;// 面值
	String standardPrice;// 基准价
	String downSerialNumber;// 发货流水号
	String upSerialNumber;// 上游流水号
	String rechargePhone;// 充值号码
	String type;// 交易类型
	String ChangesMoney;// 变动金额
	String balance;// 账户余额
	String balanceChangesDate;// 资金变动时间
	String serviceType;// 业务类型
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	public String getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	public String getDownSerialNumber() {
		return downSerialNumber;
	}
	public void setDownSerialNumber(String downSerialNumber) {
		this.downSerialNumber = downSerialNumber;
	}
	public String getUpSerialNumber() {
		return upSerialNumber;
	}
	public void setUpSerialNumber(String upSerialNumber) {
		this.upSerialNumber = upSerialNumber;
	}
	public String getRechargePhone() {
		return rechargePhone;
	}
	public void setRechargePhone(String rechargePhone) {
		this.rechargePhone = rechargePhone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getChangesMoney() {
		return ChangesMoney;
	}
	public void setChangesMoney(String changesMoney) {
		ChangesMoney = changesMoney;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBalanceChangesDate() {
		return balanceChangesDate;
	}
	public void setBalanceChangesDate(String balanceChangesDate) {
		this.balanceChangesDate = balanceChangesDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	@Override
	public String toString() {
		return "CQ_CapitalReport [operator=" + operator + ", province=" + province + ", denomination=" + denomination
				+ ", standardPrice=" + standardPrice + ", downSerialNumber=" + downSerialNumber + ", upSerialNumber="
				+ upSerialNumber + ", rechargePhone=" + rechargePhone + ", type=" + type + ", ChangesMoney="
				+ ChangesMoney + ", balance=" + balance + ", balanceChangesDate=" + balanceChangesDate
				+ ", serviceType=" + serviceType + "]";
	}
	
	
	
	
}
