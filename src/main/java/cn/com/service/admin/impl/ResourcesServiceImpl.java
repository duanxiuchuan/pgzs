package cn.com.service.admin.impl;

import cn.com.dao.admin.ResourcesDao;
import cn.com.entity.admin.Resources;
import cn.com.service.admin.ResourcesService;

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
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {

    @Autowired
    private ResourcesDao resourcesDao;

    @Override
    public List<Resources> loadAdminResources(Long adminId) {
        return resourcesDao.loadAdminResources(adminId);
    }

    @Override
    public List<Resources> findMenu(int type) {
        return resourcesDao.findMenu(type);
    }

    @Override
    public List<Resources> findByRoleId(Long roleId) {
        return resourcesDao.findByRoleId(roleId);
    }

    @Override
    public List<Resources> findByParentId(Long roleId) {
        return resourcesDao.findByParentId(roleId);
    }

	@Override
	public void addCategory(Resources resources) {
		resourcesDao.insert(resources);
	}

	@Override
	public void editCategory(Resources res) {
		resourcesDao.editCate(res);
	}

    @Override
    public List<Resources> findTopMenu(Long adminId) {
        return resourcesDao.findTopMenu(adminId);
    }

    @Override
    public List<Resources> findMyResources(Long adminId) {
        return resourcesDao.findMyResources(adminId);
    }
}
