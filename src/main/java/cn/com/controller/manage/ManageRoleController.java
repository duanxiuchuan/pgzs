package cn.com.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.common.message.JsonResult;
import cn.com.common.shiro.MyShiroRealm;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Resources;
import cn.com.entity.admin.Role;
import cn.com.entity.admin.RoleResources;
import cn.com.service.admin.AdminRoleService;
import cn.com.service.admin.ResourcesService;
import cn.com.service.admin.RoleResourcesService;
import cn.com.service.admin.RoleService;

/**
 * 角色管理
 * @author QW
 */
@Controller
@RequestMapping("/manage/role")
public class ManageRoleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageRoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RoleResourcesService roleResourcesService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private MyShiroRealm myShiroRealm;

    /**
     * 保存
     *
     * @param name
     * @return
     */
     @RequestMapping(value = "save", method = RequestMethod.POST)
     @ResponseBody
     public JsonResult save(String name) {
        try {
            Role role = new Role();
            role.setName(name);
            role.setIsBuiltIn(1);
            role = roleService.save(role, adminService.getCurrent());
            roleService.save(role);
            return JsonResult.success("保存成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 修改
     *
     * @param id
     * @param name
     * @return
     */
     @RequestMapping(value = "update", method = RequestMethod.POST)
     @ResponseBody
     public JsonResult update(Long id, String name) {
        try {
            Role role = roleService.findById(id);
            role.setName(name);
            role = roleService.update(role, adminService.getCurrent());
            roleService.update(role);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(Long roleId) {
        try {
            List<RoleResources> resources = roleResourcesService.findByRoleId(roleId);
            if (CollectionUtils.isEmpty(resources)) {
                roleService.deleteById(roleId);
                return JsonResult.success("删除成功", null);
            } else {
                return JsonResult.error("删除失败，此角色下包含权限", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("setRoleResources")
    public String setRoleResources(HttpServletRequest request, @RequestParam(name = "roleId", required = false, defaultValue = "10") Long roleId) {
        List<Role> roles = roleService.findAll();
        request.setAttribute("roles", roles);
        return "manage/role/editRoleResources";
    }

    @RequestMapping(value = "loadRoleResources", method = RequestMethod.GET)
    public String loadRoleResources(HttpServletRequest request, @RequestParam(name = "roleId", required = false, defaultValue = "10") Long roleId) {
        List<Resources> resources = resourcesService.findByRoleId(roleId);
        request.setAttribute("resources", resources);
        request.setAttribute("role", roleService.findById(roleId));
        return "manage/role/loadRoleResources";
    }

    @RequestMapping(value = "saveRoleResourcs", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveRoleResourcs(Long roleId, Long resourcesId, boolean isSave) {
        try {
            RoleResources roleResources = new RoleResources();
            if (isSave) {
                roleResources.setResId(resourcesId);
                roleResources.setRoleId(roleId);
                roleResourcesService.save(roleResources);
                // 通过角色id查找用户
                List<Long> adminIds = adminRoleService.findByRoleId(roleId);
                myShiroRealm.clearUserAuthByUserId(adminIds);
                return JsonResult.success("启用权限成功", null);
            } else {
                roleResourcesService.delete(resourcesId, roleId);
                // 通过角色id查找用户
                List<Long> adminIds = adminRoleService.findByRoleId(roleId);
                myShiroRealm.clearUserAuthByUserId(adminIds);
                return JsonResult.success("禁用权限成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.error("系统异常", null);
        }
    }

}
