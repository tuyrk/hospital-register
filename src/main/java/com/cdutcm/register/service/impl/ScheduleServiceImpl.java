package com.cdutcm.register.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdutcm.register.constant.ScheduleStatusConstant;
import com.cdutcm.register.dao.ScheduleInfoDao;
import com.cdutcm.register.dataobject.dto.ScheduleDTO;
import com.cdutcm.register.dataobject.entity.ScheduleInfo;
import com.cdutcm.register.dataobject.form.admin.ScheduleForm;
import com.cdutcm.register.dataobject.vo.admin.ScheduleAdminVO;
import com.cdutcm.register.dataobject.vo.patient.SchedulePatientVO;
import com.cdutcm.register.dataobject.vo.patient.SourceNumberDetailVO;
import com.cdutcm.register.dataobject.vo.patient.SourceNumberMasterVO;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.ScheduleService;
import com.cdutcm.register.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/12 13:24 星期二
 * Description:
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleInfoDao scheduleInfoDao;

    private Double scheduleMoney;
    private String amStart;
    private String amEnd;
    private String pmStart;
    private String pmEnd;

    public ScheduleServiceImpl(RegisterProperties registerProperties) {
        scheduleMoney = registerProperties.getSchedule().getMoney();
        amStart = registerProperties.getWorkhours().getAm().getStart();
        amEnd = registerProperties.getWorkhours().getAm().getEnd();
        pmStart = registerProperties.getWorkhours().getPm().getStart();
        pmEnd = registerProperties.getWorkhours().getPm().getEnd();
    }

    /**
     * 查找当前周排班表信息
     */
    @Override
    public List<SchedulePatientVO> getScheduleList() {
        List<ScheduleDTO> scheduleDTOList = scheduleInfoDao.selectScheduleTable();
        //排班信息为空。
        if (scheduleDTOList == null) {
            return null;
        }

        Object[] departmentNameSet = scheduleDTOList.stream().map(ScheduleDTO::getDepartmentName).distinct().toArray();

        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar benchmark = Calendar.getInstance();//基准日期，被比较日期
        Calendar scheduleTime = Calendar.getInstance();//比较日期
        // 查询的数据是按照scheduleTime排序的，即周一上午，周一下午，周二上午，周二下午...

        Set<String> nameSet;
        Map<String, Set<String>> dayMap;
        Map<String, Map> weekMap;
        List<Map> departmentList;
        SchedulePatientVO schedulePatientVO;
        List<SchedulePatientVO> schedulePatientVOList = new ArrayList<>();
        //循环科室名称数组列表
        for (Object departmentName : departmentNameSet) {
            schedulePatientVO = new SchedulePatientVO();

            nameSet = new HashSet<>();
            dayMap = new HashMap<>();
            weekMap = new HashMap<>();
            departmentList = new ArrayList<>();
            benchmark.setTime(scheduleDTOList.get(0).getScheduleTime());
            for (ScheduleDTO scheduleDTO : scheduleDTOList) {
                if (!departmentName.equals(scheduleDTO.getDepartmentName())) {
                    continue;
                }
                scheduleTime.setTime(scheduleDTO.getScheduleTime());
                if (!isSameDay(benchmark, scheduleTime)) {//如果不是同一个排班时间
                    //添加上午/下午的排班信息
                    boolean isAm = false;
                    if (benchmark.get(Calendar.AM_PM) == Calendar.AM) {
                        isAm = true;
                    }
                    String dayKey = isAm ? "上午" : "下午";
                    dayMap.put(dayKey, nameSet);

                    //添加星期的排班信息
                    if (benchmark.get(Calendar.DAY_OF_WEEK) != scheduleTime.get(Calendar.DAY_OF_WEEK)) {//如果不是同一天的排班
                        weekMap.put(weeks[benchmark.get(Calendar.DAY_OF_WEEK) - 1], dayMap);
                        departmentList.add(weekMap);
                        dayMap = new HashMap<>();
                        weekMap = new HashMap<>();
                    }
                    benchmark.setTime(scheduleTime.getTime());
                    nameSet = new HashSet<>();
                }
                //添加同一排班的医生姓名
                nameSet.add(scheduleDTO.getDoctorName());
            }


            //最后一条排班记录记录
            boolean isAm = false;
            if (benchmark.get(Calendar.AM_PM) == Calendar.AM) {
                isAm = true;
            }
            String dayKey = isAm ? "上午" : "下午";
            dayMap.put(dayKey, nameSet);
            weekMap.put(weeks[benchmark.get(Calendar.DAY_OF_WEEK) - 1], dayMap);
            departmentList.add(weekMap);

            schedulePatientVO.setDepartment(departmentName.toString());
            schedulePatientVO.setData(departmentList);
            schedulePatientVOList.add(schedulePatientVO);
        }
        return schedulePatientVOList;
    }

    //通过医生ID获取该医生的排班信息，即号源
    @Override
    public List<SourceNumberMasterVO> getScheduleByDoctorId(String doctorId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);//查询当前时间15分钟之后的号源数据。只能在15分钟之前预约
        LambdaQueryWrapper<ScheduleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleInfo::getDoctorId, doctorId)
                .gt(ScheduleInfo::getScheduleTime, calendar.getTime())
                .orderByAsc(ScheduleInfo::getScheduleTime);
        //查询数据
        List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.selectList(queryWrapper);

        //拼装返回结果信息
        List<SourceNumberMasterVO> sourceNumberMasterVOList = new ArrayList<>();
        SourceNumberMasterVO sourceNumberMasterVO;
        List<SourceNumberDetailVO> sourceNumberDetailVOList = new ArrayList<>();
        SourceNumberDetailVO sourceNumberDetailVO;

        if (scheduleInfoList.size() == 0) {//排班信息为空
            return null;
        }

        Calendar benchmark = Calendar.getInstance();//基准日期，被比较日期
        Calendar scheduleTime = Calendar.getInstance();//比较日期
        benchmark.setTime(scheduleInfoList.get(0).getScheduleTime());
        int count = 1;
        int remainder = 0;
        for (int i = 0; i < scheduleInfoList.size(); i++) {
            scheduleTime.setTime(scheduleInfoList.get(i).getScheduleTime());
            if (!isSameDay(benchmark, scheduleTime)) {//如果不是同一个排班时间

                sourceNumberMasterVO = new SourceNumberMasterVO();
                //必须要重新new一个对象。不然在for循环中添加的值是同一个。
                Calendar temp = Calendar.getInstance();
                temp.setTime(benchmark.getTime());
                sourceNumberMasterVO.setDatetime(temp);//设置排班时间
                sourceNumberMasterVO.setMoney(scheduleInfoList.get(i - 1).getScheduleMoney());//设置挂号费用
                sourceNumberMasterVO.setRemainder(remainder);//设置剩余号源数
                sourceNumberMasterVO.setSourceNumberDetailVOList(sourceNumberDetailVOList);//号源列表
                sourceNumberMasterVOList.add(sourceNumberMasterVO);//添加进排班列表

                benchmark.setTime(scheduleTime.getTime());//日期基准以当前的key为基准
                count = 1;//View ID重新置为1
                remainder = 0;//剩余数置为0

                sourceNumberDetailVOList = new ArrayList<>();
            }

            if (ScheduleStatusConstant.UNORDER == scheduleInfoList.get(i).getStatus()) {//如果未预约，则剩余数+1
                remainder++;
            }

            //拼装号源
            sourceNumberDetailVO = new SourceNumberDetailVO();
            sourceNumberDetailVO.setId(count++);//View ID
            sourceNumberDetailVO.setScheduleId(scheduleInfoList.get(i).getScheduleId());//号源ID
            sourceNumberDetailVO.setStatus(scheduleInfoList.get(i).getStatus());//号源状态，是否被预约
            sourceNumberDetailVO.setTime(scheduleInfoList.get(i).getScheduleTime());//号源时间
            sourceNumberDetailVOList.add(sourceNumberDetailVO);//添加进号源列表
        }

        //设置最后一条排班记录的号源信息
        sourceNumberMasterVO = new SourceNumberMasterVO();
        sourceNumberMasterVO.setDatetime(benchmark);//设置排班时间
        sourceNumberMasterVO.setMoney(scheduleInfoList.get(scheduleInfoList.size() - 1).getScheduleMoney());//设置挂号费用
        sourceNumberMasterVO.setRemainder(remainder);//设置剩余号源数
        sourceNumberMasterVO.setSourceNumberDetailVOList(sourceNumberDetailVOList);//号源列表
        sourceNumberMasterVOList.add(sourceNumberMasterVO);//添加进排班列表

        return sourceNumberMasterVOList;
    }


    //判断两个时间是同一天的同上下午
    private boolean isSameDay(Calendar day1, Calendar day2) {
        return day1 != null && day2 != null//判断两个日期均不为空
                && day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)//判断年份相同
                && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)//判断月份相同
                && day1.get(Calendar.DAY_OF_MONTH) == day2.get(Calendar.DAY_OF_MONTH)//判断日期相同
                && day1.get(Calendar.AM_PM) == day2.get(Calendar.AM_PM);//判断上午/下午相同
    }

    @Override
    public void maintainSchedule(List<ScheduleForm> scheduleFormList) {
        List<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        scheduleFormList.forEach(scheduleForm -> {
            ScheduleInfo scheduleInfo;
            //排班
            Calendar start = getScheduleTime(scheduleForm.getScheduleTime(), amStart, pmStart);//上班开始时间
            // 查询新增排班是否已经被排班.当前医生的当前时间
            if (scheduleInfoDao.selectBySchedule(scheduleForm.getDoctorId(), start) != null) {
                log.error("【排班维护】排班信息已存在，scheduleInfoList = {}", scheduleInfoList);
                throw new RegisterException(ResultEnum.SCHEDULE_IS_EXIST);
            }

            Calendar end = getScheduleTime(scheduleForm.getScheduleTime(), amEnd, pmEnd);//上班结束时间
            while (start.before(end)) {
                scheduleInfo = new ScheduleInfo();
                scheduleInfo.setDoctorId(scheduleForm.getDoctorId());//号源医师
                scheduleInfo.setScheduleMoney(scheduleMoney);//号源金额
                scheduleInfo.setScheduleId(KeyUtil.getUniqueKeyTime());//号源ID
                scheduleInfo.setScheduleTime(start.getTime());//号源时间
                scheduleInfoList.add(scheduleInfo);
                start.add(Calendar.MINUTE, scheduleForm.getIntervalTime());//当前时间增加间隔时间，则获得下一个号源的时间
            }
            //号源状态，初始化为0-false未使用；1-true已使用
        });
        try {
            scheduleInfoDao.insert(scheduleInfoList);
        } catch (Exception e) {
            log.error("【排班维护】添加排班信息错误，scheduleInfoList = {}", scheduleInfoList);
            throw new RegisterException(ResultEnum.SCHEDULE_SAVE_ERROR);
        }
    }

    @Override
    public List<ScheduleAdminVO> getSchedule(String doctorId) {
        List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.getSchedules(doctorId);
        if (scheduleInfoList == null || scheduleInfoList.size() == 0) {
            return null;
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        List<ScheduleAdminVO> scheduleList = new ArrayList<>();
        Calendar curDate = null;
        for (int i = 0; i < scheduleInfoList.size(); i++) {
            ScheduleAdminVO scheduleVO = new ScheduleAdminVO();
            Date currDate = scheduleInfoList.get(i).getScheduleTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDate);
            if (isSameDay(curDate, calendar)) {
                continue;
            }
            curDate = calendar;
            // scheduleId
            scheduleVO.setScheduleId(scheduleInfoList.get(i).getScheduleId());
            // scheduleTime
            String scheduleTime = sdfDate.format(currDate);

            if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                scheduleVO.setScheduleTime(scheduleTime.concat(" 上午"));
            } else if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                scheduleVO.setScheduleTime(scheduleTime.concat(" 下午"));
            }

            // intervalTime
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(currDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(scheduleInfoList.get(i + 1).getScheduleTime());
            scheduleVO.setIntervalTime((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / 60000);
            scheduleList.add(scheduleVO);
        }
        return scheduleList;
    }

    /**
     * 将ScheduleForm中的scheduleTime转换成Calendar
     *
     * @param scheduleTime
     * @param am
     * @param pm
     * @return
     */
    private Calendar getScheduleTime(String scheduleTime, String am, String pm) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date;
            if (scheduleTime.endsWith("上午")) {
                date = sdf.parse(scheduleTime.replace("上午", am));//上午开始/结束时间
            } else if (scheduleTime.endsWith("下午")) {
                date = sdf.parse(scheduleTime.replace("下午", pm));//下午开始/结束时间
            } else {
                throw new Exception();
            }
            calendar.setTime(date);
        } catch (Exception e) {
            log.error("【格式转换错误】scheduleTime = {}", scheduleTime);
            throw new RegisterException(ResultEnum.FORMAT_CONVERT_ERROR);
        }
        return calendar;
    }
}
/*
    public List<ScheduleAdminVO> getSchedule(String doctorId) {
        List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.selectByDoctorId(doctorId);
        List<ScheduleAdminVO> scheduleAdminVOList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 数据去重。同一时间仅保留一条
        Map<String, String> map = new HashMap<>();
        scheduleInfoList.forEach(e -> {
            // 将Date转换为“YYYY-MM-DD 上午”格式
            calendar.setTime(e.getScheduleTime());
            String scheduleTime = sdf.format(calendar.getTime());
            if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                scheduleTime = scheduleTime.concat(" 上午");
            } else if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                scheduleTime = scheduleTime.concat(" 下午");
            }

            // Map中不存在排班则添加记录。达到去重的目的
            if (!map.containsKey(scheduleTime)) { // 不存在排班，则添加记录数据
                map.put(scheduleTime, e.getScheduleId());
                scheduleAdminVOList.add(new ScheduleAdminVO(e.getScheduleId(), scheduleTime));
            }
        });
        return scheduleAdminVOList;
    }
 */
