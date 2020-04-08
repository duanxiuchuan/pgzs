package cn.com.dao.base;

import cn.com.entity.base.Dict;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * 字典
 *
 * @author
 */
public interface DictDao extends BaseDao<Dict> {
	/**
	 * 分页查询
	 *
	 * @param query
	 * @return
	 */
	public List<Dict> findAll(PageQuery query);

	/**
	 * 通过名称和类型查找字典
	 *
	 * @param type
	 * @param name
	 * @return
	 */
	public Dict findDictByName(@Param("type") String type, @Param("name") String name);

	/**
	 * 通过类型查询
	 *
	 * @param type
	 * @return
	 */
	List<Dict> findByType(@Param("type") String type);

    int deleteByDictId(@Param("dictId")Long dictId);
}
