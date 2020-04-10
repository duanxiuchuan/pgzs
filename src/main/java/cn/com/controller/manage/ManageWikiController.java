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
@RequestMapping("/manage/wiki")
public class ManageWikiController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageWikiController.class);

    @Autowired
    private WikiService wikiService;

    @Autowired
    private DictService dictService;

    @RequestMapping("list")
    public String list() {

        return "manage/wiki/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<Wiki> listData(HttpServletRequest request,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                    Wiki wiki) {

        PageQuery<Wiki> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(wiki.getTitle())) {
            query.setPara("title",wiki.getTitle());
        }
        query = wikiService.findPage(query);
        ResultMap<Wiki> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage(HttpServletRequest request) {
        //查询百科类型
        List<Dict> wikiList = dictService.findByType(DictConstantType.ADMIN_WIKI_TYPE);
        request.setAttribute("wikiList",wikiList);
        return "manage/wiki/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Wiki wiki) {
        try {
            wiki.setWikiId(UUIDUtil.uuid());
            wiki.setStatus("1");
            wikiService.add(wiki);
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
     * @param wikiId
     * @return
     */
    @RequestMapping("/view/{wikiId}")
    public String view(HttpServletRequest request, @PathVariable("wikiId") String wikiId) {
        //查询百科类型
        List<Dict> wikiList = dictService.findByType(DictConstantType.ADMIN_WIKI_TYPE);
        request.setAttribute("wikiList",wikiList);
        Wiki wiki = wikiService.findById(wikiId);
        request.setAttribute("wiki",wiki);
        return "manage/wiki/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param wikiId
     * @return
     */
    @RequestMapping("/editPage/{wikiId}")
    public String editPage(HttpServletRequest request, @PathVariable("wikiId") String wikiId) {
        //查询百科类型
        List<Dict> wikiList = dictService.findByType(DictConstantType.ADMIN_WIKI_TYPE);
        request.setAttribute("wikiList",wikiList);
        Wiki wiki = wikiService.findById(wikiId);
        request.setAttribute("wiki",wiki);
        return "manage/wiki/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Wiki wiki) {
        try {
            wikiService.update(wiki);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Wiki wiki) {
        try {
            wikiService.deleteByWikiId(wiki.getWikiId());
            return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
