package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Admin;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * 管理员 -- dao
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface AdminDao extends BaseDao<Admin> {
    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return
     */
    @SqlStatement(params = "userName")
    public Admin findByUserName(@Param("userName") String userName);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    public PageQuery<Admin> findPage(PageQuery<Admin> query);

    /**
     * 通过部门id查询用户列表
     *
     * @param deptId
     * @return
     */
    public List<Admin> findByDeptId(@Param("deptId") Long deptId);

    /**
     *
     *
     *
     * @param id
     * @return
     */
    public Admin findByAgentId(@Param("id") Long id);

}
