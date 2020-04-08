package cn.com.service.admin;

import cn.com.entity.admin.Style;
import org.beetl.sql.core.engine.PageQuery;

public interface StyleService extends BaseService<Style> {
    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Style> findPage(PageQuery<Style> query);

    void add(Style style);

    void update(Style style);

    void deleteByStyleId(String styleId);
}
