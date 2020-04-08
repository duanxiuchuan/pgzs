package cn.com.controller.manage;

import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Case;
import cn.com.entity.admin.Designer;
import cn.com.entity.admin.HeatAreas;
import cn.com.service.admin.CaseService;
import cn.com.service.admin.DesignerService;
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

@Controller
@RequestMapping("/manage/heat")
public class ManageHeatAreasController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private HeatAreasService heatAreasService;

    @RequestMapping("list")
    public String list() {

        return "manage/heat/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<HeatAreas> listData(HttpServletRequest request,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                         HeatAreas heatAreas) {

        PageQuery<HeatAreas> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(heatAreas.getTitle())) {
            query.setPara("title",heatAreas.getTitle());
        }
        if (StringUtils.isNotEmpty(heatAreas.getName())) {
            query.setPara("name",heatAreas.getName());
        }
        query = heatAreasService.findPage(query);
        ResultMap<HeatAreas> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage() {
        //查询所有设计师

        return "manage/heat/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, HeatAreas heatAreas) {
        try {
            heatAreas.setAreasId(UUIDUtil.uuid());
            heatAreas.setStatus("1");
            heatAreasService.add(heatAreas);
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
     * @param areasId
     * @return
     */
    @RequestMapping("/view/{areasId}")
    public String view(HttpServletRequest request, @PathVariable("areasId") String areasId) {
        HeatAreas heatAreas = heatAreasService.findById(areasId);
        request.setAttribute("heat",heatAreas);
        return "manage/heat/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param areasId
     * @return
     */
    @RequestMapping("/editPage/{areasId}")
    public String editPage(HttpServletRequest request, @PathVariable("areasId") String areasId) {
        HeatAreas heatAreas = heatAreasService.findById(areasId);
        request.setAttribute("heat",heatAreas);
        return "manage/heat/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, HeatAreas heatAreas) {
        try {
            heatAreasService.update(heatAreas);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, HeatAreas heatAreas) {
        try {
            heatAreasService.deleteByAreasId(heatAreas.getAreasId());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
