package com.cdutcm.register.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/5/13 0:33 星期一
 * Description:
 */
@Component("jobFactory")
public class JobFactory extends SpringBeanJobFactory {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object object = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(object);
        return object;
    }
}
