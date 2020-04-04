package cn.com.controller.manage.base;

import cn.com.annotation.Log;
import cn.com.common.result.ResultMap;
import cn.com.entity.admin.Admin;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 字典
 *
 * @author LiDaDa
 */
@Controller
@RequestMapping("/manage/dict")
public class DictController extends BaseController {
    @Log
    private Logger logger;

    @Autowired
    private DictService dictService;


    @RequestMapping("list")
    public String list() {
        return "manage/dict/list";
    }

    @ResponseBody
    @RequestMapping("/listData")
    public ResultMap<Dict> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        PageQuery query = new PageQuery(page, limit);

        dictService.findAll(query);
        ResultMap<Dict> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    /**
     * 新增界面
     *
     * @return
     */
    @RequestMapping("addPage")
    public String addPage() {
        return "manage/dict/add";
    }

    /**
     * 保存
     *
     * @param request
     * @param dict
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, Dict dict) {
        try {
            Admin admin = adminService.getCurrent();
            dict = dictService.save(dict, admin);
            Long id = dictService.saveModel(dict);
            if (id > 0L) {
                return super.messageResult(i18nMessage.getMessage("admin.common.addSuccess"), true);
            } else {
                return super.messageResult(i18nMessage.getMessage("admin.common.addFail"), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            int flag = dictService.deleteById(id);
            if (flag > 0) {
                return super.messageResult(i18nMessage.getMessage("admin.common.deleteSuccess"), true);
            } else {
                return super.messageResult(i18nMessage.getMessage("admin.common.deleteFail"), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }
}
