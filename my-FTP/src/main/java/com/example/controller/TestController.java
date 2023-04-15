package com.example.controller;


import com.example.util.FTPUtil;
import com.example.util.HutoolSFTPUtil;
import com.example.util.SFTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller 测试
 *
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    /**
     * 构造方法注入
     */
    @Autowired
    FTPUtil ftpUtil;

    @Autowired
    SFTPUtil sftpUtil;

    @Autowired
    HutoolSFTPUtil hutoolSFTPUtil;


    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/ftpCreate")
    public String ftpCreate() {
        boolean result =  false;
        try {
            result = ftpUtil.createDirectory("test03");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/upload")
    public String upload() {
        try {
            String remotePath = "/test01/application.yml";
            ftpUtil.createDirectory(remotePath);
            String localPath = "C:/Users/26825/Desktop/application.yml";
            FTPUtil.UploadStatus uploadStatus = ftpUtil.upload(localPath,remotePath);
            log.info(String.valueOf(uploadStatus));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/sftpCreate")
    public String sftpCreate() {
        boolean result =  false;
        try {
            result = sftpUtil.createFolders("/home/hadoop/sftpdata/test01");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/sftpUpload")
    public String sftpUpload() {
        String result =  "";
        try {
            String remotePath = "/test01";
            String remoteFileName = "test-20220718 -测试.txt";
            String localFileFullPath = "C:/Users/26825/Desktop/application.yml";
            result = sftpUtil.uploadLocalToRemote(remotePath,remoteFileName,localFileFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/hutoolMakeDir")
    public String hutoolMakeDir() {
        boolean result = false;
        try {
            result = hutoolSFTPUtil.mkdir("/home/hadoop/sftpdata/hutool");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/hutoolMakeDirs")
    public String hutoolMakeDirs() {
        boolean result = false;
        try {
            result = hutoolSFTPUtil.mkdirs("/home/hadoop/sftpdata/hutool/test01/test02");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }


    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/hutoolUpload")
    public String hutoolUpload() {
        boolean result = false;
        try {
            String remoteFile = "/home/hadoop/sftpdata/hutool/test01/test02/test01.txt";
            String localFile = "C:/Users/26825/Desktop/application.yml";
            String remotePath = remoteFile.substring(0,remoteFile.lastIndexOf("/"));
            if(hutoolSFTPUtil.mkdirs(remotePath)){
                result = hutoolSFTPUtil.upload(remoteFile,localFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/hutoolDownload")
    public String hutoolDownload() {
        boolean result = false;
        try {
            String remoteFile = "/home/hadoop/sftpdata/hutool/01.txt";
            String localFile = "C:/Users/26825/Desktop/application.yml";
            result = hutoolSFTPUtil.download(remoteFile,localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/hutoolDelDir")
    public String hutoolDelDir() {
        boolean result = false;
        try {
            result = hutoolSFTPUtil.delDir("/home/hadoop/sftpdata/hutool");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }
    /**
     * 保存数据
     *
     * @return
     */
    @GetMapping("/exec")
    public String exec() {
        String result = "";
        try {
            result = hutoolSFTPUtil.exec("ls /home/hadoop");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }
}
