package xinxing.boss.admin.boss.customer.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.customer.domain.CustomerDataAnalyze;
import xinxing.boss.admin.boss.customer.service.CustomerDataAnalyzeService;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/customerDataAnalyze")
public class CustomerDataAnalyzeCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CustomerDataAnalyzeCmd.class);

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private CustomerDataAnalyzeService customerDataAnalyzeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/customerDataAnalyze/customerDataAnalyzeList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "customerDataAnalyzeList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) throws Exception {
		Page<CustomerDataAnalyze> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page.setPageSize(100000000);
		page.setPageNo(1);
		page = customerDataAnalyzeService.search(page, filters);
		Map<Integer, CustomerDataAnalyze> pdaMap = new HashMap<>();
		List<CustomerDataAnalyze> result = page.getResult();
		for (CustomerDataAnalyze customerDataAnalyze : result) {
			Integer customerId = customerDataAnalyze.getCustomerId();
			if (pdaMap.get(customerId) != null) {
				CustomerDataAnalyze customerDataAnalyze2 = pdaMap.get(customerId).addPda(
						customerDataAnalyze);
			} else
				pdaMap.put(customerId, customerDataAnalyze);
		}
		List<CustomerDataAnalyze> pdaList = new ArrayList<>();
		Set<Entry<Integer, CustomerDataAnalyze>> entrySet = pdaMap.entrySet();
		for (Entry<Integer, CustomerDataAnalyze> entry : entrySet) {
			pdaList.add(entry.getValue());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerDataAnalyze> rows = getOrderByMap(request, pdaList, page);
		CustomerDataAnalyze tongji = tongji(pdaList);
		for (CustomerDataAnalyze cda : rows) {
			if (cda.getTotalMoney() != 0) {
				cda.setSuccessRate(cda.getSuccessNum().doubleValue() / cda.getTotalNum());
			}
			if (cda.getUseTime() != 0) {
				cda.setUseTime(cda.getUseTime() / cda.getTotalNum());
			}
			cda.setEarn(cda.getSuccessMoney() - cda.getSuccessCost());
			if (cda.getSuccessMoney() != 0) {
				cda.setMaoEarn(cda.getEarn() / cda.getSuccessMoney());
			}
			cda = cda.format2decimalPoint();
		}
		map.put("rows", rows);
		map.put("total", pdaList.size());
		map.put("tongji", tongji.format2decimalPoint());
		map.put("isSuccess", true);

		return map;
	}

	private CustomerDataAnalyze tongji(List<CustomerDataAnalyze> cdaList) {
		CustomerDataAnalyze tongji = new CustomerDataAnalyze();
		for (CustomerDataAnalyze pda : cdaList) {
			tongji.setTotalMoney(tongji.getTotalMoney() + pda.getTotalMoney());
			tongji.setTotalNum(tongji.getTotalNum() + pda.getTotalNum());
			tongji.setSuccessMoney(tongji.getSuccessMoney() + pda.getSuccessMoney());
			tongji.setSuccessNum(tongji.getSuccessNum() + pda.getSuccessNum());
			tongji.setSuccessCost((tongji.getSuccessCost() + pda.getSuccessCost()));
			tongji.setEarn(tongji.getEarn() + pda.getEarn());
			tongji.setDianxinNum(tongji.getDianxinNum() + pda.getDianxinNum());
			tongji.setLiantongNum(tongji.getLiantongNum() + pda.getLiantongNum());
			tongji.setYidongNum(tongji.getYidongNum() + pda.getYidongNum());
			tongji.setUseTime(tongji.getUseTime() + pda.getUseTime());
		}
		if (tongji.getTotalMoney() != 0) {
			tongji.setSuccessRate(tongji.getSuccessNum().doubleValue() / tongji.getTotalNum());
		}
		if (tongji.getTotalNum() != 0) {
			Long sum = 0l;
			for (CustomerDataAnalyze customerDataAnalyze : cdaList) {
				if (customerDataAnalyze.getUseTime() != 0) {
					sum += customerDataAnalyze.getUseTime();
				}
			}
			tongji.setUseTime(sum / tongji.getTotalNum());
		}
		tongji.setEarn(tongji.getSuccessMoney() - tongji.getSuccessCost());
		if (tongji.getSuccessMoney() != 0) {
			tongji.setMaoEarn(tongji.getEarn() / tongji.getSuccessMoney());
		}
		return tongji;
	}

	/*
	 * 排序
	 */
	private List<CustomerDataAnalyze> getOrderByMap(HttpServletRequest request,
			List<CustomerDataAnalyze> cdaList, Page<CustomerDataAnalyze> p)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// 排序
		String order = p.getOrder();
		String orderBy = p.getOrderBy();
		String methodName = "get" + orderBy.substring(0, 1).toUpperCase()
				+ orderBy.substring(1, orderBy.length());
		Method method = CustomerDataAnalyze.class.getMethod(methodName);
		Type genericReturnType = method.getGenericReturnType();
		CustomerDataAnalyze[] pdas = (CustomerDataAnalyze[]) cdaList
				.toArray(new CustomerDataAnalyze[cdaList.size()]);
		if (genericReturnType == Integer.class) {
			//从小到大
			for (int i = 0; i < pdas.length; i++) {
				for (int j = i + 1; j < pdas.length; j++) {
					Integer value1 = (Integer) method.invoke(pdas[i], null);
					Integer value2 = (Integer) method.invoke(pdas[j], null);
					if (value1 > value2) {
						CustomerDataAnalyze swap = pdas[i];
						pdas[i] = pdas[j];
						pdas[j] = swap;
					}
				}
			}
		} else if (genericReturnType == Double.class) {

			//从小到大
			for (int i = 0; i < pdas.length; i++) {
				for (int j = i + 1; j < pdas.length; j++) {
					Double value1 = (Double) method.invoke(pdas[i], null);
					Double value2 = (Double) method.invoke(pdas[j], null);
					if (value1 > value2) {
						CustomerDataAnalyze swap = pdas[i];
						pdas[i] = pdas[j];
						pdas[j] = swap;
					}
				}
			}

		} else if (genericReturnType == Long.class) {
			//从小到大
			for (int i = 0; i < pdas.length; i++) {
				for (int j = i + 1; j < pdas.length; j++) {
					Long value1 = (Long) method.invoke(pdas[i], null);
					Long value2 = (Long) method.invoke(pdas[j], null);
					if (value1 > value2) {
						CustomerDataAnalyze swap = pdas[i];
						pdas[i] = pdas[j];
						pdas[j] = swap;
					}
				}
			}

		} else if (genericReturnType == String.class) {
			sort(cdaList, methodName, order);
			pdas = (CustomerDataAnalyze[]) cdaList.toArray(new CustomerDataAnalyze[cdaList.size()]);
		}
		int pageNo = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int first = (pageNo - 1) * pageSize;
		int last = pageNo * pageSize;
		List<CustomerDataAnalyze> rows = new ArrayList<>();
		if ("desc".equals(order)) {
			for (int i = pdas.length - first - 1; i > pdas.length - last - 1; i--) {
				if (i < pdas.length && i >= 0) {
					rows.add(pdas[i]);
				}
			}
		} else {
			for (int i = first; i < last; i++) {
				if (i < pdas.length && i >= 0) {
					rows.add(pdas[i]);
				}
			}
		}
		return rows;
	}

	public <T> void sort(List<T> list, final String method, final String sort) {
		// 用内部类实现排序  
		Collections.sort(list, new Comparator<T>() {

			public int compare(T a, T b) {
				int ret = 0;
				try {
					// 获取m1的方法名  
					Method m1 = a.getClass().getMethod(method, null);
					// 获取m2的方法名  
					Method m2 = b.getClass().getMethod(method, null);
					// 正序排序  
					ret = m1.invoke(((T) a), null).toString()
							.compareTo(m2.invoke(((T) b), null).toString());
				} catch (NoSuchMethodException ne) {
					ne.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return ret;
			}
		});
	}

	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
	}
	
	/**
	 * 从orderinfo中获取数据,放到分析表中
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createCDA")
	@ResponseBody
	//TODO
	public String createCDA(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
			throws Exception {
		List<String> sqlList = new ArrayList<>();
		List<Map<String, Object>> query = orderInfoService
				.query("select receiveTime from boss_order ORDER BY receiveTime  limit 0,1");
		Date date = (Date) query.get(0).get("receiveTime");
		date=DateUtils.getDateStart(date);
		date.setDate(date.getDate() - 2);
		Date today = new Date();
		today = DateUtils.getDateStart(today);
		while (date.before(today)) {
			String GED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateEnd(date);
			String LED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateStart(date);
			date.setDate(date.getDate()+1);
			Map<String, Object> map = new HashMap<String, Object>();
			Page<OrderInfo> page = new Page<>();
			page.setPageSize(100000000);
			List<PropertyFilter> filters = new ArrayList<>();
			filters.add(new PropertyFilter("GED_receiveTime", GED_receiveTime));
			filters.add(new PropertyFilter("LED_receiveTime", LED_receiveTime));
			page = orderInfoService.search(page, filters);
			List<OrderInfo> result = page.getResult();
			logger.info(GED_receiveTime + "," + LED_receiveTime + "------------------------"
					+ result.size());
			if (result.size() == 0)
				continue;
			Map<Integer, CustomerDataAnalyze> cdaMap = new HashMap<>();
			for (OrderInfo orderInfo : result) {
				Integer customerId = orderInfo.getCustomerId();
				CustomerDataAnalyze cda = null;
				if (cdaMap.get(customerId) == null) {
					cda = new CustomerDataAnalyze();
					cda.setCustomerId(customerId);
				} else {
					cda = cdaMap.get(customerId);
				}
				cda.setTotalMoney(cda.getTotalMoney() + orderInfo.getPrice().doubleValue());
				// 状态为成功
				if (orderInfo.getStatus() == 3) {
					cda.setSuccessCost(cda.getSuccessCost() + orderInfo.getCost().doubleValue());
					cda.setSuccessMoney(cda.getSuccessMoney() + orderInfo.getPrice().doubleValue());
					cda.setSuccessNum(cda.getSuccessNum() + 1);
				}
				String operation = orderInfo.getBeyoudOperation();
				if ("联通".equals(operation))
					cda.setLiantongNum(cda.getLiantongNum() + 1);
				else if ("移动".equals(operation))
					cda.setYidongNum(cda.getYidongNum() + 1);
				else if ("电信".equals(operation))
					cda.setDianxinNum(cda.getDianxinNum() + 1);
				cda.setTotalNum(cda.getTotalNum() + 1);
				if (orderInfo.getStatus() == 3) {
					Long useTime = (orderInfo.getSuccessTime().getTime() - orderInfo
							.getReceiveTime().getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				} else if (orderInfo.getStatus() == 4) {
					Long useTime = (orderInfo.getFailTime().getTime() - orderInfo.getReceiveTime()
							.getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				}
				cdaMap.put(customerId, cda);
			}
			List<CustomerDataAnalyze> cdaList = new ArrayList<>();
			for (Map.Entry<Integer, CustomerDataAnalyze> entry : cdaMap.entrySet()) {
				cdaList.add(entry.getValue());
			}

			StringBuffer sql = new StringBuffer(300000);
			sql.append("INSERT boss_customer_data_analyze(customerId,totalMoney,successMoney,successCost,earn,dianxinNum,liantongNum,yidongNum,totalNum,successNum,useTime,time) value");
			if (cdaList.size() > 0) {
				//				customerId,totalMoney,successMoney,successCost,earn,
				//				dianxinNum,liantongNum,yidongNum,totalNum,successNum,
				//				useTime,time
				for (CustomerDataAnalyze pda : cdaList) {
					sql.append("(").append(pda.getCustomerId()).append(",")
							.append(pda.getTotalMoney()).append(",").append(pda.getSuccessMoney())
							.append(",").append(pda.getSuccessCost()).append(",")
							.append(pda.getEarn()).append(",").append(pda.getDianxinNum())
							.append(",").append(pda.getLiantongNum()).append(",")
							.append(pda.getYidongNum()).append(",").append(pda.getTotalNum())
							.append(",").append(pda.getSuccessNum()).append(",")
							.append(pda.getUseTime()).append(",'").append(GED_receiveTime)
							.append("'),");
				}
				sqlList.add( sql.substring(0, sql.length() - 1));
			}

		}
		customerDataAnalyzeService.save(sqlList);
		String random = RandomStringUtils.random(8);
		return "success" + random.toString();
	}

	

}
