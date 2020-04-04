package cn.com.controller;

import cn.com.common.session.LoginUser;
import cn.com.common.session.SessionApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiDaDa
 */
@Controller
@RequestMapping("")
public class UserIndexController {
	private Logger logger = LoggerFactory.getLogger(UserIndexController.class);

	/**
	 * 首页
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		try {
			// 是否已经登录
			LoginUser user = SessionApi.getCurrent(request.getSession());
			if (user != null) {
				request.setAttribute("user", user);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "manage/login";
	}






}
