package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.utils.serializer.SourceNumber2RemainderSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/13 12:03 星期三
 * Description:
 */
@Data
public class DoctorVO {
    private String doctorId;
    private String doctorPhoto;
    private String doctorName;
    private String doctorPost;
    private String doctorAdept;
    @JsonProperty("remainder")
    @JsonSerialize(using = SourceNumber2RemainderSerializer.class)
    private String sourceNumber;
}
