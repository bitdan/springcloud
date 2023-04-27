package com.example.conf.ftp;


import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties
@PropertySource(value = {"sftp.properties"})
@ConfigurationProperties(prefix = "sftp.client")
@Configuration
@Data
public class SFTPPoolConfig extends GenericObjectPoolConfig {

    // 默认进入的路径
    String workingDirectory;
    // 主机地址
    String host;
    // 主机端口
    int port;
    // 主机用户名
    String username;
    // 主机密码
    String password;
    // 主机密码
    String privateKey;
    // 传输编码
    String encoding;
    // 连接超时时间
    int clientTimeout;

    // 重新连接时间
    int retryTimes;
    // 缓存大小
    int bufferSize;
    // 最大数
    int maxTotal;
    // 最小空闲
    int minldle;
    // 最大空闲
    int maxldle;
    // 最大等待时间
    int maxWait;
    // 池对象耗尽之后是否阻塞，maxWait < 0 时一直等待
    boolean blockWhenExhausted;
    // 取对象时验证
    boolean testOnBorrow;
    // 回收验证
    boolean testOnReturn;
    // 创建时验证
    boolean testOnCreate;
    // 空闲验证
    boolean testWhileldle;
    // 后进先出
    boolean lifo;

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMinldle() {
        return minldle;
    }

    public void setMinldle(int minldle) {
        this.minldle = minldle;
    }

    public int getMaxldle() {
        return maxldle;
    }

    public void setMaxldle(int maxldle) {
        this.maxldle = maxldle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestOnCreate() {
        return testOnCreate;
    }

    public void setTestOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
    }

    public boolean isTestWhileldle() {
        return testWhileldle;
    }

    public void setTestWhileldle(boolean testWhileldle) {
        this.testWhileldle = testWhileldle;
    }

    public boolean isLifo() {
        return lifo;
    }

    public void setLifo(boolean lifo) {
        this.lifo = lifo;
    }
}
