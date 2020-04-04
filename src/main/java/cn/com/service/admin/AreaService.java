package cn.com.service.admin;

import cn.com.entity.admin.Area;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * @Author: Li Sir
 * @Date: 2018/12/28 14:40
 * @Description:  区域业务
 * 君不见高堂明镜悲白发，朝如青丝暮成雪
 */
public interface AreaService extends BaseService<Area> {

    PageQuery<Area> findAll(PageQuery<Area> query);

    /**
     * 查询省级
     *
     * @return
     */
    public List<Area> findArea(Class<Area> areaClass, String id);

    /**
     * 通过ID数组查询集合
     *
     * @param hasAreas
     */
    public List<Area> findByIds(String[] hasAreas);

    /**
     * @Author:  ljc
     * Created on 2019/1/21
     *  通过id 查询多个地区
    */
    List<Area> findAreaByIds(String[] areaId);
}
