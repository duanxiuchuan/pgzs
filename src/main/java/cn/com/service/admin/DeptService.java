package cn.com.service.admin;

import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Dept;
import cn.com.entity.admin.vo.DeptNode;

import java.util.List;

/**
 * 部门
 *
 * @author LiDaDa
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 获取组织机构
     *
     * @return
     */
    public List<DeptNode> packageDeptsTree(Admin admin);

    /**
     * 保存
     *
     * @param dept
     */
    public void save(Dept dept);

    /**
     * 更新
     *
     * @param dept
     */
    public void update(Dept dept);
}
