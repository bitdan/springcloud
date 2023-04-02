package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duran
 * @description TODO
 * @date 2023-01-06 17:28
 */
@RestController
@RequestMapping("/acc/")
public class LoginController {
    @RequestMapping("doLogin")
    public SaResult doLogin(String name, String pwd) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(name) && "123456".equals(pwd)) {
            StpUtil.login(10001);
            return SaResult.ok("登录成功");
        }
        return SaResult.error("登录失败");
    }

    // 查询登录状态  ---- http://localhost:8081/acc/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }
    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo(){
        return SaResult.data(StpUtil.getTokenInfo());
    }
    @RequestMapping("/logout")
    public SaResult logout(){
        StpUtil.logout();
        return SaResult.ok();
    }
}
