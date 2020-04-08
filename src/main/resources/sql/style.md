findPage
===
* 分页查询

    select
    @pageTag(){
        s.*
    @}
    from  style s
    where 1 = 1
    and s.status =1
    @if(!isEmpty(title)) {
        and s.title like #'%'+title+'%'#
    @} 
    @if(!isEmpty(style)) {
        and s.style like #'%'+style+'%'#
    @} 
    @if(!isEmpty(space)) {
        and s.space like #'%'+space+'%'#
    @}
    @pageIgnoreTag(){
        order by s.style_id desc
    @}


deleteByStyleId
====
* 逻辑删除
update style set status = 0 where status = 1 and style_id = #styleId#