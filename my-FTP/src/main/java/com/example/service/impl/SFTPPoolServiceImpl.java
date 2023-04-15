package com.example.service.impl;


import com.example.conf.ChannelSftpFactory;
import com.example.conf.SFTPPoolConfig;
import com.example.service.SFTPPoolService;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class SFTPPoolServiceImpl implements SFTPPoolService {

    /**
     * ftp 连接池生成
     */
    private GenericObjectPool<ChannelSftp> pool;

    /**
     * ftp 客户端配置文件
     */
    @Autowired
    private SFTPPoolConfig config;

    /**
     * ftp 客户端工厂
     */
    @Autowired
    private ChannelSftpFactory factory;

    /**
     * 初始化pool
     */
    @PostConstruct
    private void initPool() {
        this.pool = new GenericObjectPool<ChannelSftp>(this.factory, this.config);
    }

    /**
     * 获取sftp
     */
    @Override
    public ChannelSftp borrowObject() {
        if (this.pool != null) {
            try {
                return this.pool.borrowObject();
            } catch (Exception e) {
                log.error("获取 ChannelSftp 失败", e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 归还 sftp
     */
    @Override
    public void returnObject(ChannelSftp channelSftp) {
        if (this.pool != null && channelSftp != null) {
            this.pool.returnObject(channelSftp);
        }
    }

    @Override
    public SFTPPoolConfig getFtpPoolConfig() {
        return config;
    }
}
