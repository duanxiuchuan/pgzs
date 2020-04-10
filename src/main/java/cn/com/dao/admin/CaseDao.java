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
public interface CaseDao extends cn.com.dao.base.BaseDao<Case> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<Case> findPage(PageQuery<Case> query);

    void deleteByCaseId(@Param("caseId") String caseId);

    List<Case> findAllByStatus();
}