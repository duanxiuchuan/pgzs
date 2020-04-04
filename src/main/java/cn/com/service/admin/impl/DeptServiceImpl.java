package cn.com.service.admin.impl;

import cn.com.dao.admin.DeptDao;
import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Dept;
import cn.com.entity.admin.vo.DeptNode;
import cn.com.service.admin.DeptService;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源权限 -- serviceImpl
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public List<DeptNode> packageDeptsTree(Admin admin) {
        List<DeptNode> result = new ArrayList<DeptNode>();
        //获取部门
        Dept dept = deptDao.findDeptByid(admin.getDeptId());
        //获取组织根节点
        List<Dept> deptRoots = deptDao.findDirectlyChild(dept.getParentId());
        //树根节点
        DeptNode rootNode = null;
        if (!CollectionUtils.isEmpty(deptRoots)) {
            for (Dept deptRoot : deptRoots) {
                rootNode = getDeptNodeField(rootNode, deptRoot, splitJointChirdNodes(deptRoot));
                result.add(rootNode);
            }
        }
        return result;
    }

    private DeptNode getDeptNodeField(DeptNode currentNode, Dept dept, List<DeptNode> nodes) {
        currentNode = new DeptNode(dept.getId(), dept.getName(), 0L, String.valueOf(dept.getId().longValue()), false, nodes);
        return currentNode;
    }

    /**
     * 根据父节点获取所有子节点集合（递归）
     * @param parentDept 父级节点
     * @return
     */
    private List<DeptNode> splitJointChirdNodes(Dept parentDept){
        DeptNode parentNode = null;
        //树子节点
        List<DeptNode> currentChirdNodeList = new ArrayList<DeptNode>();
        //直属下级部门
        List<Dept> currentChirdDepts =  deptDao.findDirectlyChild(parentDept.getId().longValue());

        if(currentChirdDepts!=null&&currentChirdDepts.size()>0){
            for (Dept currentChirdDept : currentChirdDepts) {
                DeptNode currentNode = null;
                //递归获取所有子节点
                currentNode = getDeptNodeField(currentNode, currentChirdDept, splitJointChirdNodes(currentChirdDept));
                currentChirdNodeList.add(currentNode);
            }

            parentNode = getDeptNodeField(parentNode, parentDept, currentChirdNodeList);
        }else{
            parentNode = getDeptNodeField(parentNode, parentDept, null);
        }

        return currentChirdNodeList;
    }

    @Override
    public void save(Dept dept) {
        deptDao.insert(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.updateTemplateById(dept);
    }
}
