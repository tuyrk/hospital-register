package com.cdutcm.register.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.register.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.register.dataobject.form.admin.AdminConditionForm;
import com.cdutcm.register.dataobject.form.admin.AdminForm;
import com.cdutcm.register.dataobject.form.PageForm;
import com.cdutcm.register.dataobject.vo.admin.AdminVO;

import javax.validation.Valid;
import java.util.Map;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 12:34 星期五
 * Description:
 */
public interface AdminService {
    void saveAdmin(AdminForm adminForm);

    void deleteAdmin(String adminId);

    IPage<AdminVO> getAdminList(AdminConditionForm adminCondition, PageForm page);

    //验证登录，返回token
    Map<String, String> checkLogin(AdminAuthForm adminAuthForm);
}
