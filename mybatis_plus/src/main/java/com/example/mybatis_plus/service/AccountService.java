package com.example.mybatis_plus.service;


import com.example.mybatis_plus.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    public Account findByUsername(String username);
}