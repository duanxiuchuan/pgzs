package cn.com.dao.base;

import cn.com.entity.base.JobTask;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * 定时任务
 *
 * @author LiDaDa
 */
public interface JobTaskDao extends BaseDao<JobTask> {

	void findAll(PageQuery query);

	JobTask findJobTaskByCode(@Param("code") String code);

	public List<JobTask> findByValid();
}
