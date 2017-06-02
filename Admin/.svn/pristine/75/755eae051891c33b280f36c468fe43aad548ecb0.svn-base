package xinxing.boss.admin.boss.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.dao.CustomerInfoDAO;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.common.email.SendEmailUtils;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserRoleService;
import xinxing.boss.admin.system.user.service.UserService;
@Service
public class CustomerInfoService extends BaseService<CustomerInfo, Integer> {
	private static final Logger log =  Logger.getLogger(CustomerInfoService.class);

	@Autowired
	private CustomerInfoDAO customerInfoDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public HibernateDao<CustomerInfo, Integer> getEntityDao() {
		return customerInfoDAO;
	}
	
	/**
	 * 添加用户登录信息
	 */
	public void addCustomerLoginAccount(CustomerInfo info){
		User user = new User();
		user.setLoginName(info.getLoginName());
		user.setPlainPassword(info.getLoginPaw());
		user.setName(info.getCustomerName());
		user.setPhone(info.getCustomerNumber());
		user.setIsCustomer(1);
		user.setState(0);
		userService.save(user);
		List<Integer> list = new ArrayList<Integer>();
		list.add(4);
		userRoleService.updateUserRole(user.getId(), userRoleService.getUserRoles(user.getId()),list);
	}
	
	public void sendEmail(String customerId){
		CustomerInfo customerInfo = customerInfoDAO.get(Integer.parseInt(customerId));
		if(customerInfo!=null){
			String title="【心行登陆账号密码】";
			String htmlContent=SendEmailUtils.tmallWarnEmailContentBuilder(customerInfo);
			handleEmailException(title, htmlContent,customerInfo.getEmail());
		}
	} 
	

	/**
	 * 当发送邮件出现异常时，循环发，直到成功
	 * @param title
	 * @param htmlContent
	 * @param index
	 */
	private void handleEmailException(String title,String htmlContent,String email){
		try {
			SendEmailUtils.sendTmallWarnEmail(title, htmlContent,email);
		} catch (MessagingException e) {
			log.error("-采购商邮件发送失败-" + e.getMessage(),e);			
		}
	}
	
	/**
	 * 通过采购商简称获取采购商
	 * @param simpleName
	 * @return
	 */
	public Map<String,String> checkSimpleName(String simpleName){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtils.isBlank(simpleName)){
			map.put("msg", "请填写简称");
		}
		List<CustomerInfo> customerInfos = customerInfoDAO.checkSimpleName(simpleName);
		if(customerInfos.size()<1){
			 map.put("msg", "success");
			 return map;
		}
		CustomerInfo customer=customerInfos.get(0);
		map.put("msg", "重复的 采购商 id:"+customer.getId()+"  名称:"+customer.getCustomerName()+"  简称是:"+customer.getCusSimpleName());
		return map;
	}
}
