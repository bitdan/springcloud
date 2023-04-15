package com.example.conf;



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
}
