package com.baizhi.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportUtil {
    //注解方法导出用户信息
    public static void exportU(List<User> list) {

        for (User user : list) {
            //将图片下载到本地，最后再删除

        }

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息"), User.class, list);
        try {
            workbook.write(new FileOutputStream(new File("E:/easypoi.xls")));
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }

       /* //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题行数,默认0
        params.setHeadRows(2);  //表头行数,默认1

        //获取导入数据
        List<Teacher> teachers = ExcelImportUtil.importExcel(new FileInputStream(new File("D:/TestEasyPoi.xls")),Teacher.class, params);

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }*/
    }

    public static void deleteDir(File dir){
        File[] fs=dir.listFiles();
        for(File f:fs){//循环删除文件，循环结束文件夹为空
            if(f.isFile())f.delete();//判断是不是文件，是则删除
            if(f.isDirectory())deleteDir(f);//判断是不是文件夹，是则递归，调用本方法
        }
        dir.delete();//删除空文件夹
    }

    //导出用户信息
    public static void export(List<User> list) {
        //创建Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建工作表   参数：工作表名称(sheet1,sheet2...)
        //如果不指定工作表 默认按照：sheet1,sheet2...命名
        Sheet sheet = workbook.createSheet("用户信息");

        //创建标题行
        //创建一个标题行  参数：行下标(下标从0开始)
        Row row0 = sheet.createRow(0);

        //设置内容
        row0.createCell(0).setCellValue("学生信息");
        //合并行   参数：起始行,结束行,起始单元格,结束单元格
        CellRangeAddress addresses = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(addresses);

        //创建单元格   参数:单元格下标（下标从0开始）
        //Cell cell = row0.createCell(3);
        //给单元格设置内容
        //cell.setCellValue("这是第二行第4个单元格");

       /* //构建字体
        Font font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("楷体");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);     //将字体样式引入
        */

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //文字居中

        //创建标题行
        Row row = sheet.createRow(1);
        String[] title = {"ID", "名字", "手机号", "头像", "描述", "注册时间"};

        //处理单元格对象
        for (int i = 0; i < title.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            //设置单元格内容
            cell.setCellValue(title[i]);
            //给字体设置样式
            cell.setCellStyle(cellStyle);
        }

        //创建样式对象
        CellStyle cellStyle2 = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle2.setDataFormat(dataFormat.getFormat("yyyy/MM/dd"));

        //处理数据行
        for (int i = 0; i < list.size(); i++) {
            //遍历一次创建一行
            Row row2 = sheet.createRow(i + 1);

           /* //创建单元格
            Cell cell = row1.createCell(0);
            //设置单元格内容
            cell.setCellValue(students.get(i).getId());
            //创建单元格并设置单元格内容
            row1.createCell(1).setCellValue(students.get(i).getName());*/

            //String[] title={"ID","名字","手机号","头像","描述","注册时间"};
            //每行对应放的数据
            row2.createCell(0).setCellValue(list.get(i).getId());
            row2.createCell(1).setCellValue(list.get(i).getUsername());
            row2.createCell(2).setCellValue(list.get(i).getPhone());
            row2.createCell(3).setCellValue(list.get(i).getHead());
            row2.createCell(4).setCellValue(list.get(i).getBrief());

            //设置单元格日期格式
            Cell cell2 = row2.createCell(5);
            cell2.setCellStyle(cellStyle);    //添加日期样式
            cell2.setCellValue(list.get(i).getMins());   //添加数据
        }

        try {
            //导出Excel文档
            workbook.write(new FileOutputStream(new File("D:/190Poi.xls")));

            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
