package cn.com.controller.manage;

import cn.com.common.constant.DictConstantType;
import cn.com.common.message.JsonResult;
import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.*;
import cn.com.entity.base.Dict;
import cn.com.service.admin.CustService;
import cn.com.service.admin.DesignerService;
import cn.com.service.admin.DictService;
import cn.com.service.admin.HeatAreasService;
import cn.com.utils.StringUtils;
import cn.com.utils.UUIDUtil;
import jnr.ffi.Struct;
import org.apache.shiro.util.CollectionUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/cust")
public class ManageCustController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ManageDeptController.class);

    @Autowired
    private CustService custService;

    @Autowired
    private DictService dictService;

    @Autowired
    private DesignerService designerService;

    @Autowired
    private HeatAreasService heatAreasService;

    @RequestMapping("list")
    public String list() {

        return "manage/cust/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public ResultMap<Customer> listData(HttpServletRequest request,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                     Customer customer) {

        PageQuery<Customer> query = new PageQuery<>(page, limit);
        if (StringUtils.isNotEmpty(customer.getName())) {
            query.setPara("name", customer.getName());
        }
        if (StringUtils.isNotEmpty(customer.getPhone())) {
            query.setPara("phone", customer.getPhone());
        }
        query = custService.findPage(query);
        ResultMap<Customer> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }

    @RequestMapping("addPage")
    public String addPage(HttpServletRequest request) {
        //查询所有地区
       List<Dict> aerasList = dictService.findByType(DictConstantType.ADMIN_AREAS_TYPE);
        //查询所有设计师
        List<Designer> designerList = designerService.findAll();
        //查询所有小区
        List<HeatAreas> heatAreasList = heatAreasService.findAll();
        //查询所有户型
        List<Dict> layoutList = dictService.findByType(DictConstantType.ADMIN_LAYOUT_TYPE);
        //预约类型
        List<Dict> typeList = dictService.findByType(DictConstantType.ADMIN_TYPE_TYPE);
        request.setAttribute("aerasList",aerasList);
        request.setAttribute("designerList",designerList);
        request.setAttribute("heatAreasList",heatAreasList);
        request.setAttribute("layoutList",layoutList);
        request.setAttribute("typeList",typeList);

        return "manage/cust/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request,Customer customer) {
        try {
            Admin admin = adminService.getCurrent();
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
            return JsonResult.success("保存成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    /**
     * 查看界面
     *
     * @param request
     * @param custId
     * @return
     */
    @RequestMapping("/view/{custId}")
    public String view(HttpServletRequest request, @PathVariable("custId") String custId) {
        Customer customer = custService.findById(custId);
        if(!"".equals(customer.getHouseName()) && customer.getHouseName() != null){
            HeatAreas areas = heatAreasService.findById(customer.getHouseName());
            if(areas != null){
                customer.setHouseName(areas.getName());
            }
        }
        if(!"".equals(customer.getDesignerId()) && customer.getDesignerId() != null){
            Designer designer = designerService.findById(customer.getDesignerId());
            if(designer != null){
                customer.setDesignerId(designer.getName());
            }
        }
        request.setAttribute("cust", customer);
        return "manage/cust/view";
    }

    /**
     * 修改界面
     *
     * @param request
     * @param phone
     * @return
     */
    @RequestMapping("/editPage/{phone}")
    public String editPage(HttpServletRequest request, @PathVariable("phone") String phone) {
        //查询所有地区
        List<Dict> aerasList = dictService.findByType(DictConstantType.ADMIN_AREAS_TYPE);
        //查询所有设计师
        List<Designer> designerList = designerService.findAllByStatus();
        //查询所有小区
        List<HeatAreas> heatAreasList = heatAreasService.findAllByStatus();
        //查询所有户型
        List<Dict> layoutList = dictService.findByType(DictConstantType.ADMIN_LAYOUT_TYPE);
        //预约类型
        List<Dict> typeList = dictService.findByType(DictConstantType.ADMIN_TYPE_TYPE);
        request.setAttribute("aerasList",aerasList);
        request.setAttribute("designerList",designerList);
        request.setAttribute("heatAreasList",heatAreasList);
        request.setAttribute("layoutList",layoutList);
        request.setAttribute("typeList",typeList);
        Customer customer = custService.findByphone(phone);
        if(!"".equals(customer.getHouseName()) && customer.getHouseName() != null){
            HeatAreas areas = heatAreasService.findById(customer.getHouseName());
            customer.setHouseName(areas.getName());
        }
       if(!"".equals(customer.getDesignerId()) && customer.getDesignerId() != null){
           Designer designer = designerService.findById(customer.getDesignerId());
           customer.setDesignerId(designer.getName());
       }
        request.setAttribute("cust", customer);
        return "manage/cust/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, Customer customer) {
        try {
            custService.update(customer);
            return JsonResult.success("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Customer customer) {
        try {
            custService.deleteByPhone(customer.getPhone());
           return JsonResult.success("删除成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonResult.error("系统异常", null);
        }
    }

}
