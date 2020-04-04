findAll
===
* 分页查询

    select 
    @pageTag(){
        t.*
    @}
    from job_task t
    where 1=1
    @if(!isEmpty(name)) {
        and t.name like #'%'+name+'%'#
    @}
    @if(!isEmpty(group)) {
        and t.group like #'%'+group+'%'#
    @}
    @if(!isEmpty(code)) {
        and t.group like #'%'+code+'%'#
    @}
    @pageIgnoreTag(){
        order by t.id desc
    @}
    
findJobTaskByCode
===
* 根据类名查任务

    select * from job_task where code=#code#
    
    
findByValid
===
* 查询启动的任务

    select * from job_task where is_valid = 1
    