package cn.com.service.admin.impl;

import cn.com.dao.admin.AreaDao;
import cn.com.entity.admin.Area;
import cn.com.service.admin.AreaService;
import cn.com.utils.StringUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Li Sir
 * @Date: 2019/1/9 15:45
 * @Description: 君不见高堂明镜悲白发，朝如青丝暮成雪
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {

    @Autowired
    private AreaDao areaDao;


    @Override
    public PageQuery<Area> findAll(PageQuery<Area> query) {
        return areaDao.findAll(query);
    }

    @Override
    public List<Area> findArea(Class<Area> areaClass, String id) {
        Query<Area> query = sqlManager.query(areaClass);
        List<Area> areas;
        if (StringUtils.isEmpty(id)) {
            areas = query.andEq("grade", 0).select();
        } else {
            areas = query.andEq("parent_id", id).select();
        }
        return areas;
    }

    @Override
    public List<Area> findByIds(String[] hasAreas) {
        return areaDao.findByIds(hasAreas);
    }

    @Override
    public List<Area> findAreaByIds(String[] areaId) {
        return areaDao.findAreaByIds(areaId);
    }
}
