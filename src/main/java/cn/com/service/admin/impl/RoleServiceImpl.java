package cn.com.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.dao.admin.RoleDao;
import cn.com.entity.admin.Role;
import cn.com.service.admin.RoleService;

/**
 * 角色 -- serviceImpl
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(Role role) {
        roleDao.insert(role);
    }

    @Override
    public int update(Role role) {
        return roleDao.updateTemplateById(role);
    }

    @Override
    public List<Role> findByBuilt() {
        return roleDao.findByBuilt();
    }

    @Override
    public List<Role> findByAdminId(Long id) {
        return roleDao.findByAdminId(id);
    }
}
