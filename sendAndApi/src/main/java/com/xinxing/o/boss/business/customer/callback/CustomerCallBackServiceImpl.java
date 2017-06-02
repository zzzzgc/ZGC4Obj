package com.xinxing.o.boss.business.customer.callback;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.boss.business.api.service.CallbackCustomerService;
import com.xinxing.boss.interaction.pojo.customer.CustomerCallbackRecord;
import com.xinxing.boss.interaction.pojo.customer.CustomerInfo;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.boss.interaction.pojo.provider.ProviderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpClientUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.time.TimeUtils;

public class CustomerCallBackServiceImpl implements CallbackCustomerService {
	
	private static final Logger log = Logger.getLogger(CustomerCallBackServiceImpl.class);
	
	@Autowired
	private com.xinxing.boss.interaction.service.customer.a flowCustomerService;
	
	@Autowired
	private com.xinxing.boss.interaction.service.provider.a flowProviderService;
	
	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	
	@Autowired
	private com.xinxing.boss.interaction.service.common.a flowCommonService;
	
	
	@Override
	public void callBack(CustomerInfo customer,OrderInfo orderInfo){
		
		try{
			if(StringUtils.isNotBlank(customer.getCallbackAddress()) && customer!=null){
				log.info("开始回调采购商:----- orderId:"+orderInfo.getId()+",手机号："+orderInfo.getPhone()+",状态:"+orderInfo.getStatus());
				String statusStr = orderInfo.getStatus() == 3 ? "Success" : "Error";
				String failReason = orderInfo.getFailReason();
				if(orderInfo.getStatus() ==3){
					failReason = null;
				}else{
					String returnAllReasonCustomer =  flowCommonService.g("unfilter_customer_id");
					boolean returnAllReason = false;
					if(StringUtils.isNotBlank(returnAllReasonCustomer)){
						String cutomerIds[] = returnAllReasonCustomer.split(",");
						for(String cusId : cutomerIds){
							if(StringUtils.equals(cusId, customer.getId()+"")){
								returnAllReason =  true;
							}
						}
					}
					
					if(failReason!=null){
						if(failReason.indexOf("停机")>-1){
							failReason = "成员的状态为停机,不允许办理该业务!";
						}else if(failReason.indexOf("开通未售出")>-1){
							failReason = "成员的状态为已开通未售出,不允许办理该业务!";
						}else if(failReason.indexOf("已经是集团用户")>-1){
							failReason = "已经是集团成员,不允许重复加入!";
						}else if(returnAllReason){
							//failReason = "充值失败";
						}else{
							failReason = "充值失败";
						}
					}
				}
				String identityMsg="{\"FlowKey\":\""+orderInfo.getOrderKey()+"\",\"OrderKey\":\""+orderInfo.getId()+"\",\"Phone\":\"" + orderInfo.getPhone() +"\",\"OrderStatus\":\"" + statusStr + "\",\"ClientSeceret\":\"" + customer.getDevKey()+"\"}";
				String param="{\"FlowKey\":\""+orderInfo.getOrderKey()+"\",\"OrderKey\":\""+orderInfo.getId()+"\",\"Phone\":\"" + orderInfo.getPhone() + "\",\"OrderStatus\":\"" + statusStr + "\",\"FailReason\":\"" + failReason + "\",\"VerifyCode\":\"" + MD5_HexUtil.md5Hex(identityMsg).toUpperCase()+"\"}";
				if(customer.getIsBackCost()==1){
					ProviderInfo info = flowProviderService.o(orderInfo.getProviderId());
					log.info("供货商信息" + JsonUtils.obj2Json(info));
					log.info("订单信息" + JsonUtils.obj2Json(orderInfo));
					String providerId=orderInfo.getProviderId()+"";
					String providerName="";
					//防止产品为空时空指针
					if(StringUtils.isBlank(providerId)||"0".equals(providerId)){
						providerId="0";
						providerName="null";
					}else{
						providerName=info.getProviderName();
					}
					param ="{\"FlowKey\":\""+orderInfo.getOrderKey()+"\",\"OrderKey\":\""+orderInfo.getId()+"\",\"Phone\":\"" + orderInfo.getPhone() + "\",\"OrderStatus\":\"" + statusStr + "\",\"FailReason\":\"" + failReason + "\",\"Sprice\":\"" + orderInfo.getCost() + "\",\"VerifyCode\":\"" + MD5_HexUtil.md5Hex(identityMsg).toUpperCase()+"\",\"SupplierCode\":\"" + providerId + "\",\"SupplierName\":\""+providerName+"\"}";
				}
				
				log.info("回调参数:"+param+",url:"+JsonUtils.obj2Json(customer));
				String string = HttpClientUtils.doPost(customer.getCallbackAddress(), param);
				log.info("订单id:-------"+orderInfo.getId()+",回调参数返回值:"+string);
					if(StringUtils.isBlank(string)){
						string="false";
					}
					CustomerCallbackRecord ccr=new CustomerCallbackRecord();
					ccr.setOrderId(orderInfo.getId());
					ccr.setOrderKey(orderInfo.getOrderKey());
					ccr.setCallbackAddress(customer.getCallbackAddress());
					if("OK".equals(string.trim())){
						log.info("成功回调: orderId:"+orderInfo.getId());
						ccr.setStatus(1);
						//修改订单状态
						flowOrderService.e(orderInfo.getId(),2 );
					}else{
						log.info("回调失败 : orderId:"+orderInfo.getId());
						ccr.setStatus(2);
					}
					ccr.setCallbackData(param);
					ccr.setCallbackTime(TimeUtils.getNowTime());
					flowCustomerService.b(ccr);
			}
		}catch(Exception e){
			log.info("orderId:"+orderInfo.getId()+e.getMessage(),e);
			log.error("orderId:"+orderInfo.getId()+e.getMessage(),e);
		}
	}
}
