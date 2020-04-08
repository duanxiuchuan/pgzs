package cn.com.dao.admin;
import org.beetl.sql.core.annotatoin.*;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import cn.com.entity.admin.*;

/**
 * @Author Lidada
 * @Description：
 **/
public interface StyleDao extends cn.com.dao.base.BaseDao<Style> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<Style> findPage(PageQuery<Style> query);

    void deleteByStyleId(@Param("styleId") String styleId);
}