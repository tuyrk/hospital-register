package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.utils.serializer.SourceNumberMasterDate2String;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/27 14:59 星期三
 * Description:
 */
@Data
public class SourceNumberMasterVO {
    @JsonSerialize(using = SourceNumberMasterDate2String.class)
    private Calendar datetime;
    private Double money;
    private Integer remainder;
    @JsonProperty("sourceNumber")
    private List<SourceNumberDetailVO> sourceNumberDetailVOList;
}
