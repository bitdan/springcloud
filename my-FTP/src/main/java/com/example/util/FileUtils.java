package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;

/**
 * 文件操作工具类<br/>
 *
 * @author swh
 */
public class FileUtils {

	private FileUtils() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * (公开文件目录)得到指定路径的文件
	 *
	 * @param uri 文件相对路径
	 * @return 指定路径的文件(不存在则创建)
	 */
	public static File getFile(URI uri) {
		return new File(uri);
	}

	/**
	 * (公开文件目录)得到指定路径的文件
	 *
	 * @param path 文件相对路径
	 * @return 指定路径的文件(不存在则创建)
	 */
	public static File getFile(String path) {
		return getFile(path, true);
	}

	/**
	 * (公开文件目录)得到指定路径的文件
	 *
	 * @param path   文件相对路径
	 * @param create 不存在文件时是否创建
	 * @return 指定路径的文件
	 */
	public static File getFile(String path, boolean create) {
		path = path(path);
		if (null != path) {
			File file = new File(path);
//            File file = new File(path, FilenameUtils.getName(path));
			if (file.exists()) {
				return file;
			} else if (create) {
				try {
					File parent = file.getParentFile();
					if (null != parent && !parent.exists()) {
						parent.mkdirs();
					}
					if (file.createNewFile()) {
						return file;
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}

	/**
	 * 获取文件夹下的所有文件
	 *
	 * @param path
	 * @return
	 */
	public static File[] getFiles(String path) {
		File file = getFile(path, false);
		if (null != file) {
			return file.listFiles();
		}
		return null;
	}

	/**
	 * (公开文件目录)创建指定文件名的文件
	 *
	 * @param fileName 文件名
	 * @return 创建的文件相对路径
	 */
	public static String createFile(String fileName) {
		fileName = path(fileName);
		if (null != fileName) {
			File file = new File(fileName);
			if (!file.exists()) {
				try {
					File parent = file.getParentFile();
					if (null != parent && !parent.exists()) {
						parent.mkdirs();
					}
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return fileName;
	}

	/**
	 * (公开文件目录)复制文件到根目录下指定目标
	 *
	 * @param file       源文件
	 * @param targetName 目标名称
	 */
	public static void copyTo(File file, String targetName) {
		try {
			copyFile(new FileInputStream(file), getFile(targetName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * (公开文件目录)复制文件到根目录下指定目标
	 *
	 * @param is         输入流
	 * @param targetName 目标名称
	 */
	public static void copyTo(InputStream is, String targetName) {
		copyFile(is, getFile(targetName));
	}

	/**
	 * 根据目录创建规则创建目录
	 */
	public static String createFolder(String path) {
		if (null != path) {
			path = path(path);
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return path;
		}
		return null;
	}

	/**
	 * 根据目录创建规则创建目录
	 */
	public static String createFileFolder(String path) {
		if (null != path) {
			path = path(path);
			File file = new File(path);
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (null != parent && !parent.exists()) {
					parent.mkdirs();
				}
			}
			return path;
		}
		return null;
	}

	/**
	 * 指定路径下按日期生成目录
	 */
	public static String createFolderForDay(String path) {
		if (null != path) {
			String today = DateUtils.format(new Date());
			path = path + "/" + today + "/";
			path = path(path);
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return path;
		}
		return null;
	}

	/**
	 * 复制文件
	 */
	public static void copyFile(InputStream is, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] b = new byte[256];
			int k = 0;
			while ((k = is.read(b)) != -1) {
				fos.write(b, 0, k);
			}
			fos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 重命名文件
	 */
	public static boolean rename(String r, String source, String dest) {
		System.out.println(source);
		System.out.println(dest);
		File file = new File(r + "/" + source);
		System.out.println(file + "," + file.exists());
		if (file.exists()) {
			return file.renameTo(new File(r + "/" + dest));
		}
		return false;
	}

	/**
	 * 返回路径
	 */
	public static String path(String path) {
		if (null != path) {
			path = path.replaceAll("//+", "/");
		}
		return path;
	}

	/**
	 * 删除文件
	 */
	public static void deleteFile(String r, String fileName) {
		File file = new File(r + File.separator + fileName);
		if (file.exists()) {
			delete(file);
		}
	}

	/**
	 * 递归删除文件
	 *
	 * @param file
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				delete(f);
			}
			file.delete();
		}
	}

	/**
	 * @param filePath
	 * @param b
	 * @throws IOException
	 */
	public static void writeFile(String filePath, byte[] b) throws IOException {
		if (b == null || b.length <= 0) {
			return;
		}
		filePath = createFile(filePath);
		OutputStream out = new FileOutputStream(filePath);
		out.write(b);
		out.flush();
		out.close();
	}

	public static void writeFile(String filePath, String content) throws Exception {
		writeFile(filePath, content, true);
	}

	public static void writeFile(String filePath, String content, boolean append) throws Exception {
		String file = createFile(filePath);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8");
		out.write(content);
		out.close();
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return 文件内容
	 */
	public static String readFile(File file) {
		String jsonStr = "";
		LOG.info("开始读取文件：" + file.getPath());
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			int ch = 0;
			StringBuffer sb = new StringBuffer();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			reader.close();
			jsonStr = sb.toString();
			LOG.info("读取文件结束");
			return jsonStr;
		} catch (Exception e) {
			LOG.error("读取文件出现异常：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isExists(String path) {
		path = path(path);
		if (null != path) {
			File file = new File(path);
			return file.exists();
		}
		return false;
	}

	/**
	 * 递归获取目录下的所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getErgodicFiles(String path) {
		File dir = getFile(path, false);
		List<File> fileList = new ArrayList<>();
		if (dir != null) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isDirectory()) {
						List<File> subFileList = getErgodicFiles(f.getPath());
						fileList.addAll(subFileList);
					} else {
						fileList.add(new File(f.getPath()));
					}
				}
			}
		}
		return fileList;
	}

	/**
	 * 按最后修改时间排序，最新修改的文件排在最前
	 * @param files
	 * @return
	 */
	public static File[] sortForLastModified(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0) {
					return -1;
				} else if (diff == 0) {
					return 0;
				} else {
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
				}
			}
			@Override
			public boolean equals(Object obj) {
				return true;
			}
		});
		return files;
	}

	/**
	 * 按最后修改时间排序，最新修改的文件排在最前
	 * @param files
	 * @return
	 */
	public static List<File> sortForLastModified(List<File> files) {
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0) {
					return -1;
				} else if (diff == 0) {
					return 0;
				} else {
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
				}
			}
			@Override
			public boolean equals(Object obj) {
				return true;
			}
		});
		return files;
	}

	/**
	 * 获得文件的创建时间
	 * @param file
	 * @return
	 */
	public static Date getCreateTime(File file){
		Date createTime=null;
		try {
			// 根据文件的绝对路径获取Path
			Path path = Paths.get(file.getAbsolutePath());
			// 根据path获取文件的基本属性类
			BasicFileAttributes attrs  = Files.readAttributes(path, BasicFileAttributes.class);
			// 从基本属性类中获取文件创建时间
			FileTime fileTime = attrs.creationTime();
			// 将文件创建时间转成毫秒
			long millis = fileTime.toMillis();
			createTime = new Date(millis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return createTime;
	}

}
