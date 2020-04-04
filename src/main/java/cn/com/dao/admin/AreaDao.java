package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Area;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * @Author: Li Sir
 * @Date: 2018/12/28 14:40
 * @Description:    区域    君不见高堂明镜悲白发，朝如青丝暮成雪
 */

public interface AreaDao  extends BaseDao<Area> {
    PageQuery<Area> findAll(PageQuery<Area> query);

    /**
     * 通过ID数组查询集合
     *
     * @param hasAreas
     */
    public List<Area> findByIds(@Param("hasAreas") String[] hasAreas);

    List<Area> findAreaByIds(String[] areaId);
}
