package com.cdutcm.register.controller.patient;

import com.cdutcm.register.RegisterApplicationTests;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class WechatControllerTest extends RegisterApplicationTests {
    @Autowired
    private WxMpService wxMpService;

    @Test
    public void test() throws WxErrorException {
        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> wxMenuButtonList = new ArrayList<>();

        WxMenuButton button1 = new WxMenuButton();
        button1.setName("项目主页");
        button1.setUrl("http://wangmao.natapp1.cc/wechat/authorize");
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setKey("1");

        Collections.addAll(wxMenuButtonList, button1);
        wxMenu.setButtons(wxMenuButtonList);
        wxMpService.getMenuService().menuCreate(wxMenu);
    }

}