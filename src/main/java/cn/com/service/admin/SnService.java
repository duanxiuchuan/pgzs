package cn.com.service.admin;


import cn.com.entity.admin.Sn;

/**
 * @Author: Li Sir
 * @Date: 2019/1/14 10:57
 * @Description: sn编号生成规则
 */
public interface SnService {

    String generate(Sn.Type type);

}
