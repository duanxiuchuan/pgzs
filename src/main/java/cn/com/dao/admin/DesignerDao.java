package cn.com.dao.admin;
import org.beetl.sql.core.annotatoin.*;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import cn.com.entity.admin.*;

import java.util.List;

/**
 * @Author Lidada
 * @Description：
 **/
public interface DesignerDao extends cn.com.dao.base.BaseDao<Designer> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<Designer> findPage(PageQuery<Designer> query);

    void deleteByDesignerId(@Param("designerId")String designerId);

    List<Designer> findAllByStatus();
}