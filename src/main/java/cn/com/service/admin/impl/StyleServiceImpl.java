package cn.com.service.admin.impl;

import cn.com.dao.admin.StyleDao;
import cn.com.entity.admin.Style;
import cn.com.service.admin.StyleService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StyleServiceImpl extends BaseServiceImpl<Style> implements StyleService {

    @Autowired
    private StyleDao styleDao;
    @Override
    public PageQuery<Style> findPage(PageQuery<Style> query) {
        PageQuery<Style> ret = styleDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }

    @Override
    public void add(Style style) {
        styleDao.insert(style);
    }

    @Override
    public void update(Style style) {
        styleDao.updateTemplateById(style);
    }

    @Override
    public void deleteByStyleId(String styleId) {
        styleDao.deleteByStyleId(styleId);
    }
}
