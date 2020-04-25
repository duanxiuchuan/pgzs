package cn.com.controller.manage;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Case;
import cn.com.entity.admin.Designer;
import cn.com.entity.admin.HeatAreas;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CaseService;
import cn.com.service.admin.DesignerService;
import cn.com.service.admin.DictService;
import cn.com.service.admin.HeatAreasService;
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
@RequestMapping("/manage/case")
public class ManageCaseController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private CaseService caseService;

    @Autowired
    private DesignerService designerService;

    @Autowired
    private HeatAreasService heatAreasService;

    @Autowired
    private DictService dictService;

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
    public String addPage(HttpServletRequest request) {
        //查询所有设计师
        List<Designer> designerList = designerService.findAllByStatus();
        //查询所有小区
        List<HeatAreas> heatAreasList = heatAreasService.findAllByStatus();
        //查询装修风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        //查询所有户型
        List<Dict> layoutList = dictService.findByType(DictConstantType.ADMIN_LAYOUT_TYPE);
        //查询所有面积
        List<Dict> areaList = dictService.findByType(DictConstantType.ADMIN_AREA_TYPE);
        request.setAttribute("heatAreasList",heatAreasList);
        request.setAttribute("designerList",designerList);
        request.setAttribute("layoutList",layoutList);
        request.setAttribute("styleList",styleList);
        request.setAttribute("areaList",areaList);
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
        //查询所有设计师
        if(!"".equals(aCase.getDesignerId()) && aCase.getDesignerId() != null){
            Designer designer = designerService.findById(aCase.getDesignerId());
            if(designer != null){
                aCase.setDesignerName(designer.getName());
            }
        }
        if(!"".equals(aCase.getAreasId()) && aCase.getAreasId() != null){
            HeatAreas areas = heatAreasService.findById(aCase.getAreasId());
            if(areas != null){
                aCase.setAreasId(areas.getName());
            }
        }
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
        //查询所有设计师
        List<Designer> designerList = designerService.findAllByStatus();
        //查询所有小区
        List<HeatAreas> heatAreasList = heatAreasService.findAllByStatus();
        //查询装修风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        //查询所有户型
        List<Dict> layoutList = dictService.findByType(DictConstantType.ADMIN_LAYOUT_TYPE);
        //查询所有面积
        List<Dict> areaList = dictService.findByType(DictConstantType.ADMIN_AREA_TYPE);
        request.setAttribute("heatAreasList",heatAreasList);
        request.setAttribute("designerList",designerList);
        request.setAttribute("layoutList",layoutList);
        request.setAttribute("styleList",styleList);
        request.setAttribute("areaList",areaList);
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
