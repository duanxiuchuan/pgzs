loadAdminResources
===
* 加载当前登录人权限

    select * from sys_resources r
    left join sys_role_resources rr on r.id = rr.res_id
    left join sys_admin_role ar on ar.role_id = rr.role_id
    where ar.admin_id = #adminId#

findMenu
===
* 查询一级菜单权限

    select * from sys_resources where type = #type# order by sort asc

findByRoleId
===
* 通过角色查询其包含的权限select * from sys_resources r where r.id in (select rr.resources_id from sys_role_resources rr where rr.role_id = #roleId#);

    SELECT re.id, re.`name`, re.is_enable, re.parent_id, re.url, re.type,	( CASE WHEN EXISTS ( SELECT 1 FROM sys_role_resources rr WHERE rr.res_id = re.id AND rr.role_id = #roleId# ) THEN 'true' ELSE 'false' END ) AS checked FROM sys_resources re WHERE re.parent_id != 0 and is_enable = 1 ORDER BY re.sort ASC

findByParentId
===
* 查询子类的集合

    select * from sys_resources where parent_id = #roleId#
    
editCate
===
* 修改分类

    update  sys_resources set modify_date = #modifyDate# ,modifier = #modifier# ,url = #url# , is_enable = #isEnable# ,name = #name# where id = #id#
    
findTopMenu
===
* 查找顶级菜单

    SELECT ( CASE WHEN EXISTS ( select 1 from sys_resources sr, sys_role_resources srr, sys_admin_role sar where sr.id = srr.res_id and srr.role_id = sar.role_id and sar.admin_id = #adminId# and sr.is_enable = 1 and sr.parent_id = r.id    		) THEN 'true' ELSE 'false' END ) AS hasChildren, r.* FROM sys_resources r WHERE r.type = 0 AND r.is_enable = 1 ORDER BY r.sort ASC
    
findMyResources
===
* 查询我的权限

    select sr.* from sys_resources sr, sys_role_resources srr, sys_admin_role sar where sr.id = srr.res_id and srr.role_id = sar.role_id and sar.admin_id = #adminId# and sr.is_enable = 1