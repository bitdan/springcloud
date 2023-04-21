package com.example.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author duran
 * @description TODO
 * @date 2023-04-21
 */
public interface ForWord {

    void createParagraph(XWPFDocument doc, String text);

    void setTableFonts(XWPFTableCell cell, String cellText);


    /**
     * 导出 word文件
     * @param response
     * @param category 文件目录名
     * @param interfaceName 具体接口名
     * @param dimensionName 统计维度
     * @param file 图片文件
     * @param lists 表格内容
     * @param listKey 对应关键字
     * @param listLabel 关键字对应中文名
     * @throws Exception
     */

    void createDocumentlist(HttpServletResponse response,
                            String category,
                            String interfaceName,
                            String dimensionName,
                            String file,
                            ArrayList<List<String>> lists,
                            List<String> listKey,
                            List<String> listLabel) throws Exception;

    void exportExcel(HttpServletResponse response, List<Map<String, Object>> datas);

}
