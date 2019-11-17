package com.cdutcm.register.dao;

import com.cdutcm.register.RegisterApplicationTests;
import com.cdutcm.register.dataobject.dto.CensusDTO;
import com.cdutcm.register.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderInfoDaoTest extends RegisterApplicationTests {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Test
    public void selectOrderCensus() {
        Map<String, Integer> censusDTOList = orderInfoDao.selectOrderCensus("201902");
        System.out.println("censusDTOList = " + GsonUtil.prettyPrinting(censusDTOList));
    }
}
/*
Could not set parameters for mapping:
ParameterMapping{property='month', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}.
Cause: org.apache.ibatis.type.TypeException:
Error setting non null for parameter #1 with JdbcType null .
Try setting a different JdbcType for this parameter or a different configuration property.
Cause: java.sql.SQLException: Parameter index out of range (1 > number of parameters, which is 0).
 */