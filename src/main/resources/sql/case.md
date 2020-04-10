findPage
===
* 分页查询

    select
    @pageTag(){
        c.*,h.name as areasName,d.name as designerName
    @}
    from  exquisite_case c
     left join heat_areas h on c.areas_id = h.areas_id
     left join designer d on c.designer_id = d.designer_id
     
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

findAllByStatus
===
select * from exquisite_case where status = 1
