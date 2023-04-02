package com.example.service;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duran
 * @description TODO
 * @date 2023-01-06 17:42
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        ArrayList<String> list = new ArrayList<>();
        list.add("10001");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        list.add("art.*");
        System.out.println(list);
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }
}
