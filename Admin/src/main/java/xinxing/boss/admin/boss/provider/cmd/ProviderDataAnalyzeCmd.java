package xinxing.boss.admin.boss.provider.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
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
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.service.BossConfigService;
import xinxing.boss.admin.boss.provider.domain.ProviderDataAnalyze;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProviderDataAnalyzeService;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/providerDataAnalyze")
public class ProviderDataAnalyzeCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ProviderDataAnalyzeCmd.class);

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ProviderInfoService providerInfoService;
	@Autowired
	private BossConfigService bossConfigService;
	@Autowired
	private ProviderDataAnalyzeService providerDataAnalyzeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/providerDataAnalyze/providerDataAnalyzeList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "providerDataAnalyzeList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) throws Exception {
		String tmallOut = ParameterListener.bossConfigMap.get("tmallOutTime");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", true);
		if (tmallOut == null) {
			map.put("isSuccess", false);
			map.put("rows", new ArrayList<>());
			map.put("total", 0);
			return map;
		}
		Double tmallOutTime = new BigDecimal(tmallOut).doubleValue();
		Page<ProviderDataAnalyze> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page.setPageSize(100000000);
		page.setPageNo(1);
		page = providerDataAnalyzeService.search(page, filters);
		Map<Integer, ProviderDataAnalyze> pdaMap = new HashMap<>();
		List<ProviderDataAnalyze> result = page.getResult();
		for (ProviderDataAnalyze providerDataAnalyze : result) {
			Integer providerId = providerDataAnalyze.getProviderId();
			if (pdaMap.get(providerId) != null) {
				ProviderDataAnalyze providerDataAnalyze2 = pdaMap.get(providerId).addPda(
						providerDataAnalyze);
			} else
				pdaMap.put(providerId, providerDataAnalyze);
		}
		List<ProviderDataAnalyze> pdaList = new ArrayList<>();
		Set<Entry<Integer, ProviderDataAnalyze>> entrySet = pdaMap.entrySet();
		for (Entry<Integer, ProviderDataAnalyze> entry : entrySet) {
			pdaList.add(entry.getValue());
		}
		List<ProviderDataAnalyze> rows = getOrderByMap(request, map, pdaList, page);
		ProviderDataAnalyze tongji = tongji(pdaList);
		for (ProviderDataAnalyze providerDataAnalyze : rows) {
			Integer totalNum = providerDataAnalyze.getTotalNum();
			Double totalMoney = providerDataAnalyze.getTotalMoney();
			if (totalNum != 0) {
				providerDataAnalyze.setSuccessRate(providerDataAnalyze.getSuccessNum()
						.doubleValue() / totalNum);
				providerDataAnalyze.setUseTime(providerDataAnalyze.getUseTime() / totalNum);
			}
			providerDataAnalyze = providerDataAnalyze.format2decimalPoint();
		}
		map.put("rows", rows);
		map.put("total", pdaList.size());
		map.put("tongji", tongji.format2decimalPoint());
		map.put("isSuccess", true);
		return map;
	}

	private ProviderDataAnalyze tongji(List<ProviderDataAnalyze> cdaList) {
		ProviderDataAnalyze tongji = new ProviderDataAnalyze();
		for (ProviderDataAnalyze pda : cdaList) {
			tongji.addPda(pda);
		}
		Integer totalNum = tongji.getTotalNum();
		if (totalNum != 0) {
			tongji.setSuccessRate(tongji.getSuccessNum().doubleValue() / totalNum);
			Long sum = 0l;
			for (ProviderDataAnalyze providerDataAnalyze : cdaList) {
				if (providerDataAnalyze.getUseTime() != 0) {
					sum += providerDataAnalyze.getUseTime();
				}
			}
			tongji.setUseTime(sum / totalNum);
		}
		return tongji;
	}

	/*
	 * 排序
	 */
	private List<ProviderDataAnalyze> getOrderByMap(HttpServletRequest request,
			Map<String, Object> map, List<ProviderDataAnalyze> cdaList, Page<ProviderDataAnalyze> p)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// 排序
		String order = p.getOrder();
		String orderBy = p.getOrderBy();
		String methodName = "get" + orderBy.substring(0, 1).toUpperCase()
				+ orderBy.substring(1, orderBy.length());
		Method method = ProviderDataAnalyze.class.getMethod(methodName);
		Type genericReturnType = method.getGenericReturnType();
		ProviderDataAnalyze[] pdas = (ProviderDataAnalyze[]) cdaList
				.toArray(new ProviderDataAnalyze[cdaList.size()]);
		if (genericReturnType == Integer.class) {
			//从小到大
			for (int i = 0; i < pdas.length; i++) {
				for (int j = i + 1; j < pdas.length; j++) {
					Integer value1 = (Integer) method.invoke(pdas[i], null);
					Integer value2 = (Integer) method.invoke(pdas[j], null);
					if (value1 > value2) {
						ProviderDataAnalyze swap = pdas[i];
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
						ProviderDataAnalyze swap = pdas[i];
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
						ProviderDataAnalyze swap = pdas[i];
						pdas[i] = pdas[j];
						pdas[j] = swap;
					}
				}
			}

		} else if (genericReturnType == String.class) {
			sort(cdaList, methodName, order);
			pdas = (ProviderDataAnalyze[]) cdaList.toArray(new ProviderDataAnalyze[cdaList.size()]);
		}
		int pageNo = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int first = (pageNo - 1) * pageSize;
		int last = pageNo * pageSize;
		List<ProviderDataAnalyze> rows = new ArrayList<>();
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
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "createPDA")
	@ResponseBody
	//TODO
	public String createPDA(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> query = orderInfoService
				.query("select receiveTime from boss_order ORDER BY receiveTime  limit 0,1");
		Date date = (Date) query.get(0).get("receiveTime");
		date = DateUtils.getDateStart(date);
		date.setDate(date.getDate() - 2);
		Date today = new Date();
		today=DateUtils.getDateStart(today);
		List<String> sqlList = new ArrayList<>();
		while (date.before(today)) {
			String GED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateEnd(date);
			String LED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateStart(date);
			date.setDate(date.getDate() + 1);
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
			Map<Integer, ProviderDataAnalyze> pdaMap = new HashMap<>();
			for (OrderInfo orderInfo : result) {
				Double cost = 0d;
				Integer providerId = orderInfo.getSuccessId();
				ProviderDataAnalyze cda = null;
				if (pdaMap.get(providerId) == null) {
					cda = new ProviderDataAnalyze();
					cda.setProviderId(providerId);
				} else {
					cda = pdaMap.get(providerId);
				}
				if (orderInfo.getCost() != null) {
					cost = orderInfo.getCost().doubleValue();
					cda.setTotalMoney(cda.getTotalMoney() + orderInfo.getPrice().doubleValue());
					// 状态为成功
					if (orderInfo.getStatus() == 3) {
						cda.setSuccessNum(cda.getSuccessNum() + 1);
						cda.setSuccessMoney(cda.getSuccessMoney() + cost);
					}
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
				pdaMap.put(providerId, cda);
			}
			List<ProviderDataAnalyze> cdaList = new ArrayList<>();
			for (Map.Entry<Integer, ProviderDataAnalyze> entry : pdaMap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().getTotalMoney() != 0)
					cdaList.add(entry.getValue().format2decimalPoint());
			}

			StringBuffer sql = new StringBuffer(300000);
			sql.append("INSERT boss_provider_data_analyze(providerId,totalMoney,successMoney,dianxinNum,liantongNum,yidongNum,totalNum,successNum,useTime,time) value");
			if (cdaList.size() > 0) {
				for (ProviderDataAnalyze pda : cdaList) {
					sql.append("(").append(pda.getProviderId()).append(",")
							.append(pda.getTotalMoney()).append(",")
							.append(pda.getSuccessMoney()).append(",")
							.append(pda.getDianxinNum()).append(",")
							.append(pda.getLiantongNum()).append(",")
							.append(pda.getYidongNum()).append(",")
							.append(pda.getTotalNum()).append(",")
							.append(pda.getSuccessNum()).append(",")
							.append(pda.getUseTime()).append(",'")
							.append(GED_receiveTime).append("'),");
				}

				String sql1 = sql.substring(0, sql.length() - 1);
				sqlList.add(sql1);
			}
		}
		providerDataAnalyzeService.save(sqlList);
		return "success";
	}

}
