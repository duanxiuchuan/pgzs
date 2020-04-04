findByAdminId
=== 
* 通过管理员id查询是否包含角色，如果包含则不能删除

    select role_id from sys_admin_role where admin_id = #id#
    
    
findByRoleId
===
* 查询改角色下的所有用户

    select admin_id from sys_admin_role where role_id = #roleId#
    
deleteByAdminId
===
* 通过adminId删除

    delete from sys_admin_role where admin_id = #adminId#