package xinxing.boss.admin.common.phone;

import org.apache.commons.lang.StringUtils;

public class PhoneInfo_IP138 {
	private String province;
	private String carname;
	private String code;
	private String city;
	
	public PhoneInfo_IP138() {
	}
	
	
	public PhoneInfo_IP138(String province,String city, String carname, String code) {
		this.province = province;
		
		if(StringUtils.contains(carname,"移动")){
			this.carname = "移动";
		}
		if(StringUtils.contains(carname,"联通")){
			this.carname = "联通";
		}
		if(StringUtils.contains(carname,"电信")){
			this.carname = "电信";
		}
		this.code = code;
		if(StringUtils.endsWith(city, "市")){
			this.city = StringUtils.substringBefore(city, "市");
		}else{
			this.city = city;
		}
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCarname() {
		return carname;
	}
	public void setCarname(String carname) {
		this.carname = carname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
