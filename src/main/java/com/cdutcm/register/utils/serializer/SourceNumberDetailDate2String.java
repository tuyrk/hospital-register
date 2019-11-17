package com.cdutcm.register.utils.serializer;

import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/27 19:18 星期三
 * Description:
 */
@Slf4j
public class SourceNumberDetailDate2String extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            gen.writeString(sdf.format(value));
        } catch (Exception e) {
            log.error("【格式转换错误】value = {}", value);
            throw new RegisterException(ResultEnum.FORMAT_CONVERT_ERROR);
        }
    }
}
