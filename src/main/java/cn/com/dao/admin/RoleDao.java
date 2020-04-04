package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Role;

import org.beetl.sql.core.annotatoin.Param;

import java.util.List;

/**
 * 角色 -- dao
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface RoleDao extends BaseDao<Role> {

    /**
     * 查询集合
     *
     * @return
     */
    public List<Role> findByBuilt();

    /**
     * 查询集合
     *
     * @param id
     * @return
     */
    public List<Role> findByAdminId(@Param("id") Long id);
}
