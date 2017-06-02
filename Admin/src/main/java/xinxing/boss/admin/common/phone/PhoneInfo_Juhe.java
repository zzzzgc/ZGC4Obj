package xinxing.boss.admin.common.phone;

/**
 * 聚合数据手机归属地
 * @author Administrator
 * https://www.juhe.cn/docs/api/id/11
 */
public class PhoneInfo_Juhe {

	private String resultcode;
	private String reason;
	private PhoneInfo_Result_Juhe result;
	
	public PhoneInfo_Juhe() {
	}
	
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public PhoneInfo_Result_Juhe getResult() {
		return result;
	}
	public void setResult(PhoneInfo_Result_Juhe result) {
		this.result = result;
	}
	
	
	
}
