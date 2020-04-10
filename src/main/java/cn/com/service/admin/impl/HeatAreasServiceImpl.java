package cn.com.service.admin.impl;

import cn.com.dao.admin.HeatAreasDao;
import cn.com.entity.admin.Designer;
import cn.com.entity.admin.HeatAreas;
import cn.com.service.admin.HeatAreasService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeatAreasServiceImpl extends BaseServiceImpl<HeatAreas> implements HeatAreasService {

    @Autowired
    private HeatAreasDao heatAreasDao;
    @Override
    public PageQuery<HeatAreas> findPage(PageQuery<HeatAreas> query) {
        PageQuery<HeatAreas> ret = heatAreasDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }

    @Override
    public void add(HeatAreas heatAreas) {
        heatAreasDao.insert(heatAreas);
    }

    @Override
    public void update(HeatAreas heatAreas) {
        heatAreasDao.updateTemplateById(heatAreas);
    }

    @Override
    public void deleteByAreasId(String areasId) {
        heatAreasDao.deleteByAreasId(areasId);
    }

    @Override
    public List<HeatAreas> findAllByStatus() {
        return heatAreasDao.findAllByStatus();
    }
}
