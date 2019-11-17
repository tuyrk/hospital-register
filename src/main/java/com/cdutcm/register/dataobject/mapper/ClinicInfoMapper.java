package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdutcm.register.dataobject.entity.ClinicInfo;
import com.cdutcm.register.dataobject.form.admin.ClinicForm;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 12:04 星期日
 * Description:
 */
@Component
public interface ClinicInfoMapper extends BaseMapper<ClinicInfo> {
    @Select("SELECT floor_synopsis, clinic_position FROM clinic_info WHERE clinic_id = #{clinicId}")
    ClinicForm selectFloorDetailById(Integer clinicId);
    @Select("SELECT clinic_synopsis FROM clinic_info WHERE clinic_id = #{clinicId}")
    ClinicForm selectSynopsisById(Integer clinicId);
}
