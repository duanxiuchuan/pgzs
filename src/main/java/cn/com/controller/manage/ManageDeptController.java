package cn.com.controller.manage;

import cn.com.common.message.JsonResult;


import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Dept;
import cn.com.entity.admin.vo.DeptNode;
import cn.com.service.admin.DeptService;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author
 */
@Controller
@RequestMapping("/manage/dept")
public class ManageDeptController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ManageDeptController.class);

    @Autowired
    private DeptService deptService;

    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        Admin admin = adminService.getCurrent();
        request.setAttribute("deptId", admin.getDeptId());
        return "manage/dept/index";
    }

    @RequestMapping("loadDept")
    @ResponseBody
    public List<DeptNode> loadDept() {
        Admin admin = adminService.getCurrent();
        List<DeptNode> list = deptService.packageDeptsTree(admin);
        return list;
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request) {
        try {
            Admin admin = adminService.getCurrent();
            String parentId = request.getParameter("parentId");
            String name = request.getParameter("name");
            Dept dept = new Dept();
            dept.setName(name);
            dept.setParentId(Long.valueOf(parentId));
            deptService.save(dept, admin);
            deptService.save(dept);
            return JsonResult.success("保存成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Dept dept) {
        try {
            deptService.update(dept);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Dept dept) {
        try {
            // 判断部门下是否有人员分配
            List<Admin> admins = adminService.findByDeptId(dept.getId());
            if (CollectionUtils.isEmpty(admins)) {
                deptService.deleteById(dept.getId());
                return JsonResult.success("删除成功", null);
            } else {
                return JsonResult.error("此部门下存在员工，不能删除", null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }
}
