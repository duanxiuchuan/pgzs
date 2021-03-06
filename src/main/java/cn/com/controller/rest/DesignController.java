package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.ManageCaseController;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Case;
import cn.com.entity.admin.Designer;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CaseService;
import cn.com.service.admin.DesignerService;
import cn.com.service.admin.DictService;
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
@Api(tags = "金牌设计相关Api",value = "金牌设计相关Api")
@RequestMapping("/pgzs/design")
public class DesignController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ManageCaseController.class);

    @Autowired
    private DesignerService designerService;

    @Autowired
    private DictService dictService;

    @Autowired
    private CaseService caseService;


    @PostMapping("list")
    @ResponseBody
    @ApiOperation(value = "金牌设计",notes ="金牌设计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "设计师名称",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "当前页",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "每页条数",dataType = "String"),
            @ApiImplicitParam(name = "type",value = "设计师类型",dataType = "String")
    })
    public ResultMap<Designer> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                        @RequestBody String params) {

        Designer designer = JSONObject.parseObject(params, Designer.class);
        PageQuery<Designer> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(designer.getName())) {
            query.setPara("name",designer.getName());
        }
        if (StringUtils.isNotEmpty(designer.getType())) {
            query.setPara("type", designer.getType());
        }
        query = designerService.findPage(query);
        ResultMap<Designer> resultMap = new ResultMap<>(query.getList(), query.getTotalRow(),page,limit);
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
    @ApiOperation(value = "设计师详情",notes ="设计师详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "designerId",value = "设计师ID",dataType = "String")
    })
    public JsonResult view(HttpServletRequest request, @RequestBody String params) {
        Designer desig = JSONObject.parseObject(params, Designer.class);

        Designer designer = designerService.findById(desig.getDesignerId());
        return JsonResult.success("操作成功", designer);
    }


}
