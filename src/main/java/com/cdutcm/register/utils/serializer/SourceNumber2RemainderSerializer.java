package com.cdutcm.register.utils.serializer;

import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/14 18:44 星期四
 * Description:
 */
public class SourceNumber2RemainderSerializer extends JsonSerializer<String> {
    private Logger log = LoggerFactory.getLogger(Date2StringSerializer.class);
    @Override
    public void serialize(String sourceNumber, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            jsonGenerator.writeNumber(sourceNumber.split("-")[1]);
        } catch (IOException e) {
            log.error("【参数错误】sourceNumber = {}", sourceNumber);
            throw new RegisterException(ResultEnum.PARAM_ERROR);
        }
    }
}
