package cn.com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public class StringUtils {

	public static String strEmptyToNull (Object str) {
		if (str == "" || str == null) {
			return null;
		}
		return str.toString();
	}

	public static boolean isEmpty(Object str) {
		if (str instanceof String) {
			return (str == null || "".equals(str));
		} else {
			return str == null;
		}
	}
	
	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}
	
	/**
	 * 是否为空。只要一个为空。返回TRUE
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object... str) {
		for (int i=0; i<str.length; i++) {
			if (isEmpty(str[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static final String SPLIT_DEFAULT = ",";
	
	/**
	 * 处理数据
	 * @param value
	 * @param split
	 * @return
	 */
	public static String splitFormat(String value, String split) {
		String[] values = value.split(split);
		int size = values.length;
		String temp = "";
		for (int i=0; i<size; i++) {
			temp += values[i] + SPLIT_DEFAULT;
		}
		return temp.substring(0, temp.lastIndexOf(SPLIT_DEFAULT));
	}
	
	
    /** 
     * 将emoji表情替换成空串 
     *   
     * @param source 
     * @return 过滤后的字符串 
     */  
    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll(
                    "[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "*");
        } else {
            return source;
        }
    }

	/**
	 * 判断是否含有特殊字符
	 *
	 * @param str
	 * @return true为包含，false为不包含
	 * @author LiDaDa
	 */
	public static boolean isSpecialChar(String str) {
		String regEx = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？% ]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 替换null字符串为""并去两端空格
	 *
	 * @param obj
	 * @return
	 */
	public static String nTV(Object obj) {
		return (obj == null || "null".equals(obj)) ? "" : obj.toString().trim();
	}

	/**
	 * 替换null或者"" 为"value"值并去两端空格
	 *
	 * @param obj
	 * @return
	 */
	public static String nTVI(Object obj, String value) {
		obj = nTV(obj);
		return "".equals(obj) ? value : obj.toString();
	}
}
