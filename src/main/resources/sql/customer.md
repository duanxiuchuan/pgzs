findPage
===
* 分页查询

    select
    @pageTag(){
        c.*,h.name as houseName,d.name as designerName
    @}
    from  customer c
    left join heat_areas h on c.house_name = h.areas_id
    left join designer d on c.designer_id = d.designer_id
    where 1 = 1 
    and c.status =1
    @if(!isEmpty(name)) {
            and c.name like #'%'+name+'%'#
    @}
    @if(!isEmpty(phone)) {
     and c.phone like #'%'+phone+'%'#
    @}
    @pageIgnoreTag(){
        order by c.create_time desc
    @}


findByphone
=====
* 根据电话查询客户
    select * from customer where phone = #phone# and status=1
    
deleteByPhone
====
* 根据电话删除
   update customer set status = 0 where phone = #phone# and status=1