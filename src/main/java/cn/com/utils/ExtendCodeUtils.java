package cn.com.utils;
/**
 * <p>TITLE： </p>
 * <p>DESCRIPTION： 生成邀请码</p>
 *
 * @author: QW
 * @version 1.0
 * Created on 2018年10月25日
 * Copyright  2018LiDaDa. All rights reserved.
 */
public class ExtendCodeUtils {
		
	/**
	 * <p>TITLE：获取8位数邀请码</p>
	 *
	 * @author: QW
	 * @version 1.0
	 * Created on 2018年10月25日
	 * 
	 * @param extendCode
	 * @return
	 */
	public static final String getCode(String extendCode) {
		if (StringUtils.isEmpty(extendCode)) {
			return null;
		}
		
		String [] code = {"1","2","3","4","5","6","7","8","9","0"};
		//第一位随机数
		int firstNumber = (int) ( Math.random () * 10 );
		String first = code[firstNumber];
		//最后一位随机数
		int lastNumber = (int) ( Math.random () * 10 );
		String last = code[lastNumber];	
		
		String between = "";
		if(Integer.parseInt(extendCode) < 999999) {	
			between = String.format("%0" + extendCode.length() + "d", Integer.parseInt(extendCode) + 1);;			
		} else {
			return null;
		}
		String newextendCode = first + between + last;
		return newextendCode;
	}
}
