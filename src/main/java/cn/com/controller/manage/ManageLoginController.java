package cn.com.controller.manage;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.common.message.JsonResult;
import cn.com.entity.admin.Admin;

/**
 * 登录界面
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Controller
@RequestMapping("/manage")
public class ManageLoginController {

    private Logger logger = LoggerFactory.getLogger(ManageLoginController.class);
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "manage/login";
    }
    
    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public JsonResult submit(HttpServletRequest request, Admin admin, Model model) {

        if (StringUtils.isEmpty(admin.getUserName())) {
            return JsonResult.error("请输入用户名", null);
        }
        if (StringUtils.isEmpty(StringUtils.isEmpty(admin.getPassword()))) {
            return JsonResult.error("请输入密码", null);
        }
        Subject subject = SecurityUtils.getSubject();
        String md5Pwd = new Md5Hash(admin.getPassword(), admin.getUserName()).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUserName(), admin.getPassword());
        try {
            subject.login(token);
            
            return JsonResult.success("登录成功", null);
        } catch (LockedAccountException lae) {
            token.clear();
            return JsonResult.error("用户已经被锁定不能登录，请与管理员联系！", null);
        } catch (DisabledAccountException e) {
            token.clear();
            return JsonResult.error("用户或密码不正确！", null);
        }catch (AuthenticationException e){
            token.clear();
            return JsonResult.error("用户或密码不正确！", null);
        }catch (Exception e) {
        	token.clear();
        	e.printStackTrace();
        	logger.equals(e.getMessage());
        	return JsonResult.error("系统异常，无法访问", null);
        }
    }
    
    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
    	Subject subject = SecurityUtils.getSubject();
    	subject.logout();
        return "redirect:/manage/login";
    }
}
