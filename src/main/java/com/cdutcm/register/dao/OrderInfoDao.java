package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.dto.CensusDTO;
import com.cdutcm.register.dataobject.entity.OrderInfo;
import com.cdutcm.register.dataobject.mapper.OrderInfoMapper;
import com.cdutcm.register.dataobject.vo.patient.OrderVO;
import com.cdutcm.register.dataobject.vo.patient.PaidVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 11:01 星期日
 * Description:
 */
@Component
public class OrderInfoDao {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public Boolean insert(OrderInfo orderInfo) {
        return orderInfoMapper.insert(orderInfo) == 1;
    }

    public Boolean deleteById(String orderId) {
        return orderInfoMapper.deleteById(orderId) > 0;
    }

    public Boolean updateById(OrderInfo orderInfo) {
        return orderInfoMapper.updateById(orderInfo) == 1;
    }

    public OrderInfo selectOne(LambdaQueryWrapper<OrderInfo> queryWrapper) {
        return orderInfoMapper.selectOne(queryWrapper);
    }

    public OrderInfo selectById(String orderId) {
        System.out.println("OrderInfoDao-selectById-orderId = " + orderId);
        return orderInfoMapper.selectById(orderId);
    }

    public IPage<OrderInfo> selectPage(Page<OrderInfo> page, LambdaQueryWrapper<OrderInfo> queryWrapper) {
        return orderInfoMapper.selectPage(page, queryWrapper);
    }

    public OrderVO selectOrderVOById(String orderId) {
        return orderInfoMapper.selectOrderById(orderId);
    }

    public List<OrderVO> selectByOpenid(String openid) {
        return orderInfoMapper.selectByOpenid(openid);
    }

    public List<PaidVO> selectPaidByOpenid(String openid) {
        return orderInfoMapper.selectPaidByOpenid(openid);
    }

    public List<PaidVO> selectPayingByOpenid(String openid) {
        return orderInfoMapper.selectPayingByOpenid(openid);
    }

    public Map<String, Integer> selectOrderCensus(String month) {
        return orderInfoMapper.selectOrderCensus(month).stream()
                .collect(Collectors.toMap(CensusDTO::getDepartmentName, CensusDTO::getCount));
    }
}
