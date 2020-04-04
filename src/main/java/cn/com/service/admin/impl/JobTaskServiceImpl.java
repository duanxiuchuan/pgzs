package cn.com.service.admin.impl;

import cn.com.dao.base.JobTaskDao;
import cn.com.entity.base.JobTask;
import cn.com.service.admin.JobTaskService;
import cn.com.task.init.BaseJob;
import org.beetl.sql.core.engine.PageQuery;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务
 *
 * @author LiDaDa
 */
@Service
public class JobTaskServiceImpl implements JobTaskService {
    @Autowired
    private JobTaskDao jobTaskDao;

	//加入Qulifier注解，通过名称注入bean
	@Autowired
	@Qualifier("Scheduler")
	private Scheduler scheduler;

    @Override
    public List<JobTask> findAll() {
        return jobTaskDao.all();
    }

	@Override
	public void findAll(PageQuery query) {
		jobTaskDao.findAll(query);
	}

	@Override
	public void add(JobTask jobTask) {
		jobTaskDao.insert(jobTask);
	}

	@Override
	public JobTask findJobTaskByCode(String code) {
		return jobTaskDao.findJobTaskByCode(code);
	}

	@Override
	public int deleteById(String id) {
		return jobTaskDao.deleteById(id);
	}

	@Override
	public JobTask findById(Long id) {
		return jobTaskDao.unique(id);
	}

	@Override
	public int update(JobTask jobTask) {
		return jobTaskDao.updateTemplateById(jobTask);
	}

	@Override
	public List<JobTask> findByValid() {
		return jobTaskDao.findByValid();
	}

	@Override
	public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
		// 启动调度器
		scheduler.start();
		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
		//表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		//按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
				.withSchedule(scheduleBuilder).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			System.out.println("创建定时任务失败" + e);
			throw new Exception("创建定时任务失败");
		}
	}

	public static BaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (BaseJob) class1.newInstance();
	}
}
