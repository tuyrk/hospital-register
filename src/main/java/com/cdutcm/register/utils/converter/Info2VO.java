package com.cdutcm.register.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/16 22:33 星期六
 * Description:
 */
@Slf4j
public class Info2VO {
    public static <T, Q> Q convert(T info, Class<Q> vo) {
        Q voObj;
        try {
            voObj = vo.newInstance();
            BeanUtils.copyProperties(info, voObj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【对象类型转换失败】info = {}，vo = {}", info, vo);
            throw new RegisterException(ResultEnum.FORMAT_CONVERT_ERROR);
        }
        return voObj;
    }

    public static <T, Q> List<Q> convert(List<T> infoList, Class<Q> vo) {
        List<Q> voList = new ArrayList<>();
        for (T info : infoList) {
            voList.add(convert(info, vo));
        }
        return voList;
    }

    public static <T, Q> IPage<Q> convert(IPage<T> infoPage, Class<Q> vo) {
        IPage<Q> voPage = new Page<>();
        BeanUtils.copyProperties(infoPage, voPage);
        voPage.setRecords(convert(infoPage.getRecords(), vo));
        return voPage;
    }
}
