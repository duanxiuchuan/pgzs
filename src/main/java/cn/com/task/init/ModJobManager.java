package cn.com.task.init;

import cn.com.entity.base.JobTask;
import cn.com.service.admin.JobTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModJobManager implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JobTaskService jobTaskService;

    public void init() {
        try {
            List<JobTask> jobTasks = jobTaskService.findByValid();
            if (!jobTasks.isEmpty()) {
                for (JobTask jobTask : jobTasks) {
                    jobTaskService.addJob(jobTask.getCode(), jobTask.getGroup(), jobTask.getCron());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }
}
