package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.dto.ScheduleDTO;
import com.cdutcm.register.dataobject.entity.ScheduleInfo;
import com.cdutcm.register.dataobject.mapper.ScheduleInfoMapper;
import com.cdutcm.register.dataobject.service.ScheduleInfoService;
import com.cdutcm.register.dataobject.vo.admin.ScheduleAdminVO;
import com.cdutcm.register.utils.WeekStartEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 11:02 星期日
 * Description:
 */
@Component
public class ScheduleInfoDao {
    @Autowired
    private ScheduleInfoMapper scheduleInfoMapper;
    @Autowired
    private ScheduleInfoService scheduleInfoService;

    public Boolean insert(ScheduleInfo scheduleInfo) {
        return scheduleInfoMapper.insert(scheduleInfo) == 1;
    }

    public Boolean insert(List<ScheduleInfo> scheduleInfoList) {
        return scheduleInfoService.saveBatch(scheduleInfoList);
    }

    public Boolean deleteById(String scheduleId) {
        return scheduleInfoMapper.deleteById(scheduleId) > 0;
    }

    public Boolean updateById(ScheduleInfo scheduleInfo) {
        return scheduleInfoMapper.updateById(scheduleInfo) == 1;
    }

    public ScheduleInfo selectById(String scheduleId) {
        return scheduleInfoMapper.selectById(scheduleId);
    }

    public IPage<ScheduleInfo> selectPage(Page<ScheduleInfo> page, LambdaQueryWrapper<ScheduleInfo> queryWrapper) {
        return scheduleInfoMapper.selectPage(page, queryWrapper);
    }

    public List<ScheduleInfo> selectList(LambdaQueryWrapper<ScheduleInfo> queryWrapper) {
        return scheduleInfoMapper.selectList(queryWrapper);
    }

    public List<ScheduleDTO> selectScheduleTable() {
        //周一到周天的所有排班信息
        return scheduleInfoMapper.selectEffective(WeekStartEnd.getCurrentMonday(), WeekStartEnd.getPreviousSunday());
    }

    public List<ScheduleInfo> selectByDoctorId(String doctorId) {
        return scheduleInfoMapper.selectList(new QueryWrapper<ScheduleInfo>().lambda()
                .eq(ScheduleInfo::getDoctorId, doctorId));
    }

    public ScheduleInfo selectBySchedule(String doctorId, Calendar calendar) {
        return scheduleInfoMapper.selectOne(new LambdaQueryWrapper<ScheduleInfo>()
                .eq(ScheduleInfo::getScheduleTime, calendar.getTime())
                .eq(ScheduleInfo::getDoctorId, doctorId));
    }

    public List<ScheduleInfo> getSchedules(String doctorId) {
        return scheduleInfoMapper.selectList(new LambdaQueryWrapper<ScheduleInfo>()
                .eq(ScheduleInfo::getDoctorId, doctorId));
    }
}
