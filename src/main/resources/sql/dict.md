findAll
===
* 分页查询

    select 
    @pageTag(){
        a1.real_name createName, a2.real_name modifyName, d.* 
    @}
    from sys_dict d, sys_admin a1, sys_admin a2
    where d.creator = a1.id
    and d.modifier = a2.id
    
findDictByName
===
* 通过名称和类型查找字典

    select * from sys_dict where type = #type# and name = #name#
    
    
findByType
===
* 通过类型查询
       
    select * from sys_dict where type = #type#