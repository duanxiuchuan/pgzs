package cn.com.controller.manage;

import cn.com.common.message.JsonResult;
import cn.com.common.shiro.ShiroService;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Resources;
import cn.com.service.admin.ResourcesService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 资源权限
 * 
 * @author LiDaDa
 */
@Controller
@RequestMapping("/manage/res")
public class ManageResourcesController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ManageResourcesController.class);

	@Autowired
	private ResourcesService resourcesService;
    @Autowired
    private ShiroService shiroService;

	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		List<Resources> topResources = resourcesService.findMenu(Resources.Type.zero.ordinal());
		List<Resources> secordResources = resourcesService.findMenu(Resources.Type.one.ordinal());
		List<Resources> lastResources = resourcesService.findMenu(Resources.Type.two.ordinal());

		request.setAttribute("topResources", topResources);
		request.setAttribute("secordResources", secordResources);
		request.setAttribute("lastResources", lastResources);
		return "manage/resources/list";
	}



	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			List<Resources> resources = resourcesService.findByParentId(id);
			if (CollectionUtils.isEmpty(resources)) {
				resourcesService.deleteById(id);
				shiroService.updatePermission();
				return JsonResult.success("删除成功", null);
			} else {
				return JsonResult.error("删除失败，此目录下存在目录", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return JsonResult.error("系统异常", null);
		}
	}

	/**
	 * 添加顶级分类
	 */
	@RequestMapping("/addcategory")
	public String addCategory(HttpServletRequest request, String name, String url) {
		// 获取当前操作的管理员
		Object object = SecurityUtils.getSubject().getPrincipal();
		Admin admin = new Admin();
		if (object instanceof Admin) {
			admin = (Admin) object;
		} else {
			admin = JSON.parseObject(JSON.toJSON(object).toString(), Admin.class);
		}

		Resources resources = new Resources();
		resources.setUrl(url);
		// 设置传递过来的值
		resources.setName(name);
		// 设置创建时间
		resources.setCreateDate(new Date());
		// 设置创建人id
		resources.setCreator(admin.getId());
		// 设置修改人id
		resources.setModifier(admin.getId());
		// 是否启用
		resources.setIsEnable(1);
		// 修改时间
		resources.setModifyDate(new Date());
		// 父级id
		resources.setParentId(0L);
		// 类型
		resources.setType(0);
		// 版本
		resources.setVersion(0L);
		// 设置排序
		resources.setSort(0);

		// 数据持久化
		resourcesService.addCategory(resources);
		shiroService.updatePermission();
		
		return "redirect:list";
	}
	
	@RequestMapping("/show/{id}")
	public String show(@PathVariable String id, HttpServletRequest request) {
		if (id.lastIndexOf("e") != -1) {
			id = id.replace("e", "");
			Resources resources = resourcesService.findById(id);
			request.setAttribute("res", resources);
			return "manage/resources/resourceEdit";
		}
		Resources resources = resourcesService.findById(id);
		request.setAttribute("res", resources);
		return "manage/resources/resourceAdd";
	}

	/**
	 * 添加功能
	 * 如果enable为null 表明启用
	 */
	@RequestMapping("/addsubord")
	@ResponseBody
	public void addsubord(String enable, HttpServletRequest request, Resources res) {
		// 获取当前操作的管理员
		Admin admin = adminService.getCurrent();
		if (enable != null) {
			res.setIsEnable(1);
		} else {
			res.setIsEnable(0);
		}
		res.setType(res.getType() + 1);
		res.setCreateDate(new Date());
		res.setCreator(admin.getId());
		res.setModifyDate(new Date());
		res.setModifier(admin.getId());
		res.setSort(1);
		res.setVersion(0L);
		resourcesService.addCategory(res);
		shiroService.updatePermission();
	}

	/**
	 * 修改功能
	 * 
	 */
	@RequestMapping("/editCate")
	@ResponseBody
	public void editCate(String enable, HttpServletRequest request, Resources res) {
		// 获取当前操作的管理员
		Admin admin = adminService.getCurrent();
		if (enable != null) {
			res.setIsEnable(1);
		} else {
			res.setIsEnable(0);
		}

		res.setType(res.getType());
		res.setModifyDate(new Date());
		res.setModifier(admin.getId());
		resourcesService.editCategory(res);
		shiroService.updatePermission();
	}
	
	@RequestMapping("/open")
	public String open() {
		return "manage/resources/add";
	}
}
