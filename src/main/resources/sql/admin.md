findByUserName
===
* 根据userName获admin

    select * from sys_admin where user_name= #userName#

findPage
===
* 分页查询

    select 
    @pageTag(){
        a.*, a1.real_name createName, a2.real_name modifyName, d.`name` deptName 
    @}
    from sys_admin a, sys_admin a1, sys_admin a2, sys_dept d
    where a.creator = a1.id
    and a.modifier = a2.id
    and a.dept_id = d.id
    and a.del_flag = true
    @if(!isEmpty(userName)) {
        and a.user_name like #'%'+userName+'%'#
    @}
    @if(!isEmpty(deptId)) {
        and a.dept_id = #deptId#
    @}
    @if(!isEmpty(phone)) {
        and a.phone like #'%'+phone+'%'#
    @}
    @pageIgnoreTag(){
        order by a.id desc
    @}
    
findByDeptId
===
* 查询该部门下的员工
    
    select * from sys_admin where dept_id = #deptId#
    
    
findByAgentId
===
* 通过代理id查询用户

    select * from sys_admin where agent_id = #id#