package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
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
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "经典案例相关Api",value = "经典案例相关Api")
@RequestMapping("/pgzs/case")
public class CaseController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;

    @Autowired
    private DesignerService designerService;

    @Autowired
    private HeatAreasService heatAreasService;

    @Autowired
    private DictService dictService;

    @PostMapping("list")
    @ResponseBody
    @ApiOperation(value = "经典案例",notes ="经典案例列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area",value = "装修面积",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "当前页",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "每页条数",dataType = "String"),
            @ApiImplicitParam(name = "style",value = "风格",dataType = "String"),
            @ApiImplicitParam(name = "layout",value = "户型",dataType = "String")
    })
    public ResultMap<Case> listData(HttpServletRequest request,
                                        @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                        @RequestBody String params) {

        Case cass = JSONObject.parseObject(params, Case.class);
        PageQuery<Case> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(cass.getAreasName())) {
            query.setPara("area",cass.getAreasName());
        }
        if (StringUtils.isNotEmpty(cass.getStyle())) {
            query.setPara("style", cass.getStyle());
        }
        if (StringUtils.isNotEmpty(cass.getStyle())) {
            query.setPara("layout", cass.getLayout());
        }
        query = caseService.findPage(query);
        ResultMap<Case> resultMap = new ResultMap<>(query.getList(), query.getTotalRow(),page,limit);
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
    @ApiOperation(value = "经典案例",notes ="经典案例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "caseId",value = "案例id",dataType = "String")
    })
    public JsonResult view(HttpServletRequest request, @RequestBody String params) {
        Case cass = JSONObject.parseObject(params, Case.class);
        Case aCase = caseService.findById(cass.getCaseId());
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
                aCase.setAreasName(areas.getName());
            }
        }
        return JsonResult.success("操作成功", aCase);
    }

}
