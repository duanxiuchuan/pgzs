findAll
===
*分页查询

    select
    @pageTag(){
     *
    @}
    from busi_area a
    where a.grade = 0
    @if(!isEmpty(name)) {
        and a.name like #'%'+name+'%'#
    @}
    @pageIgnoreTag(){
        order by a.id
    @}
    
findByIds
===

    select * from busi_area 
    where 1 = 1
    @if(!isEmpty(hasAreas)) {
        and id in ( #join(hasAreas)#)
    @}
    
        
findAreaByIds
===

    select * from busi_area 
    where 1 = 1
    @if(!isEmpty(hasAreas)) {
        and id in ( #join(hasAreas)#)
    @}