package xinxing.boss.admin.common.phone;

public class PhoneInfo_Result_Juhe {

	private String province;
	private String city;
	private String areacode;
	private String zip;
	private String company;
	private String card;
	
	public PhoneInfo_Result_Juhe() {
		
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company.replace("中国", "");
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	
}
