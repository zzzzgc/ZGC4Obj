package xinxing.boss.admin.common.utils.constants;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 取Properties数据
 *
 * @author AIXIANG
 *
 */
public class Constants {
	
	
    private static ReloadableResourceBundleMessageSource messageSource;

    private static Locale zh_CN = new Locale("zh", "CN");

    public static String getString(String key) {
        String result = null;
        try {
            result = messageSource.getMessage(key, null, null, zh_CN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Integer getInteger(String key) {
        Integer result = null;
        try {
            String msg = messageSource.getMessage(key, null, null, zh_CN);
            result = Integer.parseInt(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Double getDouble(String key) {
        Double result = null;
        try {
            String msg = messageSource.getMessage(key, null, null, zh_CN);
            result = Double.parseDouble(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean getBoolean(String key) {
        Boolean result = null;
        try {
            String msg = messageSource.getMessage(key, null, null, zh_CN);
            result = Boolean.parseBoolean(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public ReloadableResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(
            ReloadableResourceBundleMessageSource messageSource) {
        Constants.messageSource = messageSource;
    }
    
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getString("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getString("frontPath");
	}
    
}
