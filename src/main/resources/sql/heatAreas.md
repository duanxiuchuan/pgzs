findPage
===
* 分页查询

    select
    @pageTag(){
        h.*
    @}
    from  heat_areas h
    where 1 = 1
    and h.status = 1
    @if(!isEmpty(title)) {
        and h.title like #'%'+title+'%'#
     @} 
     @if(!isEmpty(name)) {
        and h.name like #'%'+name+'%'#
     @}
    @pageIgnoreTag(){
        order by h.title desc
    @}


deleteByAreasId
===
* 逻辑删除
update heat_areas set status =0 where status =1 and areas_id = #areasId#