package cn.com.service.admin;

import cn.com.entity.base.JobTask;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * 定时任务
 *
 * @author LiDaDa
 */
public interface JobTaskService {
    /**
     * 查询所有
     *
     * @return
     */
    public List<JobTask> findAll();
    
    /**
     * 分页查询
     *
     * @return
     */
    public void findAll(PageQuery query);

	/**
	 * 新增
	 * 
	 * @param jobTask
	 */
	public void add(JobTask jobTask);

	/**
	 * 根据类名查任务
	 * @param name
	 * @return
	 */
	public JobTask findJobTaskByCode(String name);


    /**
     * 通过id删除数据
     *
     * @param id
     * @return
     */
	public int deleteById(String id);

	public JobTask findById(Long id);
	
    /**
     * 修改
     *
     * @param jobTask
     * @return
     */
	public int update(JobTask jobTask);

	public List<JobTask> findByValid();

	public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception;
}
