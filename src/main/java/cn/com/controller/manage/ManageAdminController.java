package cn.com.controller.manage;

import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.AdminRole;
import cn.com.entity.admin.Role;
import cn.com.service.admin.AdminRoleService;
import cn.com.service.admin.RoleService;
import cn.com.utils.PasswordHelper;
import cn.com.utils.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.CollectionUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员
 *
 * @author LiDaDa
 */
@Controller
@RequestMapping("/manage/manage")
public class ManageAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ManageAdminController.class);

    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private RoleService roleService;
    
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Long deptId) {
        request.setAttribute("deptId", deptId);
        return "manage/manage/list";
    }

    @ResponseBody
    @RequestMapping("/listData")
    public ResultMap<Admin> listData(HttpServletRequest request,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                     Admin admin) {
        Long deptId = Long.valueOf(request.getParameter("deptId"));
        PageQuery<Admin> query = new PageQuery<>(page, limit);
        if (deptId > 0L) {
            query.setPara("deptId", deptId);
        }
        if (StringUtils.isNotEmpty(admin.getUserName())) {
            query.setPara("userName", admin.getUserName());
        }
        if (StringUtils.isNotEmpty(admin.getPhone())) {
            query.setPara("phone", admin.getPhone());
        }
        query = adminService.findPage(query);
        ResultMap<Admin> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    /**
     * 打开新增界面
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        List<Role> roles = roleService.findByBuilt();
        String deptId = request.getParameter("deptId");
        request.setAttribute("deptId", deptId);
        request.setAttribute("roles", roles);
        return "manage/manage/add";
    }

    /**
     * 新增用户信息
     *
     * @param request
     * @param admin
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    @Transactional
    public JsonResult save(HttpServletRequest request, Admin admin) {
        try {
            Admin current = adminService.getCurrent();
            admin = adminService.save(admin, current);
            new PasswordHelper().encryptPassword(admin);
            // 判断用户名不能重复，并且不能重复
            if (StringUtils.isEmpty(admin.getUserName())) {
                return JsonResult.error("用户名不能为空", null);
            } else {
                Admin adminUserName = adminService.findByUserName(admin.getUserName());
                if (adminUserName != null) {
                    return JsonResult.error("用户名不能重复", null);
                }
            }
            admin.setIsBuiltIn(Admin.BuiltIn.no.ordinal());
            Long adminId = adminService.add(admin);
            String roles = request.getParameter("roles");
            String[] hasRoles = roles.split(",");
            // 保存用户和角色
            for (String str : hasRoles) {
                if (StringUtils.isNotEmpty(str)) {
                    AdminRole adminRole = new AdminRole();
                    adminRole.setAdminId(adminId);
                    adminRole.setRoleId(Long.valueOf(str));
                    adminRoleService.save(adminRole);
                }
            }

            return JsonResult.success("保存成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 修改界面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editPage/{id}")
    public String editPage(HttpServletRequest request, @PathVariable("id") Long id) {
        Admin admin = adminService.findById(id);
        List<Role> roles = roleService.findByAdminId(id);
        request.setAttribute("admin", admin);
        request.setAttribute("roles", roles);
        return "manage/manage/edit";
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @param admin
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Admin admin) {
        try {
            Admin current = adminService.getCurrent();
            admin = adminService.update(admin, current);
            adminService.update(admin);
            String roles = request.getParameter("roles");
            String[] hasRoles = roles.split(",");
            /**
             *  保存用户和角色
             *  1、保存前先删除
             *  2、保存
             */
            adminRoleService.deleteByAdminId(admin.getId());
            for (String str : hasRoles) {
                if (StringUtils.isNotEmpty(str)) {
                    AdminRole adminRole = new AdminRole();
                    adminRole.setAdminId(admin.getId());
                    adminRole.setRoleId(Long.valueOf(str));
                    adminRoleService.save(adminRole);
                }
            }
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 删除功能
     *
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            List<Long> roleIds = adminRoleService.findByAdminId(Long.valueOf(id));
            if (!CollectionUtils.isEmpty(roleIds)) {
                return JsonResult.error("账号已分配角色，不能删除", null);
            }
            int flag = adminService.deleteById(id);

            if (flag > 0) {
                return JsonResult.success("删除成功", null);
            } else {
                return JsonResult.error("删除失败", null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 查看界面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/view/{id}")
    public String view(HttpServletRequest request, @PathVariable("id") Long id) {
        Admin admin = adminService.findById(id);
        request.setAttribute("admin", admin);
        return "manage/manage/view";
    }
    

	/**
	 * 个人信息页面
	 */
    @RequestMapping("/personalInfo")
	public String personalInfo(HttpServletRequest request){
		 Admin currentAdmin = adminService.getCurrent();
		 if(currentAdmin!=null){
			 request.setAttribute("admin", currentAdmin);
		 }
		 return "manage/manage/myInfo";
	}
	
	/**
	 * 修改密码页面
	 */
    @RequestMapping("/updatePasswordPage")
	public String updatePasswordPage() {
		return "manage/manage/updatePassword";
	}
    
    /**
	 * 修改密码
	 */
    @RequestMapping("/updatePassword")
    @ResponseBody
	public JsonResult updatePassword(HttpServletRequest request) {
		String newPassword = request.getParameter("password");
		Admin current = adminService.getCurrent();
		try {
			newPassword = new Md5Hash(newPassword, current.getUserName()).toHex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Admin admin = new Admin();
		admin.setId(current.getId());
		admin.setPassword(newPassword);
        int flag = adminService.update(admin);
		if (flag > 0) {
			return JsonResult.success("修改成功，请重新登录", null);
		} else {
			return JsonResult.error("系统异常", null);
		}
	}
	
	/**
	 * 验证密码
	 */
    @RequestMapping("/getAjaxPassWord")
    @ResponseBody
	public JsonResult getAjaxPassWord(HttpServletRequest request) {
		Admin sysAdmin = adminService.getCurrent();
		if (sysAdmin == null) {
			return JsonResult.error("请重新登录", null);
		}
		String oldPsw = request.getParameter("oldPsw");
		try {
			oldPsw = new Md5Hash(oldPsw, sysAdmin.getUserName()).toHex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sysAdmin.getPassword().equals(oldPsw)) {
			return JsonResult.success("核验成功", null);
		} else {
			return JsonResult.error("", null);
		}
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(sysAdmin.getUserName(), oldPsw);
//        try {
//            subject.login(token);
//            return JsonResult.success("核验成功", null);
//            
//        } catch (LockedAccountException lae) {
//            token.clear();
//            return JsonResult.error("用户已经被锁定不能登录，请与管理员联系！", null);
//        } catch (AuthenticationException e) {
//            token.clear();
//            return JsonResult.error("系统异常", null);
//        }
	}
	
}
