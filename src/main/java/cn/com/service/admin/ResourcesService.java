package cn.com.service.admin;

import org.beetl.sql.core.annotatoin.Param;

import cn.com.entity.admin.Resources;

import java.util.List;

/**
 * 资源（权限）
 *
 * @author LiDaDa
 * @date 2018-07-18
 */
public interface ResourcesService extends BaseService<Resources>{

    /**
     * 通过用户查询权限
     *
     * @param adminId
     * @return
     */
    public List<Resources> loadAdminResources(Long adminId);

    /**
     * 通过类型查询权限
     *
     * @param type
     * @return
     */
    public List<Resources> findMenu(int type);

    /**
     * 通过角色查询其包含的权限
     *
     * @param roleId
     * @return
     */
    public List<Resources> findByRoleId(Long roleId);

    /**
     * 查询子类的集合
     *
     * @param roleId
     * @return
     */
    public List<Resources> findByParentId(Long roleId);
    
    /**
     * 设置分类
     * @param resources
     */
	public void addCategory(Resources resources);
	
	/**
     * 修改分类
     * @param res
     */
	public void editCategory(Resources res);

    /**
     * 查询顶级菜单
     *
     * @param adminId
     * @return
     */
	public List<Resources> findTopMenu(Long adminId);

    /**
     * 查询自己的权限
     *
     * @param adminId
     * @return
     */
    public List<Resources> findMyResources(@Param("adminId") Long adminId);
}
