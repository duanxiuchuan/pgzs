package cn.com.service.admin;


import cn.com.entity.admin.Case;
import cn.com.entity.admin.Customer;
import org.beetl.sql.core.engine.PageQuery;

public interface CaseService extends BaseService<Case> {
    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Case> findPage(PageQuery<Case> query);
}
