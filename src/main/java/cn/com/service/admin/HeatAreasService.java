package cn.com.service.admin;

import cn.com.entity.admin.HeatAreas;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

public interface HeatAreasService extends BaseService<HeatAreas> {
    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<HeatAreas> findPage(PageQuery<HeatAreas> query);

    void add(HeatAreas heatAreas);

    void update(HeatAreas heatAreas);

    void deleteByAreasId(String areasId);

    List<HeatAreas> findAllByStatus();
}
