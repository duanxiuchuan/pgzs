package cn.com.controller.manage;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.*;
import cn.com.entity.base.Dict;
import cn.com.service.admin.*;
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
@RequestMapping("/manage/style")
public class ManageStyleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageStyleController.class);

    @Autowired
    private StyleService styleService;

    @Autowired
    private DictService dictService;
    @RequestMapping("list")
    public String list() {

        return "manage/style/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<Style> listData(HttpServletRequest request,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                         Style style) {

        PageQuery<Style> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(style.getTitle())) {
            query.setPara("title",style.getTitle());
        }
        if (StringUtils.isNotEmpty(style.getStyle())) {
            query.setPara("style",style.getStyle());
        }
        if (StringUtils.isNotEmpty(style.getSpace())) {
            query.setPara("space",style.getSpace());
        }
        query = styleService.findPage(query);
        ResultMap<Style> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage(HttpServletRequest request) {
        //查询设计空间
        List<Dict> spaceList = dictService.findByType(DictConstantType.ADMIN_SPACE_TYPE);
        //查询设计功能
        List<Dict> fucaList = dictService.findByType(DictConstantType.ADMIN_FUCA_TYPE);
        //查询设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        request.setAttribute("spaceList",spaceList);
        request.setAttribute("fucaList",fucaList);
        request.setAttribute("styleList",styleList);
        return "manage/style/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Style style) {
        try {
            style.setStyleId(UUIDUtil.uuid());
            style.setStatus("1");
            styleService.add(style);
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
     * @param styleId
     * @return
     */
    @RequestMapping("/view/{styleId}")
    public String view(HttpServletRequest request, @PathVariable("styleId") String styleId) {
        //查询设计空间
        List<Dict> spaceList = dictService.findByType(DictConstantType.ADMIN_SPACE_TYPE);
        //查询设计功能
        List<Dict> fucaList = dictService.findByType(DictConstantType.ADMIN_FUCA_TYPE);
        //查询设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        request.setAttribute("spaceList",spaceList);
        request.setAttribute("fucaList",fucaList);
        request.setAttribute("styleList",styleList);
        Style style = styleService.findById(styleId);
        request.setAttribute("style",style);
        return "manage/style/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param styleId
     * @return
     */
    @RequestMapping("/editPage/{styleId}")
    public String editPage(HttpServletRequest request, @PathVariable("styleId") String styleId) {
        //查询设计空间
        List<Dict> spaceList = dictService.findByType(DictConstantType.ADMIN_SPACE_TYPE);
        //查询设计功能
        List<Dict> fucaList = dictService.findByType(DictConstantType.ADMIN_FUCA_TYPE);
        //查询设计风格
        List<Dict> styleList = dictService.findByType(DictConstantType.ADMIN_STYLE_TYPE);
        request.setAttribute("spaceList",spaceList);
        request.setAttribute("fucaList",fucaList);
        request.setAttribute("styleList",styleList);
        Style style = styleService.findById(styleId);
        request.setAttribute("style",style);
        return "manage/style/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Style style) {
        try {
            styleService.update(style);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Style style) {
        try {
            styleService.deleteByStyleId(style.getStyleId());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
