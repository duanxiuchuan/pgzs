package cn.com.controller.rest;

import cn.com.annotation.Log;
import cn.com.common.message.JsonResult;
import cn.com.common.redis.RedisService;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import com.alibaba.fastjson.JSONObject;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 字典
 *
 * @author LiDaDa
 */
@RestController
@CrossOrigin
@RequestMapping("/pgzs/dict")
public class RestDictController extends BaseController {
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
    @PostMapping("/listData")
    public ResultMap<Dict> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        PageQuery query = new PageQuery(page, limit);

        dictService.findAll(query);
        ResultMap<Dict> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    /**
     * 根据类型查询
     *
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/view")
    public JsonResult view(HttpServletRequest request,  @RequestBody String params) {
        Dict dict1 = JSONObject.parseObject(params, Dict.class);
        List<Dict> dictList = dictService.findByType(dict1.getType());
        return JsonResult.success("操作成功", dictList);
    }

}
