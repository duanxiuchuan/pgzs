package cn.com.service.admin.impl;

import cn.com.dao.admin.WikiDao;
import cn.com.entity.admin.Wiki;
import cn.com.service.admin.WikiService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikiServiceImpl extends BaseServiceImpl<Wiki> implements WikiService {

    @Autowired
    private WikiDao wikiDao;
    @Override
    public PageQuery<Wiki> findPage(PageQuery<Wiki> query) {
        PageQuery<Wiki> ret = wikiDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }

    @Override
    public void add(Wiki wiki) {
        wikiDao.insert(wiki);
    }

    @Override
    public void update(Wiki wiki) {
        wikiDao.updateTemplateById(wiki);
    }

    @Override
    public void deleteByWikiId(String wikiId) {
        wikiDao.deleteByWikiId(wikiId);
    }
}
