package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Style;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import cn.com.service.admin.StyleService;
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
@RequestMapping("/pgzs/style")
public class StyleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(StyleController.class);

    @Autowired
    private StyleService styleService;

    @Autowired
    private DictService dictService;

    @PostMapping("list")
    public ResultMap<Style> listData(HttpServletRequest request,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                         @RequestBody String params) {

        Style style = JSONObject.parseObject(params, Style.class);
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



    /**
     * 查看界面
     *
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/view")
    public JsonResult view(HttpServletRequest request, @RequestBody String params) {

        Style style = JSONObject.parseObject(params, Style.class);

        Style style2 = styleService.findById(style.getStyleId());
        return JsonResult.success("操作成功", style2);
    }

}
