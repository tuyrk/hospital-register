package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.entity.DepartmentInfo;
import com.cdutcm.register.dataobject.mapper.DepartmentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 11:00 星期日
 * Description:
 */
@Component
public class DepartmentInfoDao {
    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    public Boolean insert(DepartmentInfo departmentInfo) {
        return departmentInfoMapper.insert(departmentInfo) == 1;
    }

    public Boolean deleteById(String departmentId) {
        return departmentInfoMapper.deleteById(departmentId) > 0;
    }

    public Boolean updateById(DepartmentInfo departmentInfo) {
        return departmentInfoMapper.updateById(departmentInfo) == 1;
    }

    public DepartmentInfo selectById(String departmentId) {
        return departmentInfoMapper.selectById(departmentId);
    }

    public IPage<DepartmentInfo> selectPage(Page<DepartmentInfo> page, LambdaQueryWrapper<DepartmentInfo> queryWrapper) {
        return departmentInfoMapper.selectPage(page, queryWrapper);
    }

    public List<DepartmentInfo> selectAllList() {
        return departmentInfoMapper.selectList(null);
    }

    public DepartmentInfo selectByDepartmentName(String departmentName) {
        return departmentInfoMapper.selectOne(new LambdaQueryWrapper<DepartmentInfo>()
                .eq(DepartmentInfo::getDepartmentName, departmentName));
    }

    public Map<String, Integer> selectDepartmentMapNameId() {
        List<Map<String, Integer>> departmentMapNameId = departmentInfoMapper.selectDepartmentMapNameId();
        Map<String, Integer> map = new HashMap<>();
        for (Map<String, Integer> aDepartmentMapNameId : departmentMapNameId) {
            map.put("" + aDepartmentMapNameId.get("department_name"), aDepartmentMapNameId.get("department_id"));
        }
        return map;
    }

    public Map<Integer, String> selectDepartmentMapIdName() {
        List<Map<String, Integer>> departmentMapNameId = departmentInfoMapper.selectDepartmentMapNameId();
        Map<Integer, String> map = new HashMap<>();
        for (Map<String, Integer> aDepartmentMapNameId : departmentMapNameId) {
            map.put(aDepartmentMapNameId.get("department_id"), "" + aDepartmentMapNameId.get("department_name"));
        }
        return map;
    }

    public List<String> selectDepartmentNumber() {
        return departmentInfoMapper.selectDepartmentNumber();
    }

    public Integer selectDepartmentIdByName(String departmentName) {
        return departmentInfoMapper.selectDepartmentIdByName(departmentName);
    }
}
