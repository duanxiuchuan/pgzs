package cn.com.service.admin.impl;

import cn.com.dao.admin.DesignerDao;
import cn.com.entity.admin.Designer;
import cn.com.service.admin.DesignerService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignerServiceImpl extends BaseServiceImpl<Designer> implements DesignerService {

    @Autowired
    private DesignerDao designerDao;
    @Override
    public PageQuery<Designer> findPage(PageQuery<Designer> query) {
        return designerDao.findPage(query);
    }

    @Override
    public void add(Designer designer) {
        designerDao.insert(designer);
    }

    @Override
    public void update(Designer designer) {
        designerDao.updateTemplateById(designer);
    }

    @Override
    public void deleteByDesignerId(String designerId) {
        designerDao.deleteByDesignerId(designerId);
    }
}
