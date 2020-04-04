package cn.com.service.admin;

import cn.com.entity.admin.Role;

import java.util.List;

/**
 * 角色 - service
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface RoleService extends BaseService<Role>{

    /**
     * 新增角色
     *
     * @param role
     */
    public void save(Role role);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    public int update(Role role);

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
    public List<Role> findByAdminId(Long id);

}
