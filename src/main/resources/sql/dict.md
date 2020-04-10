findAll
===
* 分页查询

    select 
    @pageTag(){
        d.* 
    @}
    from sys_dict d
    where 1=1
    and d.del_flag = 1
    
findDictByName
===
* 通过名称和类型查找字典

    select * from sys_dict where type = #type# and name = #name#
    
    
findByType
===
* 通过类型查询
       
    select * from sys_dict where type = #type#
    
    
deleteByDictId
===
* 逻辑删除
update sys_dict set del_flag = 0 where del_flag =1 and dict_id = #dictId#