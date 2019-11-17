package com.cdutcm.register.service.impl;

import com.cdutcm.register.config.JobFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/5/12 16:27 星期日
 * Description:
 */
public class SchedulerRunnable<T extends Job> implements Runnable {
    // 任务类
    private Class<T> task;
    // 执行时间
    private Calendar date;
    // 参数
    private Map<String, Object> param;

    public SchedulerRunnable(Class<T> task, Calendar date, Map<String, Object> param) {
        this.task = task;
        this.date = date;
        this.param = param;
    }

    @Override
    public void run() {
        try {
            // 获取Scheduler实例
            StdSchedulerFactory sf = new StdSchedulerFactory();
            Properties props = new Properties();
            props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, UUID.randomUUID().toString());
            props.put("org.quartz.threadPool.threadCount", "10");
            sf.initialize(props);
            Scheduler scheduler = sf.getScheduler();
            scheduler.setJobFactory((JobFactory) param.get("jobFactory"));
            //具体任务.
            JobDetail jobDetail = JobBuilder.newJob(task).build();
            jobDetail.getJobDataMap().putAll(param);
            //触发时间点.
            Trigger trigger = TriggerBuilder.newTrigger().startAt(date.getTime()).build();
            // 交由Scheduler安排触发
            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();// 开启定时任务调度器
            Thread.sleep(date.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
            scheduler.shutdown();//关闭定时任务调度器.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
