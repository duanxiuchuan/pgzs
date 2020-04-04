findByBuilt
===
* 查询非内置的权限

    select * from sys_role  where is_built_in = 1

findByAdminId
===
* 查询用户的角色

    select a.*, (case when EXISTS (select 1 from sys_admin_role ar where ar.role_id = a.id and ar.admin_id = #id#) then 'true' else 'false' end) as checked from sys_role a where is_built_in = 1
