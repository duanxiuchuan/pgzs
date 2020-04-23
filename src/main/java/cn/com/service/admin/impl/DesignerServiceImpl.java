package cn.com.service.admin.impl;

import cn.com.dao.admin.DesignerDao;
import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Designer;
import cn.com.service.admin.DesignerService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignerServiceImpl extends BaseServiceImpl<Designer> implements DesignerService {

    @Autowired
    private DesignerDao designerDao;
    @Autowired
    private BaseDao<Designer> baseDao;
    @Override
    public PageQuery<Designer> findPage(PageQuery<Designer> query) {
        PageQuery<Designer> ret = designerDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
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

    @Override
    public List<Designer> findAllByStatus() {
        return designerDao.findAllByStatus();
    }

    @Override
    public Designer findByIdOne(String designerId) {
        return baseDao.single(designerId);
    }
}
