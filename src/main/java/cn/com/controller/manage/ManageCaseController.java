package cn.com.controller.manage;

import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Case;
import cn.com.service.admin.CaseService;
import cn.com.utils.StringUtils;
import cn.com.utils.UUIDUtil;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/manage/case")
public class ManageCaseController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private CaseService caseService;

    @RequestMapping("list")
    public String list() {

        return "manage/case/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<Case> listData(HttpServletRequest request,
                                        @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                        Case cass) {

        PageQuery<Case> query = new PageQuery<>(page, limit);
       /* if (StringUtils.isNotEmpty(Case.getName())) {
            query.setPara("name", Case.getName());
        }
        if (StringUtils.isNotEmpty(Case.getPhone())) {
            query.setPara("phone", Case.getPhone());
        }*/
        query = caseService.findPage(query);
        ResultMap<Case> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage() {

        return "manage/case/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Case Case) {
        try {
            Admin admin = adminService.getCurrent();
           /* // 判断用户名不能重复，并且不能重复
            if (StringUtils.isEmpty(Case.getName())) {
                return JsonResult.error("客户户名不能为空", null);
            }
            if (StringUtils.isEmpty(Case.getPhone())) {
                return JsonResult.error("客户电话不能为空", null);
            }else {
                Case Case2 = caseService.findByphone(Case.getPhone());
                if (Case2 != null) {
                    return JsonResult.error("该客户已经提交过信息，请勿重复提交！", null);
                }
            }*/
          /*  Case.setCustId(UUIDUtil.uuid());
            Case.setCreateTime(new Date());
            Case.setStatus("1");
            caseService.add(Case);*/
            return JsonResult.success("保存成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 查看界面
     *
     * @param request
     * @param phone
     * @return
     */
    @RequestMapping("/view/{phone}")
    public String view(HttpServletRequest request, @PathVariable("phone") String phone) {
        /*Case Case = caseService.findByphone(phone);
        request.setAttribute("cust", Case);*/
        return "manage/cust/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param phone
     * @return
     */
    @RequestMapping("/editPage/{phone}")
    public String editPage(HttpServletRequest request, @PathVariable("phone") String phone) {
       /* Case Case = caseService.findByphone(phone);
        request.setAttribute("cust", Case);*/
        return "manage/cust/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Case Case) {
        try {
//            caseService.update(Case);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Case Case) {
        try {
//            caseService.deleteByPhone(Case.getPhone());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
