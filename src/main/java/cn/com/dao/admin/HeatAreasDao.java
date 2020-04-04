package cn.com.dao.admin;
import org.beetl.sql.core.annotatoin.*;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import cn.com.entity.admin.*;

/**
 * @Author Lidada
 * @Description：
 **/
public interface HeatAreasDao extends cn.com.dao.base.BaseDao<HeatAreas> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<HeatAreas> findAll(PageQuery<HeatAreas> query);
}