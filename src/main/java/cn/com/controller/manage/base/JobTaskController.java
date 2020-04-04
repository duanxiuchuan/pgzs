package cn.com.controller.manage.base;

import cn.com.annotation.Log;
import cn.com.common.result.ResultMap;
import cn.com.entity.admin.Admin;
import cn.com.entity.base.JobTask;
import cn.com.service.admin.JobTaskService;
import cn.com.task.init.BaseJob;
import cn.com.utils.StringUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.quartz.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 定时任务
 *
 * @author LiDaDa
 */
@Controller
@RequestMapping("/manage/job")
public class JobTaskController extends BaseController {

    @Log
	private Logger logger;
    @Autowired
    private JobTaskService jobTaskService;
    //加入Qulifier注解，通过名称注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

	@RequestMapping("/list")
	public String list() {
		return "manage/job/list";
	}

    @ResponseBody
    @RequestMapping("/listData")
    public ResultMap<JobTask> listData(HttpServletRequest request,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                       JobTask jobTask) {
        PageQuery query = new PageQuery(page, limit);
        if (StringUtils.isNotEmpty(jobTask.getName())) {
			query.setPara("name", jobTask.getName());
		}
        if (StringUtils.isNotEmpty(jobTask.getGroup())) {
        	query.setPara("group", jobTask.getGroup());
        }
        if (StringUtils.isNotEmpty(jobTask.getCode())) {
        	query.setPara("code", jobTask.getCode());
        }
        jobTaskService.findAll(query);
        ResultMap<JobTask> resultMap = new ResultMap<>(query.getList(), query.getTotalRow());
        return resultMap;
    }
    
    /**
     * 打开新增界面
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        return "manage/job/add";
    }
    
    /**
     * 新增
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @RequestMapping(value = "addjob")
    public void addjob(@RequestParam(value = "jobClassName") String jobClassName,
                       @RequestParam(value = "jobGroupName") String jobGroupName,
                       @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        jobTaskService.addJob(jobClassName, jobGroupName, cronExpression);
    }
    

    /**
     * 新增任务
     *
     * @param request
     * @param jobTask
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, JobTask jobTask) {
        try {
            Admin current = adminService.getCurrent();
            // 判断不能为空
            if (StringUtils.isEmpty(jobTask.getName())) {
                return super.messageResult(i18nMessage.getMessage("admin.jobTask.nameNotNull"), false);
            }
            // 判断任务是否存在
            JobTask jobTaskTemp = jobTaskService.findJobTaskByCode(jobTask.getCode());
            if (jobTaskTemp != null) {
            	return super.messageResult(i18nMessage.getMessage("admin.jobTask.codeNotRepeat"), false);
            }
            jobTask.setDate(new Date());
            jobTask.setIsValid(JobTask.IsValid.start.ordinal());
            jobTaskService.addJob(jobTask.getCode(), jobTask.getGroup(), jobTask.getCron());
            jobTaskService.add(jobTask);
            return super.messageResult(i18nMessage.getMessage("admin.common.addSuccess"), true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }



    /**
     * 暂停
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/pausejob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobPause(jobClassName, jobGroupName);
    }
    
    /**
     * 暂停功能
     *
     * @param request
     * @return
     */
    @RequestMapping("/pause")
    @ResponseBody
    public Map<String, Object> pause(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            JobTask jobTask = jobTaskService.findById(Long.valueOf(id));
            int flag = 0;
            if (jobTask != null) {
            	jobPause(jobTask.getCode(), jobTask.getGroup());
				jobTask.setIsValid(JobTask.IsValid.stop.ordinal());
				flag = jobTaskService.update(jobTask);
			}
            
            if (flag > 0) {
                return super.messageResult(i18nMessage.getMessage("admin.jobTask.pauseSuccess"), true);
            } else {
                return super.messageResult(i18nMessage.getMessage("admin.jobTask.pauseFail"), false);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }

    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 重新开始
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobresume(jobClassName, jobGroupName);
    }
    
    /**
     * 开始功能
     *
     * @param request
     * @return
     */
    @RequestMapping("/resume")
    @ResponseBody
    public Map<String, Object> resume(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            JobTask jobTask = jobTaskService.findById(Long.valueOf(id));
            int flag = 0;
            if (jobTask != null) {
            	jobresume(jobTask.getCode(), jobTask.getGroup());
				jobTask.setIsValid(JobTask.IsValid.start.ordinal());
				flag = jobTaskService.update(jobTask);
			}
            
            if (flag > 0) {
                return super.messageResult(i18nMessage.getMessage("admin.jobTask.resumeSuccess"), true);
            } else {
                return super.messageResult(i18nMessage.getMessage("admin.jobTask.resumeFail"), false);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }

    public void jobresume(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 修改界面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editPage/{id}")
    public String editPage(HttpServletRequest request, @PathVariable("id") Long id) {
    	JobTask jobTask = jobTaskService.findById(id);
        request.setAttribute("jobTask", jobTask);
        return "manage/job/edit";
    }
    
    /**
     * 修改任务
     *
     * @param request
     * @param jobTask
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request, JobTask jobTask) {
        try {
            Admin current = adminService.getCurrent();
            JobTask preJobTask = jobTaskService.findById(jobTask.getId());
            // 判断任务是否存在
            JobTask jobTaskTemp = jobTaskService.findJobTaskByCode(jobTask.getCode());
            if (preJobTask != null && jobTaskTemp != null && !Objects.equals(preJobTask.getCode(), jobTaskTemp.getCode())) {
            	return super.messageResult(i18nMessage.getMessage("admin.jobTask.codeNotRepeat"), false);
			}
            jobreschedule(jobTask.getCode(), jobTask.getGroup(), jobTask.getCron());
            jobTaskService.update(jobTask);
            return super.messageResult(i18nMessage.getMessage("admin.common.updateSuccess"), true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }
    
    /**
     * 修改任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        jobreschedule(jobClassName, jobGroupName, cronExpression);
    }

    public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }

    /**
     * 删除任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobdelete(jobClassName, jobGroupName);
    }
    
    /**
     * 删除功能
     *
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            JobTask jobTask = jobTaskService.findById(Long.valueOf(id));
            jobdelete(jobTask.getCode(), jobTask.getGroup());
            int flag = jobTaskService.deleteById(id);
            
            if (flag > 0) {
                return super.messageResult(i18nMessage.getMessage("admin.common.deleteSuccess"), true);
            } else {
                return super.messageResult(i18nMessage.getMessage("admin.common.deleteFail"), false);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return super.messageResult(i18nMessage.getMessage("admin.common.systemError"), false);
        }
    }

    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
//        PageInfo<JobTask>  = jobTaskService.getJobAndTriggerDetails(pageNum, pageSize);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("JobAndTrigger", jobAndTrigger);
//        map.put("number", jobAndTrigger.getTotal());
//        return map;
        return null;
    }


    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

}
