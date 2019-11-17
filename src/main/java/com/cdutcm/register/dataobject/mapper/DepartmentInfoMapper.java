package com.cdutcm.register.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdutcm.register.dataobject.entity.DepartmentInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/3 12:04 星期日
 * Description:
 */
@Component
public interface DepartmentInfoMapper extends BaseMapper<DepartmentInfo> {
    @Select("SELECT department_id, department_name FROM department_info")
    List<Map<String,Integer>> selectDepartmentMapNameId();

    @Select("SELECT department_number FROM department_info")
    List<String> selectDepartmentNumber();

    @Select("SELECT department_id FROM department_info WHERE department_name = #{departmentName}")
    Integer selectDepartmentIdByName(String departmentName);
}
