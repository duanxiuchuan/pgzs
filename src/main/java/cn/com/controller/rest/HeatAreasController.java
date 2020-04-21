package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.ManageCaseController;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.HeatAreas;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import cn.com.service.admin.HeatAreasService;
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
@CrossOrigin
@RequestMapping("/pgzs/heat")
public class HeatAreasController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private HeatAreasService heatAreasService;

    @Autowired
    private DictService dictService;



    @PostMapping("list")
    @ResponseBody
    public ResultMap<HeatAreas> listData(HttpServletRequest request,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                         @RequestBody String params) {

        HeatAreas heatAreas = JSONObject.parseObject(params, HeatAreas.class);
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


    /**
     * 查看界面
     *
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/view")
    public JsonResult view(HttpServletRequest request, @RequestBody String params) {
        //查询地区
        HeatAreas heatAreas = JSONObject.parseObject(params, HeatAreas.class);
        HeatAreas heatAreas2 = heatAreasService.findById(heatAreas.getAreasId());
        return JsonResult.success("操作成功", heatAreas2);
    }


}
