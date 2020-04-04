findRootDept
===
* 查询根节点

    select * from sys_dept where parent_id = 0
    
findDirectlyChild
===
* 根据部门id,获取直属下级部门信息

    select * from sys_dept where parent_id = #id#
    
findDeptByid
===
* 根据部门id,获取直属下级部门信息

    select * from sys_dept where id = #deptId#