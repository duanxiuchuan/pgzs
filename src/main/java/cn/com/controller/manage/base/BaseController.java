package cn.com.controller.manage.base;

import cn.com.common.message.I18nMessage;
import cn.com.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiDaDa
 */
public class BaseController {

    @Autowired
    protected AdminService adminService;
    @Resource
    protected I18nMessage i18nMessage;

    protected static final String NOT_LOGIN_VIEW = "redirect:/manage/login";


    /**
     * 返回状态
     */
    public static final String STATUS = "status";
    /**
     * 消息返回字段
     */
    public static final String RESULT = "result";

    /**
     * 正确标识
     */
    public static final String SUCCESS = "1";

    /**
     * 错误标识
     */
    public static final String ERROR = "0";

    /**
     *
     * @param result
     * @param isTrue
     * @return
     */
    protected Map<String, Object> messageResult(Object result, boolean isTrue) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        if (isTrue) {
            map.put(STATUS, SUCCESS);
        } else {
            map.put(STATUS, ERROR);
        }
        map.put(RESULT, result);
        return map;
    }
}
