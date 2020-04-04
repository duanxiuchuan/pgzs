package cn.com.common.session;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpSession;

/**
 * @author LiDaDa
 */
public class SessionApi {
	
//	public static final String PRINCIPAL_ATTRIBUTE_NAME = User.class.getName() + ".PRINCIPAL";
	/** 缓存名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = ".PRINCIPAL";

	public static LoginUser getCurrent(HttpSession session) {
		try {
			Object object = session.getAttribute(PRINCIPAL_ATTRIBUTE_NAME);
			if(object != null){
				LoginUser user = new LoginUser();
				if (object instanceof LoginUser) {
					user = (LoginUser) object;
				} else {
					user = JSON.parseObject(JSON.toJSON(object).toString(), LoginUser.class);
				}	
				return user;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * setCurrent:当前登录用户信息存入session. <br/>
	 * 
	 * @author qw
	 * @param session
	 * @param user
	 */
	public static void setCurrent(HttpSession session, LoginUser user) {
		session.setAttribute(PRINCIPAL_ATTRIBUTE_NAME, user);
	}

	/**
	 * <p>TITLE：清楚登录状态</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @param session
	 */
	public static void clear(HttpSession session) {
		session.removeAttribute(PRINCIPAL_ATTRIBUTE_NAME);
	}
}
