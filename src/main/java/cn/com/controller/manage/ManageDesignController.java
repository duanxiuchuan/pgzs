package cn.com.controller.manage;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Case;
import cn.com.entity.admin.Designer;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CaseService;
import cn.com.service.admin.DesignerService;
import cn.com.service.admin.DictService;
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
import java.util.List;

@Controller
@RequestMapping("/manage/design")
public class ManageDesignController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private DesignerService designerService;

    @Autowired
    private DictService dictService;

    @Autowired
    private CaseService caseService;

    @RequestMapping("list")
    public String list() {

        return "manage/designer/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<Designer> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                    Designer designer) {

        PageQuery<Designer> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(designer.getName())) {
            query.setPara("name",designer.getName());
        }
        if (StringUtils.isNotEmpty(designer.getType())) {
            query.setPara("type", designer.getType());
        }
        query = designerService.findPage(query);
        ResultMap<Designer> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage(HttpServletRequest request) {
        //查询所有设计师类型
        List<Dict> designerList = dictService.findByType(DictConstantType.ADMIN_DESIGENR_TYPE);
        //查询所有设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        //查询所有案例
        List<Case> caseListList = caseService.findAllByStatus();
        request.setAttribute("designerList",designerList);
        request.setAttribute("styleList",styleList);
        request.setAttribute("caseListList",caseListList);
        return "manage/designer/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Designer designer) {
        try {
           String[] styles = request.getParameterValues("style");
            designer.setDesignerId(UUIDUtil.uuid());
            designer.setStatus("1");
            designerService.add(designer);
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
     * @param designerId
     * @return
     */
    @RequestMapping("/view/{designerId}")
    public String view(HttpServletRequest request, @PathVariable("designerId") String designerId) {
       //查询所有设计师类型
        List<Dict> designerList = dictService.findByType(DictConstantType.ADMIN_DESIGENR_TYPE);
        //查询所有设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        //查询所有案例
        List<Case> caseListList = caseService.findAllByStatus();
        request.setAttribute("caseListList",caseListList);
        request.setAttribute("designerList",designerList);
        request.setAttribute("styleList",styleList);
        Designer designer = designerService.findByIdOne(designerId);
        //每次查询 点击率+1
        String clicks = designer.getClicks();
        Integer click = Integer.parseInt(clicks)+1;
        designer.setClicks(click.toString());
        designerService.update(designer);
        Designer result = designerService.findById(designerId);
        request.setAttribute("designer",result);
        return "manage/designer/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param designerId
     * @return
     */
    @RequestMapping("/editPage/{designerId}")
    public String editPage(HttpServletRequest request, @PathVariable("designerId") String designerId) {
        //查询所有设计师类型
        List<Dict> designerList = dictService.findByType(DictConstantType.ADMIN_DESIGENR_TYPE);
        //查询所有设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        //查询所有案例
        List<Case> caseListList = caseService.findAllByStatus();
        request.setAttribute("designerList",designerList);
        request.setAttribute("caseListList",caseListList);
        request.setAttribute("styleList",styleList);
        Designer designer= designerService.findById(designerId);
        request.setAttribute("designer",designer);
        return "manage/designer/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Designer designer) {
        try {
            designerService.update(designer);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Designer designer) {
        try {
            designerService.deleteByDesignerId(designer.getDesignerId());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
