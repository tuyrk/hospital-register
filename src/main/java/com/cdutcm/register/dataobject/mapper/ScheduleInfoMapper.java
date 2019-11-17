package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdutcm.register.dataobject.dto.ScheduleDTO;
import com.cdutcm.register.dataobject.entity.ScheduleInfo;
import com.cdutcm.register.dataobject.vo.admin.ScheduleAdminVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 12:05 星期日
 * Description:
 */
@Component
public interface ScheduleInfoMapper extends BaseMapper<ScheduleInfo> {
    @Select("SELECT " +
            "department_name, schedule_time, doctor_name " +
            "FROM schedule_info " +
            "LEFT JOIN doctor_info ON schedule_info.doctor_id = doctor_info.doctor_id " +
            "LEFT JOIN department_info ON doctor_info.department_id = department_info.department_id " +
            "WHERE schedule_time BETWEEN #{start} AND #{end} " +
            "ORDER BY schedule_time ASC ")
    List<ScheduleDTO> selectEffective(@Param("start") Date start, @Param("end") Date end);

    @Select("SELECT " +
            "schedule_id, " +
            "DATE_FORMAT(schedule_time, '%Y-%m-%d %p')        AS scheduleTime, " +
            "COUNT(DATE_FORMAT(schedule_time, '%Y-%m-%d %p')) AS intervalTime " +
            "FROM schedule_info " +
            "WHERE doctor_id = #{doctorId} " +
            "GROUP BY scheduleTime;")
    List<ScheduleAdminVO> selectByDoctorId(@Param("doctorId") String doctorId);
}
