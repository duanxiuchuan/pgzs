/**
 * Copyright © 2017力雅软件. All rights reserved.
 *
 * @author: RM
 *
 * Created on 2017年9月21日
 */
package cn.com.utils;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

public class UserAgentUtils {
	
	public static String getHost(HttpServletRequest request) {
		return request.getHeader("Host");
	}
	
	/**
	 * 获取用户代理对象
	 * @param request
	 * @return
	 */
	public static UserAgent getUserAgent(HttpServletRequest request){
		return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	}
	
	/**
	 * 获取设备类型
	 * @param request
	 * @return
	 */
	public static DeviceType getDeviceType(HttpServletRequest request){
		return getUserAgent(request).getOperatingSystem().getDeviceType();
	}
	
	/**
	 * 是否是PC
	 * @param request
	 * @return
	 */
	public static boolean isComputer(HttpServletRequest request){
		return DeviceType.COMPUTER.equals(getDeviceType(request));
	}

	/**
	 * 是否是手机
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request){
		return DeviceType.MOBILE.equals(getDeviceType(request));
	}
	
	/**
	 * 是否是平板
	 * @param request
	 * @return
	 */
	public static boolean isTablet(HttpServletRequest request){
		return DeviceType.TABLET.equals(getDeviceType(request));
	}

	/**
	 * 是否是手机和平板
	 * @param request
	 * @return
	 */
	public static boolean isMobileOrTablet(HttpServletRequest request){
		DeviceType deviceType = getDeviceType(request);
		return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
	}
	
	/**
	 * 是否是微信内置浏览器
	 * @param request
	 * @return
	 */
	public static boolean isWechat(HttpServletRequest request) {
		String agent = request.getHeader("user-agent");
		if (agent.toLowerCase().indexOf("micromessenger") > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取浏览类型
	 * @param request
	 * @return
	 */
	public static Browser getBrowser(HttpServletRequest request){
		return getUserAgent(request).getBrowser();
	}
	
	/**
	 * 是否IE版本是否小于等于IE8
	 * @param request
	 * @return
	 */
	public static boolean isLteIE8(HttpServletRequest request){
		Browser browser = getBrowser(request);
		return Browser.IE5.equals(browser) || Browser.IE6.equals(browser)
				|| Browser.IE7.equals(browser) || Browser.IE8.equals(browser);
	}
}
