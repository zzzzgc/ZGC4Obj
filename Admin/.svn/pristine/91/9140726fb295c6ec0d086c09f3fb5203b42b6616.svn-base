package xinxing.boss.admin.common.utils.parameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerProductInfoService;
import xinxing.boss.admin.boss.operation.domain.OperationRecords;
import xinxing.boss.admin.boss.operation.service.OperationRecordsService;
import xinxing.boss.admin.boss.other.domain.BaseDomain;
import xinxing.boss.admin.boss.other.domain.BossConfig;
import xinxing.boss.admin.boss.other.domain.BossCustomerRoute;
import xinxing.boss.admin.boss.other.service.BossConfigService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderProductInfoService;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.system.user.service.UserService;

/**
 * 启动时加载各种参数
 * 
 * @author Administrator
 * 
 */
public class ParameterListener implements ServletContextListener {
	public static Logger logger = LoggerFactory.getLogger(ParameterListener.class);
	/*
	 * 天猫超时时间(h)
	 */
	public static Double TMALL_OUTTIME = null;
	public static String[] activityStatusStr = new String[] { "未处理", "成功", "失败", "处理中" };
	public static String[] orderPhoneStatusStr = new String[] { "未处理", "成功", "失败", "处理中" };
	public static String[] tableTypeStr = new String[] { "", "采购商", "供应商", "采购商产品", "供应商产品" };
	public static String[] changeTypeStr = new String[] { "冻结", "开通", "更改折扣" };
	public static String[] failStatusStr = new String[] { "", "直接失败", "缓存" };
	public static String[] rechargeStr = new String[] { "未充值", "充值中", "充值成功", "退款" };
	public static String[] handleStatusStr = new String[] { "", "失败转手工", "人工转手工", "供应商回应异常转手工", "无订单手工", "超时手工", "运营商地市关闭转手工", "未完成订单转手工","天猫订单缓存" };
	// 采购商或者供应商
	public static String[] cusOrProStr = new String[] { "采购商", "供应商" };
	public static String[] roleStr = new String[] { "内部员工", "外包客服" };
	public static String[] auditStr = new String[] { "未审核", "审核通过", "审核不通过" };
	public static String[] isNoStr = new String[] { "否", "是" };
	public static String[] yesNoStr = new String[] { "否", "是" };
	public static String[] yesStr = new String[] { "不会", "会" };
	public static String[] freezeStr = new String[] { "冻结", "正常" };
	public static String[] needStr = new String[] { "不需要", "需要" };
	public static String[] operatorStatusStr = new String[] { "关闭", "开通" };
	public static String[] gdCityStr = new String[] {"揭阳","江门","云浮","广州","阳江","清远","中山","深圳","惠州","茂名","珠海","汕尾","湛江","梅州","韶关","佛山","东莞","潮州","河源","汕头","肇庆"};

	public static String[] provinceStr = new String[] { "安徽", "北京", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河南", "河北", "黑龙江", "湖北", "湖南", "吉林", "江苏",
			"江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "重庆" };
	public static String[] productProvinceStr = new String[] { "全国", "安徽", "北京", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河南", "河北", "黑龙江", "湖北", "湖南",
			"吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "重庆" };

	public static String[] specStr = new String[] { "1", "5", "10", "20", "30", "50", "70", "80", "100", "150", "200", "280", "300", "500", "600",
			"700", "800", "1024", "1536", "2048", "3072", "4096", "5120", "6144", "11264" };
	// 1,2,3
	public static String[] providerStr = new String[] { "", "移动", "联通", "电信" };
	public static String[] operatorStr = new String[] { "", "移动", "联通", "电信" };
	public static String[] areaStr = new String[] { "本省", "全国" };

	// Boss新增查询条件
	public static String[] orderStatusStr = new String[] { "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需要手工处理" };
	public static String[] eleOrderStatusStr = new String[] { "兑换失败", "兑换成功", "下单中", "下单成功", "下单失败", "验证中", "验证失败" };
	public static String[] callbackStatusStr = new String[] { "", "未回调", "已回调", "主动查询", "不主动回调" };
	public static String[] successFailStr = new String[] { "", "成功", "失败" };
	public static String[] tickTypeStr = new String[] { "", "增值税发票", "普通发票" };
	public static String[] costTypeStr = new String[] { "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款" };
	public static String[] productStr = new String[] { "", "使用中", "已下架" };
	public static String[] numberStr = new String[] { "", "M", "G", "元" };
	public static String[] productTypeStr = new String[] { "", "流量包", "话费" };
	public static String[] gradeStr = new String[] { "一般", "严重", "特别严重" };
	public static String[] cardStr = new String[] { "已取走", "未取走" };
	
	//中转平台新增的查询条件
	public static String[] transferOrderStatusStr = new String[]{"失败","成功","等待","异常"};

	// 参数总Map
	public static Map<String, Object> sysParamMap = new HashMap<String, Object>();

	// 供应商
	public static Map<Integer, String> providerMap = new HashMap<Integer, String>();
	// 供应商
	public static Map<Integer, String> auditMap = new HashMap<Integer, String>();
	// 业务类型
	public static Map<Integer, String> workMap = new HashMap<Integer, String>();
	// 充值状态
	public static Map<Integer, String> reChargeMap = new HashMap<Integer, String>();
	// 是否
	public static Map<Integer, String> isNoMap = new HashMap<Integer, String>();
	// 冻结/正常
	public static Map<Integer, String> freezeMap = new HashMap<Integer, String>();
	// 成功失败
	public static Map<Integer, String> successMap = new HashMap<Integer, String>();
	// 需要
	public static Map<Integer, String> needMap = new HashMap<Integer, String>();
	// 供应商开通状态
	public static Map<Integer, String> operatorStatusMap = new HashMap<Integer, String>();
	// 使用区域
	public static Map<Integer, String> areaMap = new HashMap<Integer, String>();
	// 省份
	public static Map<Integer, String> provinceMap = new HashMap<Integer, String>();
	// 产品规格
	public static Map<Integer, String> specMap = new HashMap<Integer, String>();
	// 城市列表
	public static List<String[]> cityList = new ArrayList<String[]>();
	// 订单状态
	public static Map<Integer, String> orderStatusMap = new HashMap<Integer, String>();
	// 电子券订单状态
	public static Map<Integer, String> eleOrderStatusMap = new HashMap<Integer, String>();
	// 回调状态
	public static Map<Integer, String> callbackStatusMap = new HashMap<Integer, String>();
	// 成败
	public static Map<Integer, String> successFailMap = new HashMap<Integer, String>();
	// 发票类型
	public static Map<Integer, String> tickTypeMap = new HashMap<Integer, String>();
	// 收支类型
	public static Map<Integer, String> costTypeMap = new HashMap<Integer, String>();
	// 是否会回调
	public static Map<Integer, String> yesMap = new HashMap<Integer, String>();
	// 产品状态
	public static Map<Integer, String> productMap = new HashMap<Integer, String>();
	// 数量单位
	public static Map<Integer, String> numberMap = new HashMap<Integer, String>();
	// 产品类别省份(多了全国)
	public static Map<Integer, String> productProvinceMap = new HashMap<Integer, String>();
	// 产品类型
	public static Map<Integer, String> productTypeMap = new HashMap<Integer, String>();
	public static Map<Integer, String> gradeMap = new HashMap<Integer, String>();
	
	//中转平台的订单状态
	public static Map<Integer, String> transferOrderStatusMap = new HashMap<Integer, String>();
	

	public static Map<Integer, String> setToMap(Map<Integer, String> map, String[] str) {
		int i = 0;
		for (String string : str) {
			if (StringUtils.isBlank(string)) {
				i++;
				continue;
			}
			map.put(i, string);
			i++;
		}
		return map;
	}

	public static WebApplicationContext webContext = null;
	public static Map<Integer, Map<String, Object>> userMap = new HashMap<Integer, Map<String, Object>>();
	public static Map<Integer, BossCustomerRoute> bossCustomerRouteMap = new HashMap<Integer, BossCustomerRoute>();
	public static Map<String, Object> commonMap = new HashMap<String, Object>();

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		commonMap.put("isNotNeedYanzheng", BaseUtil.isNotNeedYanzheng());
		commonMap.put("isTest", BaseUtil.isLocalTest());
		commonMap.put("projectName", Constants.getString("setting_projectName"));
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc.getServletContext());
		webContext = context;
		Class<?> cls = ParameterListener.class;
		Field[] fields = cls.getDeclaredFields();
		getBossConfig(context);
		getProviderInfo(context);
		getCustomerInfo(context);
		getProductCategoryInfo(context);
		getProviderProductInfo(context);
		getCustomerProductInfo(context);
		getUserReadedBlackOrder(context);
		getInfo("bossCustomerRouteService");
		getUserMap(userMap);
		setToMap(providerMap, providerStr);
		setToMap(auditMap, auditStr);
		setToMap(areaMap, areaStr);
		setToMap(isNoMap, isNoStr);
		setToMap(freezeMap, freezeStr);
		setToMap(reChargeMap, rechargeStr);
		setToMap(needMap, needStr);
		setToMap(operatorStatusMap, operatorStatusStr);
		setToMap(provinceMap, provinceStr);
		setToMap(specMap, specStr);
		setToMap(orderStatusMap, orderStatusStr);
		setToMap(eleOrderStatusMap, eleOrderStatusStr);
		setToMap(callbackStatusMap, callbackStatusStr);
		setToMap(successFailMap, successFailStr);
		setToMap(tickTypeMap, tickTypeStr);
		setToMap(costTypeMap, costTypeStr);
		setToMap(yesMap, yesStr);
		setToMap(productMap, productStr);
		setToMap(numberMap, numberStr);
		setToMap(productProvinceMap, productProvinceStr);
		setToMap(productTypeMap, productTypeStr);
		setToMap(gradeMap, gradeStr);
		setToMap(transferOrderStatusMap, transferOrderStatusStr);
		for (Field field : fields) {
			field.setAccessible(true);
			if (String[].class.equals(field.getType())) {
				try {
					String name = field.getName();
					String[] str = (String[]) field.get(name);
					Map<Integer, String> map = new HashMap<Integer, String>();
					setToMap(map, str);
					String mapName = name.length() - 3 > 0 ? name.substring(0, name.length() - 3) + "Map" : name.substring(0, 1) + "Map";
					sysParamMap.put(mapName, map);
					sysParamMap.put(mapName + "Json", JsonUtils.obj2Json(map));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (Map.class.equals(field.getType())) {
				Map<Integer, Object> map = new HashMap<>();
				try {
					map = (Map<Integer, Object>) field.get(field.getName());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				sysParamMap.put(field.getName(), map);
				sysParamMap.put(field.getName() + "Json", JsonUtils.obj2Json(map));
			}
		}

		sysParamMap.put("providerMap", providerMap);
		sysParamMap.put("auditMap", auditMap);
		sysParamMap.put("providerJsonMap", JsonUtils.obj2Json(providerMap));
		sysParamMap.put("workMap", workMap);
		sysParamMap.put("reChargeMap", reChargeMap);
		sysParamMap.put("isNoMap", isNoMap);
		sysParamMap.put("isNoMapJson", JsonUtils.obj2Json(isNoMap));
		sysParamMap.put("freezeMap", freezeMap);
		sysParamMap.put("freezeMapJson", JsonUtils.obj2Json(freezeMap));
		sysParamMap.put("successMap", successMap);
		sysParamMap.put("needMap", needMap);
		sysParamMap.put("operatorStatusMap", operatorStatusMap);
		sysParamMap.put("areaMap", areaMap);
		sysParamMap.put("areaJsonMap", JsonUtils.obj2Json(areaMap));
		sysParamMap.put("provinceMap", provinceMap);
		sysParamMap.put("specMap", specMap);
		sysParamMap.put("orderStatusMap", orderStatusMap);
		sysParamMap.put("eleOrderStatusMap", eleOrderStatusMap);
		sysParamMap.put("callbackStatusMap", callbackStatusMap);
		sysParamMap.put("successFailMap", successFailMap);
		sysParamMap.put("tickTypeMap", tickTypeMap);
		sysParamMap.put("costTypeMap", costTypeMap);
		sysParamMap.put("yesMap", yesMap);
		sysParamMap.put("productMap", productMap);
		sysParamMap.put("numberMap", numberMap);
		sysParamMap.put("numberJsonMap", JsonUtils.obj2Json(numberMap));
		sysParamMap.put("productProvinceMap", productProvinceMap);
		sysParamMap.put("productProvinceJsonMap", JsonUtils.obj2Json(productProvinceMap));
		sysParamMap.put("productTypeMap", productTypeMap);
		sysParamMap.put("gradeMap", gradeMap);
		sysParamMap.put("productTypeJsonMap", JsonUtils.obj2Json(productTypeMap));
		sysParamMap.put("providerInfoMap", JsonUtils.obj2Json(providerInfoMap));
		sysParamMap.put("providerInputMap", providerInfoMap);
		sysParamMap.put("customerInfoMap", JsonUtils.obj2Json(customerInfoMap));
		sysParamMap.put("customerInputMap", customerInfoMap);
		sysParamMap.put("productCategoryInfoMap", JsonUtils.obj2Json(productCategoryInfoMap));
		sysParamMap.put("productCategoryInputMap", productCategoryInfoMap);
		sysParamMap.put("providerProductInfoMap", JsonUtils.obj2Json(providerProductMap));
		sysParamMap.put("providerProductInputMap", providerProductMap);
		sysParamMap.put("userMap", userMap);
		sysParamMap.put("userMapJson", JsonUtils.obj2Json(userMap));
		sysParamMap.put("transferOrderStatusMap", transferOrderStatusMap);

		// 加载城市
		cityList.add(CityParamer.guangdong);
		cityList.add(CityParamer.guangxi);
		cityList.add(CityParamer.beijing);
		cityList.add(CityParamer.shanghai);
		cityList.add(CityParamer.fujian);
		cityList.add(CityParamer.zhejiang);
		cityList.add(CityParamer.gansu);
		cityList.add(CityParamer.guizhou);
		cityList.add(CityParamer.hebei);
		cityList.add(CityParamer.henan);
		cityList.add(CityParamer.heilongjiang);
		cityList.add(CityParamer.hubei);
		cityList.add(CityParamer.hunan);
		cityList.add(CityParamer.jilin);
		cityList.add(CityParamer.jiangsu);
		cityList.add(CityParamer.jiangxi);
		cityList.add(CityParamer.liaoning);
		cityList.add(CityParamer.neimenggu);
		cityList.add(CityParamer.tianjin);
		cityList.add(CityParamer.chongqing);
		cityList.add(CityParamer.anhui);
		cityList.add(CityParamer.ningxia);
		cityList.add(CityParamer.qinghai);
		cityList.add(CityParamer.shandong);
		cityList.add(CityParamer.shanxi);
		cityList.add(CityParamer.shanXi);
		cityList.add(CityParamer.sichuan);
		cityList.add(CityParamer.xizang);
		cityList.add(CityParamer.xinjiang);
		cityList.add(CityParamer.yunnan);
		cityList.add(CityParamer.gansu);
		cityList.add(CityParamer.hainan);

		sc.getServletContext().setAttribute("sysParam", sysParamMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	// 获取供应商列表
	public static Map<Integer, ProviderInfo> providerInfoMap = new HashMap<Integer, ProviderInfo>();

	public static void getProviderInfo(WebApplicationContext context) {
		ProviderInfoService providerInfoService = (ProviderInfoService) context.getBean("providerInfoService");
		List<ProviderInfo> list = providerInfoService.getAll();

		if (list != null && list.size() > 0) {
			providerInfoMap.clear();
			for (ProviderInfo providerInfo : list) {
				providerInfoMap.put(providerInfo.getId(), providerInfo);

			}
		}
	}

	// 获取采购商列表
	public static Map<Integer, CustomerInfo> customerInfoMap = new HashMap<Integer, CustomerInfo>();

	public static void getCustomerInfo(WebApplicationContext context) {
		CustomerInfoService customerInfoService = (CustomerInfoService) context.getBean("customerInfoService");
		List<CustomerInfo> list = customerInfoService.getAll();

		if (list != null && list.size() > 0) {
			customerInfoMap.clear();
			for (CustomerInfo customerInfo : list) {
				customerInfoMap.put(customerInfo.getId(), customerInfo);

			}
		}
	}

	// 获取分类产品列表
	public static Map<Integer, ProductCategoryInfo> productCategoryInfoMap = new HashMap<Integer, ProductCategoryInfo>();

	public void getProductCategoryInfo(WebApplicationContext context) {
		ProductCategoryInfoService productCategoryInfoService = (ProductCategoryInfoService) context.getBean("productCategoryInfoService");
		List<ProductCategoryInfo> list = productCategoryInfoService.getAll();
		if (list != null && list.size() > 0) {
			productCategoryInfoMap.clear();
			for (ProductCategoryInfo productCategoryInfo : list) {
				if (productCategoryInfo.getPid() == null) {
					productCategoryInfoMap.put(productCategoryInfo.getId(), productCategoryInfo);
				}
			}
		}
	}

	// 获取供应商产品列表
	public static Map<Integer, ProviderProductInfo> providerProductMap = new HashMap<Integer, ProviderProductInfo>();

	public void getProviderProductInfo(WebApplicationContext context) {
		ProviderProductInfoService providerProductInfoService = (ProviderProductInfoService) context.getBean("providerProductInfoService");
		List<ProviderProductInfo> list = providerProductInfoService.getAll();
		if (list != null && list.size() > 0) {
			providerProductMap.clear();
			for (ProviderProductInfo providerProductInfo : list) {
				providerProductMap.put(providerProductInfo.getId(), providerProductInfo);
			}
		}
	}

	// 获取采购商产品列表
	public static Map<Integer, CustomerProductInfo> customerProductInfoMap = new HashMap<Integer, CustomerProductInfo>();

	public void getCustomerProductInfo(WebApplicationContext context) {
		CustomerProductInfoService customerProductInfoService = (CustomerProductInfoService) context.getBean("customerProductInfoService");
		List<CustomerProductInfo> list = customerProductInfoService.getAll();
		if (list != null && list.size() > 0) {
			customerProductInfoMap.clear();
			for (CustomerProductInfo customerProductInfo : list) {
				customerProductInfoMap.put(customerProductInfo.getId(), customerProductInfo);
			}
		}
	}
		
	public static Map<Integer, Map<String, Object>> bossCustomerMap = new HashMap<Integer, Map<String, Object>>();
	public static Map<Integer, Map<String, Object>> bossProviderMap = new HashMap<Integer, Map<String, Object>>();

	// 获取采购商和供货商对应表 财务用
	public void getCxOldBossCustomerAndBossProvider(WebApplicationContext context, String customerTableName,
			Map<Integer, Map<String, Object>> bossCustomerMap, String providerTableName, Map<Integer, Map<String, Object>> bossProviderMap) {
		UserService userService = (UserService) context.getBean("userService");
		List<Map<String, Object>> bossCustomerList = userService.queryUser("select id,customerName from " + customerTableName);
		if (bossCustomerList.size() > 0) {
			bossCustomerMap.clear();
			for (Map<String, Object> obj : bossCustomerList) {
				bossCustomerMap.put((Integer) obj.get("id"), obj);
			}
		}
		List<Map<String, Object>> bossProviderList = userService.queryUser("select id,providerName from " + providerTableName);
		if (bossProviderList.size() > 0) {
			bossProviderMap.clear();
			for (Map<String, Object> obj : bossProviderList) {
				bossProviderMap.put((Integer) obj.get("id"), obj);
			}
		}
	}

	// 获取boss配置信息
	public static Map<String, String> bossConfigMap = new HashMap<String, String>();

	public void getBossConfig(WebApplicationContext context) {
		BossConfigService bossConfigService = (BossConfigService) context.getBean("bossConfigService");
		List<BossConfig> list = bossConfigService.getAll();
		if (list != null && list.size() > 0) {
			bossConfigMap.clear();
			for (BossConfig bossConfig : list) {
				bossConfigMap.put(bossConfig.getName(), bossConfig.getContent());
			}
		}
	}


	// 记录对应客服人员所看到的黑名单
	public static Map<String, List<String>> userReadedBlackOrderMap = new HashMap<>();

	public void getUserReadedBlackOrder(WebApplicationContext context) {
		UserService userService = (UserService) context.getBean("userService");
		OperationRecordsService operationRecordsService = (OperationRecordsService) context.getBean("operationRecordsService");
		List<OperationRecords> operationRecords = operationRecordsService.getReadedOrders();
		List<Map<String, Object>> users = userService.queryUser("select isCustomer,login_name from sys_user");

		for (Map<String, Object> user : users) {
			if ((Byte) user.get("isCustomer") == 1)
				continue;
			List<String> list = new ArrayList<>();
			for (OperationRecords operation : operationRecords) {
				if (operation.getUserName().equals((String) user.get("login_name"))) {
					list.add(operation.getOtherId());
				}
			}
			userReadedBlackOrderMap.put((String) user.get("login_name"), list);
		}
	}

	// 刷新内存
	public static void refresh(String name) {
		ParameterListener p = new ParameterListener();
		switch (name.trim()) {
		case "customerProductInfo":
			p.getCustomerProductInfo(webContext);
			sysParamMap.put("customerProductInfoMapJson", JsonUtils.obj2Json(customerProductInfoMap));
			sysParamMap.put("customerProductInfoMap", customerProductInfoMap);
			break;
		case "provider":
			p.getProviderInfo(webContext);
			sysParamMap.put("providerInfoMap", JsonUtils.obj2Json(providerInfoMap));
			sysParamMap.put("providerInputMap", providerInfoMap);
			break;
		case "providerProduct":
			p.getProviderProductInfo(webContext);
			sysParamMap.put("providerProductInfoMap", JsonUtils.obj2Json(providerProductMap));
			sysParamMap.put("providerProductInputMap", providerProductMap);

			break;
		case "product":
			p.getProductCategoryInfo(webContext);
			sysParamMap.put("productCategoryInfoMap", JsonUtils.obj2Json(productCategoryInfoMap));
			sysParamMap.put("productCategoryInputMap", productCategoryInfoMap);
			break;
		case "bossConfig":
			p.getBossConfig(webContext);
			sysParamMap.put("bossConfigMap", JsonUtils.obj2Json(bossConfigMap));
			break;
		case "customer":
			p.getCustomerInfo(webContext);
			sysParamMap.put("customerInfoMap", JsonUtils.obj2Json(customerInfoMap));
			sysParamMap.put("customerInputMap", customerInfoMap);
			break;
		default:
			logger.info("刷新失败，参数错误  name:" + name);
			break;
		}
	}

	/**
	 * 获得User 缓存
	 * 
	 * @param infoName
	 * @param map
	 * @return
	 */
	public static void getUserMap(Map<Integer, Map<String, Object>> userMap) {
		UserService userService = (UserService) ParameterListener.webContext.getBean("userService");
		List<Map<String, Object>> list = userService.queryUser("select id,name,login_name,password,salt,email,phone from sys_user");
		logger.error("userMapSize:" + list.size());
		if (list != null && list.size() > 0) {
			userMap.clear();
			for (Map<String, Object> baseDomain : list) {
				userMap.put((Integer) baseDomain.get("id"), baseDomain);
			}
		}
	}

	public static <T extends BaseService<K, Integer>, K extends BaseDomain> Map<Integer, K> getInfo(String infoName) {
		WebApplicationContext context = ParameterListener.webContext;
		Map<Integer, K> map = new HashMap<Integer, K>();
		T providerProductInfoService = (T) context.getBean(infoName);
		List<K> list = providerProductInfoService.getAll();
		if (list != null && list.size() > 0) {
			map.clear();
			for (K providerProductInfo : list) {
				map.put(providerProductInfo.getId(), providerProductInfo);
			}
		}
		return map;
	}

	/**
	 * 刷新Boss缓存
	 * 
	 * @param type
	 * @return
	 */
	public static String flushStatus(String type) {
		String str = "{\"type\":\"" + type + "\"}";
		String reqUrl = Constants.getString("bossFreshUrl_send");
		String string = HttpUtils.sendPost(reqUrl, str, "UTF-8");
		logger.info("刷新缓存,bossFreshUrl_send:" + reqUrl + ",param:" + str+",返回:"+string);
		reqUrl = Constants.getString("bossFreshUrl_api");
		string = HttpUtils.sendPost(reqUrl, str, "UTF-8");
		logger.info("刷新缓存,bossFreshUrl_api:" + reqUrl + ",param:" + str+",返回:"+string);
		return string;
	}

}
