package com.cdutcm.register.utils.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.register.dataobject.form.PageForm;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 0:11 星期日
 * Description:
 */
public class PageForm2Page {
    public static <T> Page<T> convert(PageForm pageForm, Integer page, Integer size) {
        if (pageForm.getPage() == null) {
            pageForm.setPage(page);
        }
        if (pageForm.getSize() == null) {
            pageForm.setSize(size);
        }
        return new Page<>(pageForm.getPage(), pageForm.getSize());
    }
}
