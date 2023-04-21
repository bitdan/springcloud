package com.example.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel生成工具<br/>
 * 1：设置文件名称fileName，文档标题title<br/>
 * 2：调用addColumn方法添加列<br/>
 * 3：调用dataArr/setDataMap方法传入数据<br/>
 * 4：调用create方法生成文档<br/>
 * 5：传入setDataMap时，addColumn中必须设置name(Map key(字段名称))参数 <br/>
 * 
 * @author swh
 * @date 2017-05-23
 * 
 */

@SuppressWarnings("deprecation")
public class ExcelUtils {

	/** excel文档文件后缀 */
	public static final String EXCEL_SUFFIX = ".xls";

	/** 字段类型-数字 */
	public static final CellType CELL_TYPE_NUMERIC = CellType.NUMERIC;
	/** 字段类型-字符串 */
	public static final CellType CELL_TYPE_STRING = CellType.STRING;
	/** 字段类型-空 */
	public static final CellType CELL_TYPE_BLANK = CellType.BLANK;
	/** 字段类型-Boolean */
	public static final CellType CELL_TYPE_BOOLEAN = CellType.BOOLEAN;

	/** 单元格颜色-黄 */
	public static final short CELL_COLOR_YELLOW = IndexedColors.GOLD.index;
	/** 单元格颜色-灰 */
	public static final short CELL_COLOR_GREY = IndexedColors.GREY_25_PERCENT.index;

	/** 文件名称 */
	private String fileName = null;
	/** 文档标题 */
	private String title = null;
	/** 文档列 */
	private List<Object[]> columns = null;

	/** 文档数据(数组) */
	private List<Object[]> dataArr = null;
	/** 文档数据(Map) */
	private List<Map<String, Object>> dataMap = null;
	/** 文档数据长度 */
	private int dataSize = 0;

	/** 列数 */
	private int columnSize = 0;
	/** 列名称(数据为Map时) */
	private String columnsName[] = null;
	/** 列前景颜色(数据为Map时) */
	private String columnsColor[] = null;
	/** 列标题 */
	private String columnsTitle[] = null;
	/** 列类型 */
	private CellType columnsType[] = null;
	/** 列宽 */
	private int columnsWidth[] = null;

	/** 标题样式 */
	private CellStyle titleStyle;
	/** 列标题样式 */
	private CellStyle columnStyle;
	/** 列标题样式 */
	private CellStyle bottomStyle;
	/** 内容区域的样式 */
	private CellStyle contentStyle;
	/** 列备注样式 */
	private CellStyle remarkStyle;
	/** 备注样式开关(默认关闭) **/
	private boolean switchRemark = false;

	/** 自定义尾部信息 */
	private String footerMsg = null;

	/** 自定义的合并单元格信息 */
	private List<CellRangeAddress> mergedRegions = null;

	/** 构造Excel生成工具 */
	public ExcelUtils() {
	}

	/**
	 * 构造Excel生成工具
	 * 
	 * @param fileName 文件名称(写入服务器本地文件保存的文件名，请避免重复，为null自动生成名称)
	 * @param title    文档标题
	 */
	public ExcelUtils(String fileName, String title) {
		this.title = title;
		this.fileName = fileName(fileName);
	}

	/**
	 * 添加列
	 * 
	 * @param title 列标题名称
	 * @param type  列字段类型(缺省String，可选ExcelUtils.CELL_TYPE_... )
	 * @param width 列宽度(字符数，默认自动列宽)
	 * @param name  Map key(字段名称)
	 */
	public void addColumn(String title, CellType type, Integer width, String name) {
		addColumn(title, type, width, name, null);
	}

	/**
	 * 添加列
	 * 
	 * @param title 列标题名称
	 * @param type  列字段类型(缺省String，可选ExcelUtils.CELL_TYPE_... )
	 * @param width 列宽度(字符数，默认自动列宽)
	 * @param name  Map key(字段名称)
	 * @param color Map key(字段名称)
	 */
	public void addColumn(String title, CellType type, Integer width, String name, String color) {
		if (null == columns) {
			columns = new ArrayList<Object[]>();
		}
		CellType cell_type = CELL_TYPE_STRING;// 缺省类型为字符串
		if (null != type) {
			cell_type = type;
			if (cell_type != CELL_TYPE_STRING && cell_type != CELL_TYPE_NUMERIC && cell_type != CELL_TYPE_BLANK && cell_type != CELL_TYPE_BOOLEAN) {
				cell_type = CELL_TYPE_STRING;
			}
		}
		if (null == width || width < 1 || width > 250) {
			width = 0;
		} else {
			width = width * 256;
		}
		Object[] obj = new Object[] { title, cell_type, width, name, color };// 列标题，列类型，列宽，列名称(Map key)，列颜色(Map key)
		columns.add(obj);
	}

	/**
	 * 生成excel文档
	 * 
	 * @param os 文档输出流，非null则将文档写入os(不会关闭os)，否则通过FileUtils.createSecurityFile生成服务器本地文件
	 * @return os非null则返回null，否则返回文件相对地址，通过FileUtils.getSecurityFile(path)读取文件
	 */
	public String create(OutputStream os) {
		return create(os, false, null);
	}

	/**
	 * 生成excel文档
	 * 
	 * @param os 文档输出流，非null则将文档写入os(不会关闭os)，否则通过FileUtils.createSecurityFile生成服务器本地文件
	 * @return os非null则返回null，否则返回文件相对地址，通过FileUtils.getSecurityFile(path)读取文件
	 */
	public String create(OutputStream os, String path) {
		return create(os, false, path);
	}

	/**
	 * 创建没有尾部数据的excel
	 */
	public String createWithNoFlooter(OutputStream os) {
		return create(os, true, null);
	}

	/**
	 * 创建没有尾部数据的excel
	 */
	public String createWithNoFlooter(OutputStream os, String path) {
		return create(os, true, path);
	}

	/**
	 * 生成excel文档
	 * 
	 * @param os       文档输出流，非null则将文档写入os(不会关闭os)，否则通过FileUtils.createSecurityFile生成服务器本地文件
	 * @param noFooter 屏蔽数据量行(true:不输入数据量行,false:输出数据量行)
	 * @return os非null则返回null，否则返回文件相对地址，通过FileUtils.getSecurityFile(path)读取文件
	 */
	public String create(OutputStream os, boolean noFooter) {
		return create(os, noFooter, null);
	}

	/**
	 * 生成excel文档
	 * 
	 * @param os       文档输出流，非null则将文档写入os(不会关闭os)，否则通过FileUtils.createSecurityFile生成服务器本地文件
	 * @param noFooter 屏蔽数据量行(true:不输入数据量行,false:输出数据量行)
	 * @param path     文件路径（生成本地文件）
	 * @return os非null则返回null，否则返回文件相对地址，通过FileUtils.getSecurityFile(path)读取文件
	 */
	public String create(OutputStream os, boolean noFooter, String path) {
		initColumns();// 初始化列
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			initStyleSpecial(workbook);// 初始化标题样式

			HSSFSheet sheet = workbook.createSheet();// 创建sheet

			// 合并单元格的操作
			if (mergedRegions != null) {
				for (CellRangeAddress region : mergedRegions) {
					sheet.addMergedRegion(region);
				}
			}

			columnWidth(sheet);// 列宽
			int ri = 0;// 行索引
			ri = title(sheet, ri);// 标题
			ri = header(sheet, ri);// 列标题
			if (dataSize > 0) {
				int n = 0;// sheet 数据行数
				int nt = 0;// 文档 数据行数
				if (null != dataArr) { // 处理数组数据
					for (Object[] data : dataArr) {
						rowData(sheet.createRow(ri++), data);
						n++;
						nt++;
						if (n >= 60000 && nt != dataSize) {
							sheet = workbook.createSheet();// 创建sheet
							columnWidth(sheet);// 列宽
							ri = 0;
							n = 0;
							ri = header(sheet, ri);// 列标题
						}
					}
				} else if (null != dataMap) {// 处理Map数据
					for (Map<String, Object> data : dataMap) {
						rowData(sheet.createRow(ri++), data, workbook);
						n++;
						nt++;
						if (n >= 60000 && nt != dataSize) {
							sheet = workbook.createSheet();// 创建sheet
							columnWidth(sheet);// 列宽
							ri = 0;
							n = 0;
							ri = header(sheet, ri);// 列标题
						}
					}
				}
			}
			if (null != footerData && !footerData.isEmpty()) {
				ri = footerData(sheet, ri);
			}
			ri = footerConsume(sheet, ri);// 自定义尾部信息
			if (!noFooter) {
				ri = footer(sheet, ri, dataSize);// 尾部
			}

			return write(workbook, os, path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			clear();// 清理
		}
	}

	/** 尾行统计数据 */
	private Map<Integer, String> footerData = null;

	/**
	 * 添加未行数据(字符串格式)
	 * 
	 * @param footerData
	 */
	public void footer(Map<Integer, String> footerData) {
		this.footerData = footerData;
	}

	/** 尾部信息 */
	private int footerData(HSSFSheet sheet, int ri) {
		HSSFRow row = sheet.createRow(ri++);// 行
		for (Integer i : footerData.keySet()) {
			HSSFCell cell = row.createCell(i);
			String d = footerData.get(i);
			if (null == d) {
				cell.setCellType(CellType.BLANK);
				cell.setCellValue("");
			} else {
				cell.setCellValue(d.toString());
			}
		}
		return ri;
	}

	/** 生成文档 */
	private String write(HSSFWorkbook workbook, OutputStream os, String path) throws IOException {
		if (null == os) {
			File f = FileUtils.getFile(path);
			os = new FileOutputStream(f);
			workbook.write(os);
			os.flush();
			os.close();
			return path;
		} else {
			workbook.write(os);
			os.flush();
			os.close();
			return null;
		}
	}

	/** 清理 */
	private void clear() {
		if (null != dataMap) {
			dataMap.clear();
			dataMap = null;
		}
		if (null != dataArr) {
			dataArr.clear();
			dataArr = null;
		}
		footerData = null;
		if (null != columns) {
			columns.clear();
			columns = null;
		}
		columnsTitle = null;
		columnsType = null;
		columnsWidth = null;
		columnsName = null;

		titleStyle = null;
		columnStyle = null;
		bottomStyle = null;
		contentStyle = null;
	}

	/** 初始化列标题、列数据类型、列宽 */
	private void initColumns() {
		if (null == columns || columns.isEmpty()) {
			throw new NullPointerException("No column found! Please add column first!");
		}
		columnSize = columns.size();
		columnsTitle = new String[columnSize];
		columnsType = new CellType[columnSize];
		columnsWidth = new int[columnSize];
		columnsName = new String[columnSize];
		columnsColor = new String[columnSize];
		int fi = 0;
		for (Object fo[] : columns) {
			columnsTitle[fi] = null == fo[0] ? "" : fo[0].toString();// 列标题
			columnsType[fi] = (CellType) fo[1];// 列类型
			columnsWidth[fi] = ((Integer) fo[2]).intValue();// 列宽
			columnsName[fi] = null == fo[3] ? "" : fo[3].toString();// 列字段名称(Map)
			columnsColor[fi] = null == fo[4] ? "" : fo[4].toString();// 列颜色名称(Map)
			fi++;
		}
	}

	/**
	 * 初始化样式 样式采用靠左，不自动换行
	 */
	private void initStyleSpecial(HSSFWorkbook workbook) {
		columnStyle = workbook.createCellStyle();
		HSSFFont columnFont = workbook.createFont();
		columnFont.setFontName("微软雅黑");
		columnFont.setFontHeightInPoints((short) 11);// 字号
		columnStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		columnStyle.setBorderBottom(BorderStyle.THIN);
		columnStyle.setBorderLeft(BorderStyle.THIN);
		columnStyle.setBorderRight(BorderStyle.THIN);
		columnStyle.setBorderTop(BorderStyle.THIN);
		columnStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		columnStyle.setFont(columnFont);
		columnStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		columnStyle.setWrapText(true);

		titleStyle = workbook.createCellStyle();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 12);// 字号
		titleFont.setBold(true);// 加粗
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		titleStyle.setWrapText(true);

		bottomStyle = workbook.createCellStyle();
		HSSFFont bottomFont = workbook.createFont();
		bottomFont.setFontHeightInPoints((short) 11);// 字号
		bottomStyle.setFont(bottomFont);
		bottomStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		bottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

		// 内容区域的样式
		contentStyle = workbook.createCellStyle();
		contentStyle.setWrapText(false);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);

		// 备注样式
		remarkStyle = workbook.createCellStyle();
		remarkStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		remarkStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		remarkStyle.setBorderBottom(BorderStyle.THIN);
		remarkStyle.setBorderTop(BorderStyle.THIN);
		remarkStyle.setBorderLeft(BorderStyle.THIN);
		remarkStyle.setBorderRight(BorderStyle.THIN);
	}

	/** 初始化样式 */
	private void initStyle(HSSFWorkbook workbook) {
		columnStyle = workbook.createCellStyle();
		HSSFFont columnFont = workbook.createFont();
		columnFont.setFontName("微软雅黑");
		columnFont.setFontHeightInPoints((short) 11);// 字号
		columnStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		columnStyle.setBorderBottom(BorderStyle.THIN);
		columnStyle.setBorderLeft(BorderStyle.THIN);
		columnStyle.setBorderRight(BorderStyle.THIN);
		columnStyle.setBorderTop(BorderStyle.THIN);
		columnStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		columnStyle.setFont(columnFont);
		columnStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		columnStyle.setWrapText(true);

		titleStyle = workbook.createCellStyle();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 12);// 字号
		titleFont.setBold(true);// 加粗
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		titleStyle.setWrapText(true);

		bottomStyle = workbook.createCellStyle();
		HSSFFont bottomFont = workbook.createFont();
		bottomFont.setFontHeightInPoints((short) 11);// 字号
		bottomStyle.setFont(bottomFont);
		bottomStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
		bottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

		// 内容区域的样式
		contentStyle = workbook.createCellStyle();
		contentStyle.setWrapText(true);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 备注样式
		remarkStyle = workbook.createCellStyle();
		remarkStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		remarkStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		remarkStyle.setBorderBottom(BorderStyle.THIN);
		remarkStyle.setBorderTop(BorderStyle.THIN);
		remarkStyle.setBorderLeft(BorderStyle.THIN);
		remarkStyle.setBorderRight(BorderStyle.THIN);
	}

	/** 设置列宽 */
	private void columnWidth(HSSFSheet sheet) {
		for (int i = 0; i < columnSize; i++) {
			int width = columnsWidth[i];
			if (width < 256) {
				sheet.autoSizeColumn(i);
			} else {
				sheet.setColumnWidth(i, width);
			}
		}
	}

	/** 填充行数据 */
	private void rowData(HSSFRow row, Object[] data) {
		for (int i = 0; i < columnSize; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(contentStyle);
			CellType type = columnsType[i];
			cell.setCellType(type);
			Object d = data[i];
			if (type == CELL_TYPE_STRING) {
				cell.setCellValue(null != d ? d.toString() : "");
			} else if (type == CELL_TYPE_NUMERIC) {
				cell.setCellValue(((Number) d).doubleValue());
			} else if (type == CELL_TYPE_BLANK) {
				cell.setCellValue("");
			} else if (type == CELL_TYPE_BOOLEAN) {
				cell.setCellValue((Boolean) d);
			} else {
				cell.setCellValue(null != d ? d.toString() : "");
			}
		}
	}

	/** 填充行数据 */
	private void rowData(HSSFRow row, Map<String, Object> data, HSSFWorkbook workbook) {
		for (int i = 0; i < columnSize; i++) {
			HSSFCell cell = row.createCell(i);
			Object d = data.get(columnsName[i]);
			Object color = data.get(columnsColor[i]);
			cell.setCellStyle(contentStyle);
			if (null == d) {
				cell.setCellType(CellType.BLANK);
				cell.setCellValue("");
			} else {
				CellType type = columnsType[i];
				cell.setCellType(type);
				if (type == CELL_TYPE_STRING) {
					cell.setCellValue(d.toString());
				} else if (type == CELL_TYPE_NUMERIC) {
					cell.setCellValue(((Number) d).doubleValue());
				} else if (type == CELL_TYPE_BLANK) {
					cell.setCellValue("");
				} else if (type == CELL_TYPE_BOOLEAN) {
					cell.setCellValue((Boolean) d);
				} else {
					cell.setCellValue(d.toString());
				}
				if (switchRemark && i == (columnSize - 1)) {
					cell.setCellStyle(remarkStyle);
				} else if (color != null) {
					CellStyle cellStyle = setForegroundColor(color, workbook);
					cell.setCellStyle(cellStyle);
				}
			}
		}
	}

	/** 填充前景颜色等样式 **/
	private CellStyle setForegroundColor(Object color, HSSFWorkbook workbook) {
		CellStyle c = workbook.createCellStyle();
		HSSFFont columnFont = workbook.createFont();
		columnFont.setFontName("微软雅黑");
		columnFont.setFontHeightInPoints((short) 11);// 字号
		c.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		c.setBorderBottom(BorderStyle.THIN);
		c.setBorderLeft(BorderStyle.THIN);
		c.setBorderRight(BorderStyle.THIN);
		c.setBorderTop(BorderStyle.THIN);
		c.setFillForegroundColor((Short) color);
		c.setFont(columnFont);
		c.setAlignment(HorizontalAlignment.CENTER);// 居中
		c.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		c.setWrapText(true);
		return c;
	}

	/** 创建文档标题 */
	private int title(HSSFSheet sheet, int ri) {
		if (null != title) {
			HSSFRow row = sheet.createRow(ri++);
			int i = 1;
			int inx = 0;
			while (inx != -1) {
				inx = title.indexOf("\n", inx);
				if (inx != -1) {
					inx++;
				}
				i++;
			}
			row.setHeight((short) ((256 * i)));// 设置标题行高(N+1行)
			HSSFCell cell = row.createCell(0);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(title);
			cell.setCellStyle(titleStyle);
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, columnSize > 1 ? (columnSize - 1) : 1));
		}
		return ri;
	}

	/** 创建列标题 */
	private int header(HSSFSheet sheet, int ri) {
		HSSFRow row = sheet.createRow(ri++);
		for (int i = 0; i < columnSize; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellType(CellType.STRING);
			cell.setCellStyle(columnStyle);
			cell.setCellValue(columnsTitle[i]);
		}
		return ri;
	}

	/** 尾部信息 */
	private int footer(HSSFSheet sheet, int ri, int size) {
		HSSFRow row = sheet.createRow(ri++);// 行
		HSSFCell cell = row.createCell(0);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("共" + size + "条数据!");
		cell.setCellStyle(bottomStyle);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, columnSize > 1 ? (columnSize - 1) : 1));
		return ri;
	}

	/** 尾部信息(自定义内容) */
	private int footerConsume(HSSFSheet sheet, int ri) {
		if (null != footerMsg && !footerMsg.isEmpty()) {
			HSSFRow row = sheet.createRow(ri++);// 行
			HSSFCell cell = row.createCell(0);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(footerMsg);
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, columnSize > 1 ? (columnSize - 1) : 1));
		}
		return ri;
	}

	/**
	 * 获取 文档标题
	 * 
	 * @return title 文档标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 文档标题
	 * 
	 * @param title 文档标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取文件名称
	 * 
	 * @return fileName 文件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 数据
	 * 
	 * @param dataArr (List<Object[]>：object数组集合，数组长度必须匹配列数)
	 */
	public void setDataArr(List<Object[]> dataArr) {
		this.dataArr = dataArr;
		this.dataMap = null;
		if (null != this.dataArr) {
			this.dataSize = this.dataArr.size();
		}
	}

	/**
	 * 设置 数据
	 * 
	 * @param dataMap (List<Map<String, Object>>：map集合)
	 */
	public void setDataMap(List<Map<String, Object>> dataMap) {
		this.dataArr = null;
		this.dataMap = dataMap;
		if (null != this.dataMap) {
			this.dataSize = this.dataMap.size();
		}
	}

	/**
	 * 自定义尾部信息
	 */
	public void setFooterMsg(String footerMsg) {
		this.footerMsg = footerMsg;
	}

	/**
	 * 设置 文件名称
	 * 
	 * @param fileName 文件名称(写入服务器本地文件保存的文件名，请避免重复，为null自动生成名称)
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName(fileName);
	}

	/**
	 * 
	 * @param mergedRegions 需要合并的单元格，格式为｛开始的行，结束的行，开始的列，结束的列｝
	 */
	public void setMergedRegions(List<CellRangeAddress> mergedRegions) {
		this.mergedRegions = mergedRegions;
	}

	/** 处理文件名称 */
	private String fileName(String fileName) {
		fileName = StringUtils.trim(fileName);
		if (null == fileName) {
			this.fileName = DateUtils.now("yyyyMMddHHmmss_") + StringUtils.ranLetter(8);
		}
		return fileName;
	}

	/** 开启备注样式(默认为关闭) **/
	public void openRemarkStyle() {
		switchRemark = true;
	}

	/**
	 * 生成excel文档
	 * 
	 * @param os       文档输出流，非null则将文档写入os(不会关闭os)，否则通过FileUtils.
	 *                 createSecurityFile生成服务器本地文件
	 * @param allClms  多个table
	 * @param allDatas 多个table
	 * @return
	 */
	public String create(String file, OutputStream os, List<List<Object[]>> allClms, List<List<Map<String, Object>>> allDatas) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			initStyle(workbook);// 初始化标题样式
			HSSFSheet sheet = workbook.createSheet();// 创建sheet
			int ri = 0;// 行索引
			for (int i = 0; i < allClms.size(); i++) {
				this.setColumns(allClms.get(i));
				this.setDataMap(allDatas.get(i));
				initColumns();// 初始化列
				columnWidth(sheet);// 列宽
				ri = title(sheet, ri);// 标题
				ri = header(sheet, ri);// 列标题
				if (dataSize > 0) {
					int n = 0;// sheet 数据行数
					if (null != dataMap) {// 处理Map数据
						for (Map<String, Object> data : dataMap) {
							rowData(sheet.createRow(ri++), data, workbook);
							n++;
							if (n >= 60000 && n != dataSize) {
								sheet = workbook.createSheet();// 创建sheet
								columnWidth(sheet);// 列宽
								ri = 0;
								n = 0;
								ri = header(sheet, ri);// 列标题
							}
						}
						sheet.createRow(ri++);
					}
				} else {
					sheet.createRow(ri++);
				}
			}
			return write(workbook, os, file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			clear();// 清理
		}
	}

	public List<Object[]> getColumns() {
		return columns;
	}

	public void setColumns(List<Object[]> columns) {
		this.columns = columns;
	}

}
