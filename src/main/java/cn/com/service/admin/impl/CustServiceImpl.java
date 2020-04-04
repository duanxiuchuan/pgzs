package cn.com.service.admin.impl;

import cn.com.dao.admin.CustomerDao;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Customer;
import cn.com.service.admin.CustService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustServiceImpl extends BaseServiceImpl<Customer> implements CustService {

    @Autowired
    private CustomerDao customerDao;


    @Override
    public PageQuery<Customer> findPage(PageQuery query) {
        PageQuery<Customer> ret = customerDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }

    @Override
    public Customer findByphone(String phone) {
        return customerDao.findByphone(phone);
    }

    @Override
    public void add(Customer customer) {
        customerDao.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.updateTemplateById(customer);
    }

    @Override
    public void deleteByPhone(String phone) {
        customerDao.deleteByPhone(phone);
    }


}
