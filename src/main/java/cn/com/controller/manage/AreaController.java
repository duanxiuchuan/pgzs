package cn.com.controller.manage;

import cn.com.common.result.ResultMap;
import cn.com.controller.manage.base.BaseController;
import cn.com.entity.admin.Area;
import cn.com.service.admin.AreaService;
import cn.com.utils.StringUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Li Sir
 * @Date: 2019/1/9 14:30
 * @Description:     区域控制层     君不见高堂明镜悲白发，朝如青丝暮成雪
 */
@Controller
@RequestMapping("/manage/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;


    /**
     * @Author: Li Sir
     * @Date: 2019/1/9 15:41
     * @Description:
     */
    @RequestMapping("/index")
    public String index() {
        return "hospital/area/list";
    }

    /**
     * @Author: Li Sir
     * @Date: 2019/1/9 15:41
     * @Description:
     */
    @RequestMapping("/listData")
    @ResponseBody
    public ResultMap<Area> listData(HttpServletRequest request,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
                                        , String name) {
        PageQuery<Area> query = new PageQuery(page, limit);
        if (StringUtils.isNotEmpty(name)){
            query.setPara("name",name);
        }
        query = areaService.findAll(query);
        ResultMap<Area> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }
}