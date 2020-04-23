package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Wiki;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import cn.com.service.admin.WikiService;
import cn.com.utils.StringUtils;
import cn.com.utils.UUIDUtil;
import com.alibaba.fastjson.JSONObject;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@RequestMapping("/pgzs/wiki")
public class WikiController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(WikiController.class);

    @Autowired
    private WikiService wikiService;

    @Autowired
    private DictService dictService;


    @PostMapping("list")
    @ResponseBody
    public ResultMap<Wiki> listData(HttpServletRequest request,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                    @RequestBody String params) {

        Wiki wiki = JSONObject.parseObject(params, Wiki.class);
        PageQuery<Wiki> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(wiki.getTitle())) {
            query.setPara("title",wiki.getTitle());
        }
        query = wikiService.findPage(query);
        ResultMap<Wiki> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }


    /**
     * 查看界面
     *
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/view")
    public JsonResult view(HttpServletRequest request, @RequestBody String params) {
        Wiki wiki1 = JSONObject.parseObject(params, Wiki.class);
        Wiki wiki = wikiService.findByIdOne(wiki1.getWikiId());

        //每次点击 +1
        String clicks = wiki.getClicks();
        Integer click = Integer.parseInt(clicks)+1;
        wiki.setClicks(click.toString());
        wikiService.update(wiki);
        Wiki result = wikiService.findById(wiki1.getWikiId());
        return JsonResult.success("操作成功", result);
    }


}
