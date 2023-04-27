package com.example.conf.satoken;

/**
 * @author duran
 * @description TODO
 * @date 2023-04-27
 */

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    // 返回一个账号所拥有的权限码集合
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Arrays.asList("101", "user-add", "user-delete", "user-update", "user-get", "article-get");
    }

    // 返回一个账号所拥有的角色标识集合
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Arrays.asList("admin", "super-admin");
    }

}