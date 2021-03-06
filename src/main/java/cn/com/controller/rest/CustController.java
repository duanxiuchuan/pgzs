package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.ManageDeptController;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.*;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CustService;
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
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@Api(tags = "客户管理相关Api",value = "客户管理相关Api")
@RequestMapping("/pgzs/cust")
public class CustController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ManageDeptController.class);

    @Autowired
    private CustService custService;

    @PostMapping("save")
    public JsonResult save(HttpServletRequest request,@RequestBody String params) {
        try {
            Customer customer = JSONObject.parseObject(params, Customer.class);
            // 判断用户名不能重复，并且不能重复
            if (StringUtils.isEmpty(customer.getName())) {
                return JsonResult.error("客户户名不能为空", null);
            }
            if (StringUtils.isEmpty(customer.getPhone())) {
                return JsonResult.error("客户电话不能为空", null);
            }else {
                Customer customer2 = custService.findByphone(customer.getPhone());
                if (customer2 != null) {
                    return JsonResult.error("该客户已经提交过信息，请勿重复提交！", null);
                }
            }
            customer.setCustId(UUIDUtil.uuid());
            customer.setCreateTime(new Date());
            customer.setStatus("1");
            custService.add(customer);
            return JsonResult.success("提交成功，我们将尽快与您联系！", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @PostMapping("list")
    @ResponseBody
    @ApiOperation(value = "客户管理",notes ="客户管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "客户名称",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "当前页",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "每页条数",dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "电话号码",dataType = "String")
    })
    public ResultMap<Customer> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                    @RequestBody String params) {

        Customer customer = JSONObject.parseObject(params, Customer.class);
        PageQuery<Customer> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(customer.getName())) {
            query.setPara("name", customer.getName());
        }
        if (StringUtils.isNotEmpty(customer.getPhone())) {
            query.setPara("phone", customer.getPhone());
        }
        query = custService.findPage(query);
        ResultMap<Customer> resultMap = new ResultMap<>(query.getList(), query.getTotalRow(),page,limit);
        return resultMap;
    }

}
