package com.example.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duran
 * @description TODO
 * @date 2023-04-27
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


    // 查询 Token 信息  ---- http://localhost:8081/user/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

//    http://localhost:8081/user/encryption
    @RequestMapping("encryption")
    public SaResult encryption() {
        String s = SaSecureUtil.md5("123456");
        return SaResult.data(s);
    }

    //    http://localhost:8081/user/sessionIdList
    @RequestMapping("sessionIdList ")
    public SaResult sessionIdList() {
        // 获取所有已登录的会话id
        List<String> sessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        ArrayList<List> tokenSigns = new ArrayList<>();
        for (String sessionId : sessionIdList) {
            // 根据会话id，查询对应的 SaSession 对象，此处一个 SaSession 对象即代表一个登录的账号
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            // 查询这个账号都在哪些设备登录了，依据上面的示例，账号A 的 tokenSign 数量是 3，账号B 的 tokenSign 数量是 2
//            List<TokenSign> tokenSignList = session.getTokenSignList();
//            System.out.println("会话id：" + sessionId + "，共在 " + tokenSignList.size() + " 设备登录");
            tokenSigns.add(session.getTokenSignList());
        }
        return SaResult.data(tokenSigns);
    }


    // 测试注销  ---- http://localhost:8081/user/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}
