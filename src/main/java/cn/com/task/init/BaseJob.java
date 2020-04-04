package cn.com.task.init;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author LiDaDa
 */
public interface BaseJob extends Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException;
}
