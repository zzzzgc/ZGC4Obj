package xinxing.boss.admin.boss.other.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "transfer_ordre")
@DynamicInsert @DynamicUpdate
public class TransferOrder extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String downId; //上游订单号

    private String supplierId;  //下游订单号

    private String phone;	//	手机号码

    private Integer status; //	状态 0异常 1成功 2失败 3等待

    private String callbackAddress;  //	回调地址
    			   
    private String callbackData;  // 回调的数据

    private String callbackTime;  // 回调结束时间

    private String startTime;  //开始充值时间

    private String endTime;  //充值结束时间

    private String typeFlow;  //  按照 流量值(M),流量范围,流量漫游类型,运营商 来写 例如: 100,广州,0,YD 
    						  //流量值 按M算 范围 全国为全国 省份写省份 流量漫游类型 0省内 1全国 运营商 YD LT DX
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "downid" ,length=32)
    public String getDownId() {
        return downId;
    }

    public void setDownId(String downId) {
        this.downId = downId == null ? null : downId.trim();
    }
    @Column(name = "supplierid" ,length=32)
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }
    @Column(name = "phone" ,length=11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    
    @Column(name = "status" ,length=2)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name = "callbackaddress" ,length=234)
    public String getCallbackAddress() {
        return callbackAddress;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.callbackAddress = callbackAddress == null ? null : callbackAddress.trim();
    }
    
    @Column(name = "callbackdata" ,length=500)
    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData == null ? null : callbackData.trim();
    }
    
    @Column(name = "callbacktime" ,length=20)
    public String getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(String callbackTime) {
        this.callbackTime = callbackTime == null ? null : callbackTime.trim();
    }

    @Column(name = "starttime" ,length=20)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    @Column(name = "endtime" ,length=20)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
    
    @Column(name = "typeflow" ,length=20)
    public String getTypeFlow() {
        return typeFlow;
    }

    public void setTypeFlow(String typeFlow) {
        this.typeFlow = typeFlow == null ? null : typeFlow.trim();
    }
}