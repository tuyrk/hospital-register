package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.utils.serializer.SourceNumberDetailDate2String;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/27 14:55 星期三
 * Description:
 */
@Data
public class SourceNumberDetailVO {
    //View ID
    private Integer id;
    //号源ID
    private String scheduleId;
    //号源时间
    @JsonSerialize(using = SourceNumberDetailDate2String.class)
    private Date time;
    //号源状态（false未使用；true已使用）
    private Boolean status;
}
