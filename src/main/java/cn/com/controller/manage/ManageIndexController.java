package cn.com.controller.manage;

import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Resources;
import cn.com.service.admin.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LiDaDa
 */
@Controller
@RequestMapping("/manage")
public class ManageIndexController extends BaseController {

    @Autowired
    private ResourcesService resourcesService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        Admin admin = adminService.getCurrent();
        if(admin == null){
        	return "redirect:login";
        }
        List<Resources> topResources = resourcesService.findTopMenu(admin.getId());
        List<Resources> secordResources = resourcesService.findMyResources(admin.getId());
		request.setAttribute("admin", admin);
		request.setAttribute("topResources", topResources);
		request.setAttribute("secordResources", secordResources);
        return "manage/index";
    }

    @RequestMapping("/main")
    public String welcome() {
        return "manage/welcome";
    }

}
