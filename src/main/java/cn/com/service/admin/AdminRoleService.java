package cn.com.service.admin;

import cn.com.entity.admin.AdminRole;

import java.util.List;

/**
 * 用户角色
 *
 * @author LiDaDa
 * @date 2018-07-18
 */
public interface AdminRoleService {

    /**
     * 通过管理员id查询是否包含角色，如果包含则不能删除
     * @param id
     * @return
     */
    public List<Long> findByAdminId(Long id);


    /**
     * 通过角色id查询集合
     *
     * @param roleId
     * @return
     */
    public List<Long> findByRoleId(Long roleId);

    /**
     * 保存用户和角色
     *
     * @param adminRole
     */
    public void save(AdminRole adminRole);

    /**
     * 通过用户id删除
     *
     * @param adminId
     */
    public void deleteByAdminId(Long adminId);

}
