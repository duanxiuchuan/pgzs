package cn.com.service.admin;

import java.util.List;

import cn.com.entity.admin.RoleResources;

/**
 * 角色 -- 资源（权限）
 *
 * @author LiDaDa
 * @date 2018-07-18
 */
public interface RoleResourcesService {
    /**
     * 保存实体
     * @param roleResources
     */
    public void save(RoleResources roleResources);

    /**
     * 删除实体
     *
     * @param roleId
     * @param resourcesId
     */
    public void delete(Long resourcesId, Long roleId);

    /**
     * 通过roleId和resourcesId查询
     *
     * @param roleId
     * @param resourcesId
     * @return
     */
    public RoleResources findByRoleAndResourcesId(Long roleId, Long resourcesId);

    /**
     * 通过roleId查询角色是否有权限
     *
     * @param roleId
     * @return
     */
    public List<RoleResources> findByRoleId(Long roleId);
}
