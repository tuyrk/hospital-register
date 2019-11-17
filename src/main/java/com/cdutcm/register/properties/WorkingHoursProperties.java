package com.cdutcm.register.properties;

import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 18:12 星期三
 * Description:
 */
@Data
public class WorkingHoursProperties {
    private WorkTimeProperties am = new WorkTimeProperties();
    private WorkTimeProperties pm = new WorkTimeProperties();
}
