package cn.com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信相关的工具类
 * 
 * @author malongbo
 */
public class SmsUtils {


	final static Pattern PATTERN = Pattern.compile("^1[3-9][0-9]{9}$");
    /**
     * 检测手机号有效性*
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static final boolean isMobileNo(String mobile){
        Matcher m = PATTERN.matcher(mobile);
        return m.matches();
    }
    
    /**
     * 生成短信验证码*
     * @param length 长度
     * @return 指定长度的随机短信验证码
     */
    public static final String randomSMSCode(int length) {
        boolean numberFlag = true;
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }  

}
