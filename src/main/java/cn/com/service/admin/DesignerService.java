package cn.com.service.admin;

import cn.com.entity.admin.Designer;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

public interface DesignerService extends BaseService<Designer> {
    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Designer> findPage(PageQuery<Designer> query);

    void add(Designer designer);

    void update(Designer designer);

    void deleteByDesignerId(String caseId);

    List<Designer> findAllByStatus();

    Designer findByIdOne(String designerId);
}
