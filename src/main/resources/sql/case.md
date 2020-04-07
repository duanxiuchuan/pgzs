findPage
===
* 分页查询

    select
    @pageTag(){
        c.*
    @}
    from  exquisite_case c
    where 1 = 1
    and c.status = 1
    @if(!isEmpty(areasName)) {
                and c.areas_name like #'%'+areasName+'%'#
        @}
        @if(!isEmpty(style)) {
         and c.style like #'%'+style+'%'#
        @}
    @pageIgnoreTag(){
        order by c.area desc
    @}

deleteByCaseId
====
* 逻辑删除
update exquisite_case set status = 0 where case_id = #caseId# and status =1
