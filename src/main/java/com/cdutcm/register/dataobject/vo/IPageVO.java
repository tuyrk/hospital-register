package com.cdutcm.register.dataobject.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/17 12:49 星期日
 * Description:
 */
@JsonView(BaseView.class)
public interface IPageVO<T> extends IPage<T> {
}
