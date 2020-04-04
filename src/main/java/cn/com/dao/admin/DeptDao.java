package cn.com.dao.admin;

import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Dept;

import org.beetl.sql.core.annotatoin.Param;

import java.util.List;

/**
 * 部门
 *
 * @author LiDaDa
 */
public interface DeptDao extends BaseDao<Dept> {

    /**
     * 查询根节点
     *
     * @return
     */
    public List<Dept> findRootDept();

    /**
     * 根据部门id,获取直属下级部门信息
     *
     * @param id id
     * @return
     */
    public List<Dept> findDirectlyChild(@Param("id") long id);

     Dept findDeptByid(@Param("deptId") Long deptId);
}
