package cn.com.controller.manage.base;

import cn.com.annotation.Log;
import cn.com.common.message.JsonResult;
import cn.com.common.redis.RedisService;
import cn.com.common.result.ResultMap;
import cn.com.entity.admin.Admin;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource
    protected RedisService redisService;

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
            //查询类型与值 是否已经占用
            Dict disct2 = dictService.findByTypeAndValue(dict.getType(),dict.getValue());
            if(disct2 != null){
                return super.messageResult(i18nMessage.getMessage("admin.common.systemMsg"), false);
            }
            Long id = dictService.saveModel(dict);
            if (id > 0L) {
                redisService.remove(dict.getType());
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

    /**
     * 查看界面
     *
     * @param request
     * @param dictId
     * @return
     */
    @RequestMapping("/view/{dictId}")
    public String view(HttpServletRequest request, @PathVariable("dictId") String dictId) {
        Dict dict = dictService.findById(dictId);
        request.setAttribute("dict",dict);
        return "manage/dict/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param dictId
     * @return
     */
    @RequestMapping("/editPage/{dictId}")
    public String editPage(HttpServletRequest request, @PathVariable("dictId") String dictId) {
        Dict dict = dictService.findById(dictId);
        request.setAttribute("dict",dict);
        return "manage/dict/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Dict dict) {
        try {
            dictService.update(dict);
            redisService.remove(dict.getType());
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request,Dict dict) {
        try {
            int flag = dictService.deleteByDictId(dict.getDictId());
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
