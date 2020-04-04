package cn.com.service.admin;

import cn.com.entity.base.Dict;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * 字典
 *
 * @author LiDaDa
 */
public interface DictService extends BaseService<Dict> {

    /**
     * 分页查询
     *
     * @param query
     */
    public List<Dict> findAll(PageQuery query);

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public Dict findCoreDict(String type, String value);


    /**
     *
     * @param type
     * @param name
     * @return
     */
    public Dict findDictByName(String type, String name);

    /**
     * 根据类型查询字典
     * @param type
     * @return
     */
    List<Dict> findByDicType(String type);
}
