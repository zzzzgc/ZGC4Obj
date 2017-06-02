package xinxing.boss.admin.common.utils.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class HtmlFilter {

	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签   
	
	/**
	 * 过滤掉html标签
	 * 
	 * @param str
	 * @return
	 */
	public static String filterHtml(String str) {
		if (StringUtils.isNotBlank(str)) {
			Pattern pattern = Pattern.compile(regxpForHtml);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");
				result1 = matcher.find();
			}
			matcher.appendTail(sb);
			return sb.toString();
		}
		return str;
	}
}
