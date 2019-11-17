package com.cdutcm.register.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.entity.AdminInfo;
import com.cdutcm.register.dataobject.mapper.AdminInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/10 10:57 星期日
 * Description:
 */
@Component
public class AdminInfoDao {
    @Autowired
    private AdminInfoMapper adminInfoMapper;

    public Boolean insertAdmin(AdminInfo adminInfo) {
        return adminInfoMapper.insert(adminInfo) == 1;
    }

    public Boolean deleteById(String adminId) {
        return adminInfoMapper.deleteById(adminId) > 0;
    }

    public Boolean updateById(AdminInfo adminInfo) {
        return adminInfoMapper.updateById(adminInfo) == 1;
    }


    public AdminInfo selectOne(LambdaQueryWrapper<AdminInfo> queryWrapper) {
        return adminInfoMapper.selectOne(queryWrapper);
    }

    public AdminInfo selectById(String adminId) {
        return adminInfoMapper.selectById(adminId);
    }

    public IPage<AdminInfo> selectPage(Page<AdminInfo> page, LambdaQueryWrapper<AdminInfo> queryWrapper) {
        return adminInfoMapper.selectPage(page, queryWrapper);
    }

    public AdminInfo selectByUsername(String username) {
        return adminInfoMapper.selectOne(new LambdaQueryWrapper<AdminInfo>()
                .eq(AdminInfo::getUsername, username));
    }
}
