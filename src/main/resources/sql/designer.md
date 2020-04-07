findPage
===
* 分页查询

    select
    @pageTag(){
        d.*
    @}
    from  designer d
    where 1 = 1
    and d.status = 1
    @if(!isEmpty(name)) {
       and d.name like #'%'+name+'%'#
    @}
    @if(!isEmpty(type)) {
       and d.type like #'%'+type+'%'#
    @}
    @pageIgnoreTag(){
        order by d.years desc
    @}


deleteByDesignerId
===
* 逻辑删除
update designer set status =0 where status =1 and designer_id =#designerId#