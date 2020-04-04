package cn.com.service.admin;

import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Customer;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

public interface CustService extends BaseService<Customer>{



    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Customer> findPage(PageQuery<Customer> query);

    Customer findByphone(String phone);

    void add(Customer customer);

    void update(Customer customer);

    void deleteByPhone(String phone);
}
