package cn.com.dao.admin;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import cn.com.entity.admin.RoleResources;

import java.util.List;

/**
 * 角色 - 权限资源 -- dao
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface RoleResourcesDao extends BaseMapper<RoleResources>{
    /**
     * 通过roleId和resourcesId查询
     *
     * @param roleId
     * @param resourcesId
     * @return
     */
    @SqlStatement(params = "roleId, resourcesId")
    public RoleResources findByRoleAndResourcesId(Long roleId, Long resourcesId);

    /**
     * 通过roleId和resourcesId删除
     *
     * @param roleId
     * @param resourcesId
     * @return
     */
    public void deleteByRoleAndResourcesId(@Param("resourcesId") Long resourcesId, @Param("roleId") Long roleId);

    /**
     * 通过roleId查询角色是否有权限
     *
     * @param roleId
     * @return
     */
    public List<RoleResources> findByRoleId(@Param("roleId") Long roleId);
}
