package com.cdutcm.register.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 18:09 星期三
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "register")
public class RegisterProperties {
    private WorkingHoursProperties workhours = new WorkingHoursProperties();
    private PageProperties page = new PageProperties();
    private ClinicProperties clinic = new ClinicProperties();
    private AdminProperties admin = new AdminProperties();
    private DoctorProperties doctor = new DoctorProperties();
    private StorageProperties storage = new StorageProperties();
    private ScheduleProperties schedule = new ScheduleProperties();
    private WeChatAccountProperties wechat = new WeChatAccountProperties();
    private ProjectUrlProperties url = new ProjectUrlProperties();
}
