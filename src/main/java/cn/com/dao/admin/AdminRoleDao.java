package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.AdminRole;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;

import java.util.List;

/**
 * 用户 - 角色 -- dao
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {

    /**
     * 通过管理员id查询是否包含角色，如果包含则不能删除
     * @param id
     * @return
     */
    @SqlStatement(params = "id")
    public List<Long> findByAdminId(Long id);

    /**
     * 通过角色id查询集合
     *
     * @param roleId
     * @return
     */
    public List<Long> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过用户id删除
     *
     * @param adminId
     */
    public void deleteByAdminId(@Param("adminId") Long adminId);

}
