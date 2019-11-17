package com.cdutcm.register.utils.serializer;

import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 18:11 星期四
 * Description:
 */
public class Date2StringSerializer extends JsonSerializer<Date> {
    private Logger log = LoggerFactory.getLogger(Date2StringSerializer.class);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            jsonGenerator.writeString(sdf.format(date));
        } catch (Exception e) {
            log.error("【格式转换错误】date = {}", date);
            throw new RegisterException(ResultEnum.FORMAT_CONVERT_ERROR);
        }
    }
}
