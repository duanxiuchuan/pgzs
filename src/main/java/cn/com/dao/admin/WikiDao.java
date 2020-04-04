package cn.com.dao.admin;
import org.beetl.sql.core.annotatoin.*;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import cn.com.entity.admin.*;

/**
 * @Author Lidada
 * @Description：
 **/
public interface WikiDao extends cn.com.dao.base.BaseDao<Wiki> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<Wiki> findAll(PageQuery<Wiki> query);
}