package com.example.util;


import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ArrayUtil;
import com.example.service.SFTPPoolService;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Sftp工具类
 */
@Component
@Slf4j
public class SFTPUtil {

    /**
     * sftp连接池服务
     */
    @Autowired
    SFTPPoolService sFtpPoolService;

    /**
     * 文件分隔符，linux下就是 /
     */
    private String NODE_SEPARATOR = "/";

    /**
     * 权限数字
     */
    private String permission = "755";

    /**
     * 每个目录下最大子文件(夹)数量
     */
    private int MAX_CHILD_FILE_NUMBER = 1000;


    /**
     * 本地文件上传到远程 目录
     *
     * @param relativePath 远程文件最后一级目录
     * @param fileName 远程文件名
     * @param localFileFullPath 本地文件全路径
     * @return
     */
    public String uploadLocalToRemote(String relativePath, String fileName, String localFileFullPath) {
        String remoteFullPath = "";
        File localFile = new File(localFileFullPath);
        if (localFile.exists() && localFile.isFile()) {
            try (InputStream inputStream = new FileInputStream(localFile)) {
                if (!relativePath.endsWith(NODE_SEPARATOR)) {
                    relativePath = relativePath.concat(NODE_SEPARATOR);
                }
                remoteFullPath = upload(relativePath, fileName, inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return remoteFullPath;
    }


    /**
     * 附件上传
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return 上传后的文件完整路径
     */
    public String upload(String fileName, InputStream inputStream) {
        return upload(null, fileName, inputStream);
    }

    /**
     * 文件上传
     *
     * @param relativePath 文件保存的相对路径(最后一级目录)
     * @param fileName     文件名
     * @param inputStream  文件流
     * @return 上传后的文件完整路径
     */
    public String upload(String relativePath, String fileName, InputStream inputStream) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        String filePath = sFtpPoolService.getFtpPoolConfig().getWorkingDirectory();
        try {
            if (relativePath != null && !relativePath.trim().isEmpty()) {
                filePath = filePath + relativePath;
            }
            if (!dirIsExist(filePath)) {
                filePath = generateValidPath(filePath, sftp);
            }
            filePath = filePath.concat(fileName);
            sftp.put(inputStream, filePath);
            sftp.chmod(Integer.parseInt(permission, 8), filePath);
            return filePath;
        } catch (SftpException e) {
            log.error("SFTP上传文件出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return filePath;
    }

    /**
     * 文件下载
     *
     * @param fileUrl 文件路径
     * @return 文件字节数组
     */
    public byte[] download(String fileUrl) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            InputStream inputStream = sftp.get(fileUrl);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int n;
            byte[] data = new byte[sFtpPoolService.getFtpPoolConfig().getBufferSize()];
            while ((n = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, n);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException | SftpException e) {
            log.error("SFTP下载文件出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return new byte[0];
    }

    /**
     * 创建目录(只能创建一级目录,如果需要创建多级目录,需要调用mkdirs方法)
     *
     * @param path 目录路径
     */
    public void createFolder(String path) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            sftp.mkdir(path);
        } catch (SftpException e) {
            log.error("SFTP创建文件夹出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
    }

    /**
     * 如果目录不存在，则创建多级目录
     *
     * @param path
     * @return
     */
    public boolean createFolders(String path){
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        boolean result = false;
        try {
            if (validatePathValid(path, sftp)) {
                result = true;
            } else {
                String newPath = path + String.valueOf(System.currentTimeMillis()).substring(9);
                mkdirs(sftp, newPath.split("/"), "", newPath.split("/").length, 0);
                result = true;
            }
        } catch (Exception e) {
            log.error("SFTP创建文件夹出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return result;
    }

    /**
     * 文件读取
     *
     * @param fileUrl 文件路径
     * @return 文件字节数组
     */
    public String read(String fileUrl) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            InputStream inputStream = sftp.get(fileUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String str, resultStr = "";
            while ((str = in.readLine()) != null) {
                resultStr = resultStr.concat(str);
            }
            return resultStr;
        } catch (SftpException | IOException e) {
            log.error("SFTP读取文件出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return "";
    }

    /**
     * 判断目录是否存在
     *
     * @param url 文件夹目录
     * @return ture:存在；false:不存在
     */
    public boolean dirIsExist(String url) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            if (isDirectory(url)) {
                sftp.cd(url);
                String pwd = sftp.pwd();
                return pwd.equals(url) || pwd.concat("/").equals(url);
            }
            return false;
        } catch (SftpException e) {
            log.error("SFTP读取文件夹出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return false;
    }

    /**
     * 删除文件 或 删除文件夹
     * 注: 如果是文件夹， 不论该文件夹中有无内容，都能删除， 因此:此方法慎用
     *
     * @param remoteDirOrRemoteFile 要删除的文件  或 文件夹
     */
    public void delete(String remoteDirOrRemoteFile) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            List<String> targetFileOrDirContainer = new ArrayList<>(8);
            targetFileOrDirContainer.add(remoteDirOrRemoteFile);
            List<String> toBeDeletedEmptyDirContainer = new ArrayList<>(8);
            if (isDirectory(remoteDirOrRemoteFile)) {
                toBeDeletedEmptyDirContainer.add(remoteDirOrRemoteFile);
            }
            collectToBeDeletedEmptyDir(toBeDeletedEmptyDirContainer, targetFileOrDirContainer);
            if (!toBeDeletedEmptyDirContainer.isEmpty()) {
                String targetDir;
                for (int i = toBeDeletedEmptyDirContainer.size() - 1; i >= 0; i--) {
                    targetDir = toBeDeletedEmptyDirContainer.get(i);
                    sftp.rmdir(targetDir);
                }
            }
        } catch (SftpException e) {
            log.error("SFTP删除文件或者文件夹出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
    }

    /**
     * 删除相关文件 并 采集所有 需要被删除的 文件夹
     * <p>
     * 注: 如果是文件夹， 不论该文件夹中有无内容，都能删除， 因此:此方法慎用
     *
     * @param toBeDeletedEmptyDirContainer 所有待删除的空文件夹集合
     * @param targetFileOrDirContainer     本次, 要删除的文件的集合   或   本次, 要删除的文件所在文件夹的集合
     */
    public void collectToBeDeletedEmptyDir(List<String> toBeDeletedEmptyDirContainer, List<String> targetFileOrDirContainer) {
        List<String> todoCallDirContainer = new ArrayList<>(8);
        List<String> subfolderList;
        for (String remoteDirOrRemoteFile : targetFileOrDirContainer) {
            subfolderList = fileDeleteExecutor(remoteDirOrRemoteFile);
            toBeDeletedEmptyDirContainer.addAll(subfolderList);
            todoCallDirContainer.addAll(subfolderList);
        }
        if (!todoCallDirContainer.isEmpty()) {
            collectToBeDeletedEmptyDir(toBeDeletedEmptyDirContainer, todoCallDirContainer);
        }
    }

    /**
     * 删除remoteDirOrRemoteFile指向的文件 或 删除remoteDirOrRemoteFile指向的文件夹下的所有子级文件
     * 注: 如果是文件夹， 只会删除该文件夹下的子级文件；不会删除该文件夹下的孙子级文件(如果有孙子级文件的话)
     *
     * @param remoteDirOrRemoteFile 要删除的文件 或 要 文件夹   【绝对路径】
     * @return remoteDirOrRemoteFile指向的文件夹 下的 文件夹集合
     * 注: 如果remoteDirOrRemoteFile指向的是文件的话，返回空的集合
     * 注: 只会包含子级文件夹，不包含孙子级文件夹(如果有孙子级文件夹的话)
     */
    public List<String> fileDeleteExecutor(String remoteDirOrRemoteFile) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            List<String> subfolderList = new ArrayList<>(8);
            // 如果是文件，直接删除
            if (!isDirectory(remoteDirOrRemoteFile)) {
                sftp.rm(remoteDirOrRemoteFile);
                return subfolderList;
            }
            // 保证 remoteDirOrRemoteFile 以 “/” 开头,以 “/” 结尾
            remoteDirOrRemoteFile = handlePath(remoteDirOrRemoteFile, true, true);
            Vector<?> vector = sftp.ls(remoteDirOrRemoteFile);
            String fileName;
            String sftpAbsoluteFilename;
            // 列出文件名
            for (Object item : vector) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
                fileName = entry.getFilename();
                if (invalidFileName(fileName)) {
                    continue;
                }
                sftpAbsoluteFilename = remoteDirOrRemoteFile + fileName;
                // 如果是文件，直接删除
                if (!isDirectory(sftpAbsoluteFilename)) {
                    sftp.rm(sftpAbsoluteFilename);
                    continue;
                }
                subfolderList.add(sftpAbsoluteFilename);
            }
            return subfolderList;
        } catch (SftpException e) {
            log.error("SFTP删除文件或者文件夹出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return new ArrayList<>(0);
    }

    /**
     * 从给定路径中截取文件名
     *
     * @param path 路径，  如: /files/abc/info.yml
     * @return 文件名， 如: info.yml
     */
    private String getFilenameFromPath(String path) {
        return path.substring(path.lastIndexOf(NODE_SEPARATOR) + 1);
    }

    /**
     * 路径处理器
     * <p>
     * 根据参数控制处理类型，如:
     * 当: originPath 为【var/apps】时，
     * 当: handleHead 为 true, 处理结果为【/var/apps】
     * 当: handleTail 为 true, 处理结果为【var/apps/】
     * 当: handleHead 和 handleTail 均为 true, 处理结果为【/var/apps/】
     *
     * @param originPath 要处理的路径
     * @param handleHead 处理 起始处
     * @param handleTail 处理 结尾处
     * @return 处理后的路径
     */
    private String handlePath(String originPath, boolean handleHead, boolean handleTail) {
        if (originPath == null || "".equals(originPath.trim())) {
            return NODE_SEPARATOR;
        }
        if (handleHead && !originPath.startsWith(NODE_SEPARATOR)) {
            originPath = NODE_SEPARATOR.concat(originPath);
        }
        if (handleTail && !originPath.endsWith(NODE_SEPARATOR)) {
            originPath = originPath.concat(NODE_SEPARATOR);
        }
        return originPath;
    }

    /**
     * 判断是否为无效的文件名
     * 注:文件名(夹)名为【.】或【..】时，是无效的
     *
     * @param fileName 文件名
     * @return 是有无效
     */
    public boolean invalidFileName(String fileName) {
        return ".".equals(fileName) || "..".equals(fileName);
    }

    /**
     * 判断SFTP上的path是否为文件夹
     * 注:如果该路径不存在，那么会返回false
     *
     * @param path SFTP上的路径
     * @return 判断结果
     */
    public boolean isDirectory(String path) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        // 合法的错误id
        // int legalErrorId = 4;
        try {
            sftp.cd(path);
            return true;
        } catch (SftpException e) {
            // 如果 path不存在，那么报错信息为【No such file】，错误id为【2】
            // 如果 path存在，但是不能cd进去，那么报错信息形如【Can't change directory: /files/sqljdbc4-3.0.jar】，错误id为【4】
            return false;
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
    }

    /**
     * 获取某个文件夹下的所有文件名称
     *
     * @param path      文件夹路径
     * @param fileTypes 文件类型,如果为null或者长度为0,则获取所有文件名称,如果已指定,则获取指定类型的文件类型
     * @return 文件名称
     */
    public List<String> queryFileName(String path, String... fileTypes) {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            Vector<ChannelSftp.LsEntry> ls = sftp.ls(path);
            return ls.stream().map(ChannelSftp.LsEntry::getFilename).filter(
                    name -> {
                        if (ArrayUtil.isNotEmpty(fileTypes)) {
                            return FileNameUtil.isType(name, fileTypes);
                        }
                        return true;
                    }).collect(Collectors.toList());
        } catch (SftpException e) {
            log.error("SFTP获取某个文件夹下的所有文件名称出错", e);
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
        return new ArrayList<>(0);
    }

    /**
     * 创建多级文件目录
     *
     * @param dirs     每个目录的名称数组
     * @param tempPath 临时路径，传入""空字符串，主要为了递归调用方便
     * @param length   数组长度
     * @param index    当前索引，为了递归调用
     */
    private void mkdirs(ChannelSftp sftp, String[] dirs, String tempPath, int length, int index) {
        // 以"/a/b/c/d"为例按"/"分隔后,第0位是"";顾下标从1开始
        index++;
        if (index < length) {
            // 目录不存在，则创建文件夹
            tempPath += "/" + dirs[index];
        }
        try {
            sftp.cd(tempPath);
            if (index < length) {
                mkdirs(sftp, dirs, tempPath, length, index);
            }
        } catch (SftpException ex) {
            try {
                sftp.mkdir(tempPath);
                sftp.chmod(Integer.parseInt(permission, 8), tempPath);
                sftp.cd(tempPath);
            } catch (SftpException e) {
                return;
            }
            mkdirs(sftp, dirs, tempPath, length, index);
        }
    }

    /**
     * 统计目录下文件(夹)数量
     *
     * @param path 目录路径
     * @return 文件数量
     */
    private int countFiles(String path) throws SftpException {
        ChannelSftp sftp = sFtpPoolService.borrowObject();
        try {
            sftp.cd(path);
            return sftp.ls(path).size();
        } finally {
            sFtpPoolService.returnObject(sftp);
        }
    }

    /**
     * 校验路径是否可用
     *
     * @param path 路径
     * @return 是否可用
     */
    private boolean validatePathValid(String path, ChannelSftp sftp) {
        int countFiles = 0;
        try {
            countFiles = countFiles(path);
        } catch (SftpException e) {
            mkdirs(sftp, path.split("/"), "", path.split("/").length, 0);
        }
        return countFiles <= MAX_CHILD_FILE_NUMBER;
    }

    /**
     * 生成有效路径
     *
     * @param path 参数路径
     * @return 解析后的有效路径
     */
    private String generateValidPath(String path, ChannelSftp sftp) {
        if (validatePathValid(path, sftp)) {
            return path;
        } else {
            String newPath = path + String.valueOf(System.currentTimeMillis()).substring(9);
            mkdirs(sftp, newPath.split("/"), "", newPath.split("/").length, 0);
            return newPath;
        }
    }
}
