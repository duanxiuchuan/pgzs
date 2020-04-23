package cn.com.service.admin;

import cn.com.entity.admin.Wiki;
import org.beetl.sql.core.engine.PageQuery;

public interface WikiService extends BaseService<Wiki> {
    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Wiki> findPage(PageQuery<Wiki> query);

    void add(Wiki wiki);

    void update(Wiki wiki);

    void deleteByWikiId(String wikiId);

    Wiki findByIdOne(String wikiId);
}
