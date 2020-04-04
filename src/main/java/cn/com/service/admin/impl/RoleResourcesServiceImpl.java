package cn.com.service.admin.impl;

import cn.com.dao.admin.RoleResourcesDao;
import cn.com.entity.admin.RoleResources;
import cn.com.service.admin.RoleResourcesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源权限 -- serviceImpl
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Service
public class RoleResourcesServiceImpl implements RoleResourcesService {
    @Autowired
    private RoleResourcesDao roleResourcesDao;

    @Override
    public void save(RoleResources roleResources) {
        roleResourcesDao.insert(roleResources);
    }

    @Override
    public void delete(Long resourcesId, Long roleId) {
        roleResourcesDao.deleteByRoleAndResourcesId(resourcesId, roleId);
    }

    @Override
    public RoleResources findByRoleAndResourcesId(Long roleId, Long resourcesId) {
        return roleResourcesDao.findByRoleAndResourcesId(roleId, resourcesId);
    }

    @Override
    public List<RoleResources> findByRoleId(Long roleId) {
        return roleResourcesDao.findByRoleId(roleId);
    }
}
