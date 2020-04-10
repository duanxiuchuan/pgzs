package cn.com.controller.rest;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.ManageDeptController;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Customer;
import cn.com.entity.admin.Designer;
import cn.com.entity.admin.HeatAreas;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CustService;
import cn.com.service.admin.DesignerService;
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
import java.util.Date;
import java.util.List;

@RestController
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

}
