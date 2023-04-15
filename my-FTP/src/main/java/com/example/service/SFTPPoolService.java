package com.example.service;


import com.example.conf.SFTPPoolConfig;
import com.jcraft.jsch.ChannelSftp;

/**
 * 获取 sftp 客户端对象的接口
 */
public interface SFTPPoolService {
    /**
     * 获取 sftp
     */
    ChannelSftp borrowObject() ;

    /**
     * 归还 sftp
     */
    void returnObject(ChannelSftp channelSftp);

    /**
     * 获取 ftp 配置信息
     * @return
     */
    SFTPPoolConfig getFtpPoolConfig();
}
