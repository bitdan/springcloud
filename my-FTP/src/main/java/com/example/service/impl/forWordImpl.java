package com.example.service.impl;

import com.example.service.ForWord;
import com.example.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author duran
 * @description TODO
 * @date 2023-04-21
 */
@Slf4j
public class forWordImpl implements ForWord {

    /**
     * 创建段落
     *
     * @param text
     */
    public void createParagraph(XWPFDocument doc, String text) {
        XWPFParagraph paragraph = doc.createParagraph();// 新建段落
        //paragraph.setAlignment(ParagraphAlignment.LEFT);// 设置段落的对齐方式
        paragraph.setFontAlignment(1);//字体对齐方式：1左对齐 2居中3右对齐
        XWPFRun run = paragraph.createRun();//创建标题
        run.setText(text);
        run.setColor("000000");//设置颜色
        run.setFontSize(14); //设置字体大小
        run.addCarriageReturn();//回车换行
    }

    /**
     * 设置表格中字体
     *
     * @param cell
     * @param cellText
     */
    public void setTableFonts(XWPFTableCell cell, String cellText) {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph p = new XWPFParagraph(ctp, cell);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = p.createRun();
        run.setFontSize(12);
        run.setText(cellText);
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
//            CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
//            fonts.setAscii("仿宋");
//            fonts.setEastAsia("仿宋");
//            fonts.setHAnsi("仿宋");

        cell.setParagraph(p);
    }

    @Override
    public void createDocumentlist(HttpServletResponse response, String category, String interfaceName, String dimensionName, String file, ArrayList<List<String>> lists, List<String> listKey, List<String> listLabel) throws Exception {
        XWPFDocument doc = new XWPFDocument();// 创建Word文件
        // 标题
        XWPFParagraph p = doc.createParagraph();// 新建段落
        p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
        XWPFRun r = p.createRun();//创建标题
        r.setText(category);
        r.setBold(true);//设置为粗体
        r.setColor("000000");//设置颜色
        r.setFontSize(21); //设置字体大小
        r.addCarriageReturn();//回车换行

        // 段落

        createParagraph(doc, "统计类型：" + category);
        createParagraph(doc, "具体类别：" + interfaceName);
        createParagraph(doc, "统计维度：" + dimensionName);

        // 插入图片
        XWPFParagraph pImg = doc.createParagraph();
        pImg.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun rImg = pImg.createRun();//创建标题
        rImg.addCarriageReturn();//回车换行
        String imgData = file.substring(file.indexOf(",") + 1);
        byte[] bytes = new BASE64Decoder().decodeBuffer(imgData);
        rImg.addPicture(new ByteArrayInputStream(bytes), Document.PICTURE_TYPE_PNG, "123.png", Units.toEMU(400), Units.toEMU(180));


        // 待插入表格内容
        Integer rows = lists.size();
        Integer cols = listKey.size();

        XWPFTable table = doc.createTable(rows + 1, cols + 1);
        //列宽自动分割
        CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));


        //表头内容
        for (Integer i = 0; i < cols; i++) {
            setTableFonts(table.getRow(0).getCell(i + 1), listLabel.get(i));
        }
        //表头字段序号
        setTableFonts(table.getRow(0).getCell(0), "序号");
        //是总计
        setTableFonts(table.getRow(rows).getCell(0), "总计");
        //序号列
        for (int j = 0; j < rows - 1; j++) {
            setTableFonts(table.getRow(j + 1).getCell(0), String.valueOf(j + 1));
        }
        //表格内容
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                setTableFonts(table.getRow(i + 1).getCell(j + 1), lists.get(i).get(j));
            }
        }

        String fileName = category + "_" + System.currentTimeMillis() + ".doc";
        String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        response.setContentType("application/octet-stream");
        //刷新缓冲
        response.flushBuffer();
        OutputStream ouputStream = response.getOutputStream();
        doc.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    @Override
    public void exportExcel(HttpServletResponse response, List<Map<String, Object>> datas) {
        try {
            String filename = new String(("输电线路台账" + ExcelUtils.EXCEL_SUFFIX).getBytes("GBK"), "ISO8859-1");
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename);
            ExcelUtils eu = new ExcelUtils();
            eu.setTitle("输电线路台账");
            eu.addColumn("线路ID", ExcelUtils.CELL_TYPE_STRING, 20, "id");
            eu.addColumn("线路名称", ExcelUtils.CELL_TYPE_STRING, 20, "line_name");
            eu.addColumn("电压等级", ExcelUtils.CELL_TYPE_STRING, 20, "base_voltage");
            eu.addColumn("进线站id", ExcelUtils.CELL_TYPE_STRING, 20, "enter_substation_id");
            eu.addColumn("进线站名称", ExcelUtils.CELL_TYPE_STRING, 20, "enter_substation");
            eu.addColumn("出线站id", ExcelUtils.CELL_TYPE_STRING, 20, "out_substation_id");
            eu.addColumn("出线站名称", ExcelUtils.CELL_TYPE_STRING, 20, "out_substation");
            eu.addColumn("线路类型", ExcelUtils.CELL_TYPE_STRING, 20, "line_type");
            eu.addColumn("全路径", ExcelUtils.CELL_TYPE_STRING, 20, "full_path");
            eu.addColumn("所属区域", ExcelUtils.CELL_TYPE_STRING, 20, "region");
            eu.addColumn("备注", ExcelUtils.CELL_TYPE_STRING, 20, "remarks");

            eu.setDataMap(datas);
            eu.create(response.getOutputStream());// 生成文档并提供下载
            log.info("【导出文件】-输电线路台账-");
        } catch (Exception e) {
            log.error("【导出文件失败】-输电线路台账", e);
        }
    }
}
