package cn.com.service.admin.impl;

import cn.com.dao.admin.AdminDao;
import cn.com.entity.admin.Admin;
import cn.com.service.admin.AdminService;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员 -- serviceImpl
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Override
    public List<Admin> findAll(Class<Admin> adminClass) {
        return super.findAll(adminClass);
    }

    @Override
    public List<Admin> findAll() {
        return super.findAll();
    }

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findByUserName(String userName) {
        return adminDao.findByUserName(userName);
    }

    @Override
    public PageQuery<Admin> findPage(PageQuery<Admin> query){
        PageQuery<Admin> ret = adminDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }

    @Override
    public Long add(Admin admin) {
        return adminDao.insertReturnKey(admin).getLong();
    }

    @Override
    public int update(Admin admin) {
        return adminDao.updateTemplateById(admin);
    }

    @Override
    public Admin getCurrent() {
        try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
			    Object principal = subject.getPrincipal();
			    if(principal != null){
			    	 Admin admin = new Admin();
			         if(principal instanceof Admin) {
			             admin = (Admin) principal;
			         } else {
			             admin = JSON.parseObject(JSON.toJSON(principal).toString(), Admin.class);
			         }
			         if (principal != null) {
			             return adminDao.unique(admin.getId());
			         }
			    }
			   
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public List<Admin> findByDeptId(Long deptId) {
        return adminDao.findByDeptId(deptId);
    }

    @Override
    public Admin findByHospitalId(Class<Admin> adminClass, Long id) {
        Query<Admin> query = sqlManager.query(adminClass);
        Admin admin = query.andEq("agent_id", id).select().get(0);
        return admin;
    }
}
