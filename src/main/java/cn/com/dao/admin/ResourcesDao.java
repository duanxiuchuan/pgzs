package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Resources;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;

import java.util.List;

/**
 * 权限资源 -- dao
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
public interface ResourcesDao extends BaseDao<Resources> {

    /**
     * 加载用户的权限资源
     *
     * @param adminId
     * @return
     */
    @SqlStatement(params = "adminId")
    public List<Resources> loadAdminResources(Long adminId);

    /**
     * 通过类型查询权限
     *
     * @param type
     * @return
     */
    public List<Resources> findMenu(@Param("type") int type);

    /**
     * 查询第一级菜单
     *
     * @return
     */
    public List<Resources> findFirstMenu();

    /**
     * 查询按钮权限
     *
     * @return
     */
    public List<Resources> findSecondMenu();

    /**
     * 通过角色查询其包含的权限
     *
     * @param roleId
     * @return
     */
    @SqlStatement(params = "roleId")
    public List<Resources> findByRoleId(Long roleId);

    /**
     * 查询子类的集合
     *
     * @param roleId
     * @return
     */
    public List<Resources> findByParentId(@Param("roleId") Long roleId);
    
    /**
     * 修改分类
     *
     * @param res
     * @return
     */
    @SqlStatement
    public void editCate(Resources res);

    /**
     * 通过用户id查询权限
     *
     * @param adminId
     * @return
     */
    public List<Resources> findTopMenu(@Param("adminId") Long adminId);

    /**
     * 查询自己的权限
     *
     * @param adminId
     * @return
     */
    public List<Resources> findMyResources(@Param("adminId") Long adminId);
}
