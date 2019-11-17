package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdutcm.register.dataobject.vo.patient.DoctorVO;
import com.cdutcm.register.dataobject.entity.DoctorInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 12:04 星期日
 * Description:
 */
@Component
public interface DoctorInfoMapper extends BaseMapper<DoctorInfo> {
    // 根据科室ID，获取时间之后的所有已排班医生的列表
    @Select("SELECT DISTINCT " +
            "d.doctor_id, doctor_photo, doctor_name, doctor_post, doctor_adept " +
            "FROM doctor_info d, schedule_info s " +
            "WHERE s.doctor_id = d.doctor_id AND s.schedule_time > #{datetime} AND department_id = #{departmentId} AND d.status = 1")
    List<DoctorVO> selectSchedule(@Param("departmentId") String departmentId, @Param("datetime") Date datetime);

    @Insert("<script>" +
            "INSERT INTO doctor_info(doctor_id, doctor_photo, doctor_name, doctor_sex, doctor_card, department_id, doctor_phone, doctor_mail, doctor_post, doctor_adept, doctor_detail, username, password) VALUES " +
            "<foreach collection = 'entityList' item = 'entity' separator = ','>" +
            "(#{entity.doctorId}, #{entity.doctorPhoto, jdbcType=VARCHAR}, #{entity.doctorName}, #{entity.doctorSex, jdbcType=NUMERIC}, #{entity.doctorCard}, #{entity.departmentId}, #{entity.doctorPhone, jdbcType=VARCHAR}, #{entity.doctorMail, jdbcType=VARCHAR}, #{entity.doctorPost, jdbcType=VARCHAR}, #{entity.doctorAdept, jdbcType=VARCHAR}, #{entity.doctorDetail, jdbcType=VARCHAR}, #{entity.username}, #{entity.password})" +
            "</foreach>" +
            "</script>")
    Boolean insertBatch(@Param("entityList") Collection<DoctorInfo> entityList);

    // 仅查询status为1存在的医生名字。0删除
    @Select("SELECT username FROM doctor_info WHERE status = 1")
    List<String> selectAllUsername();

    @Select("SELECT doctor_id FROM doctor_info WHERE doctor_name = #{doctorName}")
    List<String> selectDoctorIdByName(String doctorName);
}
