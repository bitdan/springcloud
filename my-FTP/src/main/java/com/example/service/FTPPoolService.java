package com.example.service;


import com.example.conf.ftp.FTPPoolConfig;
import org.apache.commons.net.ftp.FTPClient;

/**
 * 获取 ftp 客户端对象的接口
 */
public interface FTPPoolService {
    /**
     * 获取ftpClient
     */
    FTPClient borrowObject();

    /**
     * 归还ftpClient
     */
    void returnObject(FTPClient ftpClient);

    /**
     * 获取 ftp 配置信息
     * @return
     */
    FTPPoolConfig getFtpPoolConfig();
}
