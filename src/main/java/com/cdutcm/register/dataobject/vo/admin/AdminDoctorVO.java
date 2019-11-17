package com.cdutcm.register.dataobject.vo.admin;

import com.cdutcm.register.dataobject.vo.BaseView;
import com.cdutcm.register.enums.SexEnum;
import com.cdutcm.register.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/16 18:58 星期六
 * Description:
 */
@Data
public class AdminDoctorVO {
    public interface DoctorSimpleView extends BaseView {
    }

    public interface DoctorDetailView extends DoctorSimpleView {
    }

    public interface DoctorInfoView extends DoctorSimpleView{
    }

    @JsonView(DoctorSimpleView.class)
    private String doctorId;
    @JsonView(DoctorSimpleView.class)
    private String username;
    @JsonView(DoctorSimpleView.class)
    private String doctorName;
    @JsonIgnore
    private Integer doctorSex;
    @JsonView(DoctorDetailView.class)
    private String doctorCard;
    @JsonView(DoctorSimpleView.class)
    private String department;
    @JsonView(DoctorSimpleView.class)
    private String doctorPhone;
    @JsonView(DoctorInfoView.class)
    private String doctorPost;
    @JsonView(DoctorInfoView.class)
    private String doctorAdept;
    @JsonView(DoctorInfoView.class)
    private String doctorDetail;

    @JsonProperty("adminSex")
    @JsonView(DoctorDetailView.class)
    public String getDoctorSex() {
        if (doctorSex == null) {
            return null;
        }
        return EnumUtil.getByCode(doctorSex, SexEnum.class).getMsg();
    }
}