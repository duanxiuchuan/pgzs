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
        if (StringUtils.isNotEmpty(cass.getAreasName())) {
            query.setPara("areasName",cass.getAreasName());
        }
        if (StringUtils.isNotEmpty(cass.getStyle())) {
            query.setPara("style", cass.getStyle());
        }
        query = caseService.findPage(query);
        ResultMap<Case> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage() {
    //查询所有设计师

        return "manage/case/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Case cass) {
        try {
            cass.setCaseId(UUIDUtil.uuid());
            cass.setStatus("1");
            caseService.add(cass);
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
     * @param caseId
     * @return
     */
    @RequestMapping("/view/{caseId}")
    public String view(HttpServletRequest request, @PathVariable("caseId") String caseId) {
        Case aCase = caseService.findById(caseId);
        request.setAttribute("case",aCase);
        return "manage/case/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param caseId
     * @return
     */
    @RequestMapping("/editPage/{caseId}")
    public String editPage(HttpServletRequest request, @PathVariable("caseId") String caseId) {
        Case aCase = caseService.findById(caseId);
        request.setAttribute("case",aCase);
        return "manage/case/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Case cass) {
        try {
            caseService.update(cass);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Case cass) {
        try {
            caseService.deleteByCaseId(cass.getCaseId());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
