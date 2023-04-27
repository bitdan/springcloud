package com.example.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.example.conf.ftp.SFTPPoolConfig;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class HutoolSFTPUtil {

    @Autowired
    SFTPPoolConfig sftpPoolConfig;

    /**
     * 创建目录
     *
     * @param newDir
     */
    public boolean mkdir(String newDir) {
        // jsch的session需要补充设置sshConfig.put("PreferredAuthentications", "publickey,keyboard-interactive,password")来跳过Kerberos认证，同样的HutoolSFTPUtil工具类里面也有这个问题
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        Sftp sftp = JschUtil.createSftp(session);
        boolean result = false;
        try {
            result = sftp.mkdir(newDir);
        } catch (Exception e) {
            log.error("mkdir error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }

    /**
     * 删除目录
     *
     * @param delDir
     */
    public boolean delDir(String delDir) {
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        Sftp sftp = JschUtil.createSftp(session);
        boolean result = false;
        try {
            result = sftp.delDir(delDir);
        } catch (Exception e) {
            log.error("mkdir error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }

    /**
     * 递归创建目录
     *
     * @param newDir
     * @return
     */
    public boolean mkdirs(String newDir) {
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        Sftp sftp = JschUtil.createSftp(session);
        boolean result = false;
        try {
            sftp.mkDirs(newDir);
            result = true;
        } catch (Exception e) {
            log.error("mkdir error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param destPath
     * @param srcFileFullPath
     * @return
     */
    public boolean upload(String destPath, String srcFileFullPath) {
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        Sftp sftp = JschUtil.createSftp(session);
        boolean result = false;
        try {
            File file = new File(srcFileFullPath);
            result = sftp.upload(destPath, file);
        } catch (Exception e) {
            log.error("mkdir error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param src
     * @param destFileFullPath
     * @return
     */
    public boolean download(String src, String destFileFullPath) {
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        Sftp sftp = JschUtil.createSftp(session);
        boolean result = false;
        try {
            File destFile = new File(destFileFullPath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            sftp.download(src, destFile);
            result = true;
        } catch (Exception e) {
            log.error("mkdir error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }

    /**
     * 远程执行 shell 命令
     *
     * @param command
     * @return
     * @throws Exception
     */
    public String exec(String command) {
        Session session = JschUtil.createSession(sftpPoolConfig.getHost(), sftpPoolConfig.getPort(), sftpPoolConfig.getUsername(), sftpPoolConfig.getPassword());
        String result = "";
        try {
            log.info("执行命令：{}", command);
            result = JschUtil.exec(session, command, CharsetUtil.CHARSET_UTF_8);
            log.info("执行结果：{}", result);
        } catch (Exception e) {
            log.error("exec error ", e);
        } finally {
            JschUtil.close(session);
        }
        return result;
    }
}
