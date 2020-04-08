findPage
===
* 分页查询

    select
    @pageTag(){
        w.*
    @}
    from  wiki w
    where 1 = 1
    and w.status =1
    @if(!isEmpty(title)) {
      and w.title like #'%'+title+'%'#
    @} 
    @pageIgnoreTag(){
        order by w.clicks desc
    @}


deleteByWikiId
====
* 逻辑删除
update wiki set status =0 where status =1 and wiki_id = #wikiId#