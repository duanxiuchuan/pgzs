package cn.com.service.admin;

import org.beetl.sql.core.engine.PageQuery;

import cn.com.entity.admin.Admin;

import java.util.List;

/**
 * 管理员 -- service
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface AdminService extends BaseService<Admin> {

    /**
     * 获取当前登录管理员
     *
     * @return 当前登录管理员，若不存在则返回null
     */
    public Admin getCurrent();

    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return
     */
    public Admin findByUserName(String userName);

    /**
     * 查询所有并分页
     *
     * @param query
     * @return
     */
    public PageQuery<Admin> findPage(PageQuery<Admin> query);

    /**
     * 保存
     *
     * @param admin 用户实体
     * @return
     */
    public Long add(Admin admin);

    /**
     * 修改
     *
     * @param admin 用户实体
     * @return
     */
    public int update(Admin admin);

    /**
     * 通过部门id查询用户集合
     *
     * @param deptId
     * @return
     */
    public List<Admin> findByDeptId(Long deptId);

    /**
     * 通过医院id查询用户
     *
     * @param id
     * @return
     */
    public Admin findByHospitalId(Class<Admin> adminClass, Long id);

}
