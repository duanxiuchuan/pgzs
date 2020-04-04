package cn.com.dao.admin;
import cn.com.dao.base.BaseDao;
import org.beetl.sql.core.annotatoin.*;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import cn.com.entity.admin.*;

/**
 * @Author Lidada
 * @Description：
 **/
public interface CustomerDao extends BaseDao<Customer> {

    /**
     * @Author Lidada
     * @Description：
     **/
     PageQuery<Customer> findAll(PageQuery<Customer> query);

    PageQuery<Customer> findPage(PageQuery query);

    Customer findByphone(@Param("phone")String phone);

    void deleteByPhone(@Param("phone")String phone);
}