package com.cdutcm.register.dataobject.vo.patient;

import com.cdutcm.register.dataobject.vo.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 0:42 星期日
 * Description:
 */
@Data
public class PatientVO {
    public interface ListView extends BaseView{
    }

    @JsonView(ListView.class)
    private String patientId;
    @JsonView(ListView.class)
    private String patientName;
    @JsonView(ListView.class)
    private String patientCard;
    private String patientPhone;
    private String patientMail;
    private String openid;
}
