package cn.com.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.dao.admin.AdminRoleDao;
import cn.com.entity.admin.AdminRole;
import cn.com.service.admin.AdminRoleService;

import java.util.List;

/**
 * 用户-角色 -- serviceImpl
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Long> findByAdminId(Long id) {
        return adminRoleDao.findByAdminId(id);
    }

    @Override
    public List<Long> findByRoleId(Long roleId) {
        return adminRoleDao.findByRoleId(roleId);
    }

    @Override
    public void save(AdminRole adminRole) {
        adminRoleDao.insert(adminRole);
    }

    @Override
    public void deleteByAdminId(Long adminId) {
        adminRoleDao.deleteByAdminId(adminId);
    }

}
