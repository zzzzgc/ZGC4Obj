package xinxing.boss.admin.common.utils.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.persistence.PropertyFilter.MatchType;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.system.user.utils.UserUtil;

public class BaseUtil {
	public static Logger log = LoggerFactory.getLogger(BaseUtil.class);
	public static String LOCAL_TEST_NAME = "filterSearchTime";
	public static String LOCAL_IS_NEED_YANZHENG = "local_is_need_yanzheng";
	private static Boolean IS_LOCAL_TEST = null;

	public static void getUserOperateLog(Logger logger, Object... values) {
		String userName = UserUtil.getCurrentUser().getName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		StringBuilder sb = new StringBuilder(250);
		sb.append(userName).append(" 方法:").append(method);
		for (Object obj : values) {
			sb.append(" 参数:").append(JsonUtils.obj2Json(obj));
		}
		logger.warn(sb.toString());
	}

	/**
	 * 在setting.properties中添加一个filterSearchTime = 2012-12-12 00:00:00
	 * 自己测试的时候就不用因为测试数据过期而修改值 对线上也没有影响
	 * 
	 * @param filters
	 * @param time
	 * @throws ParseException
	 */
	public static void testSearch(List<PropertyFilter> filters, String filterName) throws ParseException {
		if (Constants.getString("filterSearchTime") != null) {
			for (PropertyFilter p : filters) {
				if (p.getMatchType() == MatchType.GE && p.getPropertyName().equals(filterName)) {
					Long time = (new Date().getTime() - ((Date) p.getMatchValue().get(0)).getTime()) / 1000;
					if (time <= 60 * 60 * 24) {
						List<Object> list = new ArrayList<Object>();
						// Date addMonths = DateUtils.addMonths(date, -3);
						list.add(DateUtils.addMonths(new Date(), -3));
						// list.add(DateUtils.getDateByStr(Constants.getString("filterSearchTime")));
						p.setMatchValue(list);
					}
				}
			}
		}
	}

	/**
	 * 防止导出数据过多而降低数据库效率 通过判断导出数据的时间来看是不是要导出数据
	 * 
	 * @param filter_GED_time
	 * @param filter_LED_time
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static Boolean isOverMonth(String filter_GED_time, String filter_LED_time, Integer month) throws ParseException {
		if (month == null)
			month = 1;
		Boolean pass = false;
		Date GED_time = null;
		Date LED_time;
		if (!StringUtils.isEmpty(filter_GED_time)) {
			GED_time = DateUtils.getDateByStr(filter_GED_time);
		} else
			pass = true;
		if (StringUtils.isEmpty(filter_LED_time)) {
			LED_time = new Date();
		} else
			LED_time = DateUtils.getDateByStr(filter_LED_time);
		if (GED_time != null && LED_time != null) {
			Long time = (LED_time.getTime() - GED_time.getTime()) / 1000;
			System.out.println(time);
			System.out.println(60 * 60 * 24 * 30);
			if (time > 60 * 60 * 24 * 30 * month)
				pass = true;
		}
		return pass;
	}

	/**
	 * 去掉字符串最后一个字符 如果字符串长度为0 则不操作;
	 * 
	 * @param sb
	 * @return
	 */
	public static String subLastWord(String sb) {
		return sb.length() != 0 ? sb.substring(0, sb.length() - 1) : sb;
	}

	/**
	 * 
	 * 数组或者list变为 0,1,2类似的字符串
	 * 
	 * @param sb
	 * @return
	 */
	public static String getStrByArraysOrList(Object obj) {
		StringBuffer sb = new StringBuffer(100);
		if (obj.getClass() == String[].class) {
			Object[] strs = (String[]) obj;
			for (Object o : strs) {
				sb.append(o).append(",");
			}
		} else if (obj.getClass() == List.class || obj.getClass() == ArrayList.class) {
			List<Object> list = (List) obj;
			for (Object object : list) {
				sb.append(object).append(",");
			}
		} else if (obj.getClass() == Integer[].class) {
			Object[] strs = (Integer[]) obj;
			for (Object o : strs) {
				sb.append(o).append(",");
			}
		}
		return subLastWord(sb.toString());
	}

	/**
	 * 获取web-inf的绝对地址
	 * 
	 * @return
	 */
	public static String getWEBINFUrl() {
		String parthArg = BaseUtil.class.getResource("").toString();
		// 将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）
		parthArg = parthArg.substring(5, parthArg.length()).replaceAll("%20", " "); // 如果是window请使用
																					// parthArg=parthArg.substring(6,parthArg.length()).replaceAll("%20",
																					// "");
		int num = parthArg.indexOf("WEB-INF");
		// 截取
		if ("/".equals(parthArg.substring(0, 1)) || "\\".equals(parthArg.substring(0, 1))) {
			// parthArg = parthArg.substring(1, num + "WEB-INF".length()); //本地
			parthArg = "/" + parthArg.substring(1, num + "WEB-INF".length());// linux
		}
		return parthArg;
	}

	/**
	 * 获取下载文件备份地址
	 * 
	 * @return
	 */
	public static String getBackUrl() {
		String bossCsvUrl = Constants.getString("setting_bossCsvUrl");
		if (StringUtils.isNotEmpty(bossCsvUrl)) {
			return bossCsvUrl;
		} else {
			String webinfUrl = getWEBINFUrl();
			webinfUrl = webinfUrl.replaceAll("/webapps/XinXingBossAdmin/WEB-INF", "");
			return webinfUrl;
		}
	}

	public static String getSqlConditionByFilters(List<PropertyFilter> filters, Logger log, Boolean isSingle) {
		return "";
	}

	/**
	 * 根据filter 获得sql条件
	 * 
	 * @param filters
	 * @param sql
	 * @param log
	 * @return
	 * @throws ParseException
	 */
	public static String getSqlConditionByFilters(List<PropertyFilter> filters, Logger log) {
		StringBuffer sql = new StringBuffer(100);

		List<String> conditionList = getConditions(filters, log);

		for (int j = 0; j < conditionList.size(); j++) {
			if (j == 0) {
				sql.append(" where ");
			} else
				sql.append(" and ");
			sql.append(conditionList.get(0));
		}

		return sql.toString();
	}

	public static List<String> getConditions(List<PropertyFilter> filters, Logger log) {
		List<String> conditionList = new ArrayList<>();
		for (int i = 0, len = filters.size(); i < len; i++) {
			MatchType matchType = filters.get(i).getMatchType();
			String bijiao = "";
			List<Object> matchValueList = filters.get(i).getMatchValue();
			if (matchValueList.size() > 0) {
				String matchValue = matchValueList.get(0).toString();
				log.info(matchValue + "::::::" + matchValueList.get(0).getClass());

				Class<? extends Object> cls = matchValueList.get(0).getClass();
				if (cls.equals(String.class) || cls.equals(Date.class)) {
					if (cls.equals(Date.class)) {
						SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
						try {
							matchValue = DateUtils.formatDate(sdf1.parse(matchValue), "yyyy-MM-dd HH:mm:ss");
						} catch (ParseException e) {
							log.equals("parse错误:" + e.getMessage());
						}
					}
					matchValue = "'" + matchValue + "'";
					log.info(matchValue);
				}
				switch (matchType) {
				case EQ:
					bijiao = "=" + matchValue;
					break;
				case LIKE:
					bijiao = " like ('%" + matchValue.replaceAll("'", "") + "%')";
					break;
				case LT:
					bijiao = "<" + matchValue;
					break;
				case GT:
					bijiao = ">" + matchValue;
					break;
				case GE:
					bijiao = ">=" + matchValue;
					break;
				case LE:
					bijiao = "<=" + matchValue;
					break;
				case IN:
					String str = "";
					if (matchValueList.size() > 0) {
						for (Object object : matchValueList) {
							if (object.getClass().equals(String.class) || object.getClass().equals(Date.class))
								str += "'" + object.toString() + "',";
							else {
								str += object.toString() + ",";
							}
						}
						str = str.substring(0, str.length() - 1);
					}
					bijiao = " in (" + str + ")";
					break;
				default:
					break;
				}
			}
			conditionList.add(filters.get(i).getPropertyName() + bijiao);
		}
		return conditionList;
	}

	public static Boolean isLocalTest() {
		if (IS_LOCAL_TEST == null) {
			IS_LOCAL_TEST = Constants.getBoolean("setting_isLocalTest");
			if (IS_LOCAL_TEST == null) {
				IS_LOCAL_TEST = false;
			}
		}
		return IS_LOCAL_TEST;
	}

	public static Boolean isNotNeedYanzheng() {
		return Constants.getBoolean("setting_isNotNeedYanzheng");
	}

}
