package com.cdutcm.register.utils.serializer;

import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/27 19:10 星期三
 * Description:
 * 将Date转换为‘2019-03-27 下午’
 */
@Slf4j
public class SourceNumberMasterDate2String extends JsonSerializer<Calendar> {
    @Override
    public void serialize(Calendar value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String datetimeString = sdf.format(value.getTime());
            if (value.get(Calendar.AM_PM) == Calendar.AM) {
                datetimeString = datetimeString.concat(" 上午");
            } else if (value.get(Calendar.AM_PM) == Calendar.PM) {
                datetimeString = datetimeString.concat(" 下午");
            }
            gen.writeString(datetimeString);
        } catch (Exception e) {
            log.error("【格式转换错误】value = {}", value);
            throw new RegisterException(ResultEnum.FORMAT_CONVERT_ERROR);
        }
    }
}
