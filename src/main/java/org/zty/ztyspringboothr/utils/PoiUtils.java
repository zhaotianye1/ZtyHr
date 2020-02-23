package org.zty.ztyspringboothr.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.zty.ztyspringboothr.bean.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 赵天野
 * @date 2020/3/22 15:02
 */
public class PoiUtils {
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        //1.创建一个excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档信息
        DocumentSummaryInformation documentSummaryInformation = workbook.getDocumentSummaryInformation();
        //文档类别
        documentSummaryInformation.setCategory("员工信息");
        //文档管理员
        documentSummaryInformation.setManager("zty");
        //设置公司信息
        documentSummaryInformation.setCompany("沈阳工业大学");
        //4.获取文档摘要信息
        SummaryInformation summaryInformation = workbook.getSummaryInformation();
        //文档标题
        summaryInformation.setTitle("员工信息表");
        //文档作者
        summaryInformation.setAuthor("zty");
        //文档备注
        summaryInformation.setComments("解释权由赵天野所有");

        //5.创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 5 * 256);
        sheet.setColumnWidth(8, 5 * 256);
        sheet.setColumnWidth(9, 10 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 12 * 256);
        sheet.setColumnWidth(12, 20 * 256);
        sheet.setColumnWidth(13, 10 * 256);
        sheet.setColumnWidth(14, 10 * 256);
        sheet.setColumnWidth(15, 10 * 256);
        sheet.setColumnWidth(16, 10 * 256);
        sheet.setColumnWidth(17, 12 * 256);
        sheet.setColumnWidth(18, 12 * 256);
        sheet.setColumnWidth(19, 12 * 256);
        sheet.setColumnWidth(20, 12 * 256);
        sheet.setColumnWidth(21, 10 * 256);
        sheet.setColumnWidth(22, 10 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 20 * 256);
        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c0.setCellValue("姓名");
        c1.setCellStyle(headerStyle);
        HSSFCell c2 = r0.createCell(2);
        c2.setCellValue("工号");
        c2.setCellStyle(headerStyle);
        HSSFCell c3 = r0.createCell(3);
        c3.setCellValue("性别");
        c3.setCellStyle(headerStyle);
        HSSFCell c4 = r0.createCell(4);
        c4.setCellValue("出生日期");
        c4.setCellStyle(headerStyle);
        HSSFCell c5 = r0.createCell(5);
        c5.setCellValue("身份证号码");
        c5.setCellStyle(headerStyle);
        HSSFCell c6 = r0.createCell(6);
        c6.setCellValue("婚姻状况");
        c6.setCellStyle(headerStyle);
        HSSFCell c7 = r0.createCell(7);
        c7.setCellValue("民族");
        c7.setCellStyle(headerStyle);
        HSSFCell c8 = r0.createCell(8);
        c8.setCellValue("籍贯");
        c8.setCellStyle(headerStyle);
        HSSFCell c9 = r0.createCell(9);
        c9.setCellValue("政治面貌");
        c9.setCellStyle(headerStyle);
        HSSFCell c10 = r0.createCell(10);
        c10.setCellValue("电子邮件");
        c10.setCellStyle(headerStyle);
        HSSFCell c11 = r0.createCell(11);
        c11.setCellValue("电话号码");
        c11.setCellStyle(headerStyle);
        HSSFCell c12 = r0.createCell(12);
        c12.setCellValue("联系地址");
        c12.setCellStyle(headerStyle);
        HSSFCell c13 = r0.createCell(13);
        c13.setCellValue("所属部门");
        c13.setCellStyle(headerStyle);
        HSSFCell c14 = r0.createCell(14);
        c14.setCellValue("职位");
        c14.setCellStyle(headerStyle);
        HSSFCell c15 = r0.createCell(15);
        c15.setCellValue("职称");
        c15.setCellStyle(headerStyle);
        HSSFCell c16 = r0.createCell(16);
        c16.setCellValue("聘用形式");
        c16.setCellStyle(headerStyle);
        HSSFCell c17 = r0.createCell(17);
        c17.setCellValue("入职日期");
        c17.setCellStyle(headerStyle);
        HSSFCell c18 = r0.createCell(18);
        c18.setCellValue("转正日期");
        c18.setCellStyle(headerStyle);
        HSSFCell c19 = r0.createCell(19);
        c19.setCellValue("合同起始日期");
        c19.setCellStyle(headerStyle);
        HSSFCell c20 = r0.createCell(20);
        c20.setCellValue("合同终止日期");
        c20.setCellStyle(headerStyle);
        HSSFCell c21 = r0.createCell(21);
        c21.setCellValue("合同期限（年）");
        c21.setCellStyle(headerStyle);
        HSSFCell c22 = r0.createCell(22);
        c22.setCellValue("最高学历");
        c22.setCellStyle(headerStyle);
        HSSFCell c23 = r0.createCell(23);
        c23.setCellValue("毕业院校");
        c23.setCellStyle(headerStyle);
        HSSFCell c24 = r0.createCell(24);
        c24.setCellValue("专业");
        c24.setCellStyle(headerStyle);
        for (int i = 0; i < list.size(); i++) {
            Employee employee = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getWorkID());
            row.createCell(3).setCellValue(employee.getGender());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(employee.getBirthday());
            cell4.setCellStyle(dateCellStyle);
            row.createCell(5).setCellValue(employee.getIdCard());
            row.createCell(6).setCellValue(employee.getWedlock());
            row.createCell(7).setCellValue(employee.getNation().getName());
            row.createCell(8).setCellValue(employee.getNativePlace());
            row.createCell(9).setCellValue(employee.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(employee.getEmail());
            row.createCell(11).setCellValue(employee.getPhone());
            row.createCell(12).setCellValue(employee.getAddress());
            row.createCell(13).setCellValue(employee.getDepartment().getName());
            row.createCell(14).setCellValue(employee.getPosition().getName());
            row.createCell(15).setCellValue(employee.getJobLevel().getName());
            row.createCell(16).setCellValue(employee.getEngageForm());
            HSSFCell cell17 = row.createCell(17);
            cell17.setCellValue(employee.getBeginDate());
            cell17.setCellStyle(dateCellStyle);
            HSSFCell cell18 = row.createCell(18);
            cell18.setCellValue(employee.getConversionTime());
            cell18.setCellStyle(dateCellStyle);
            HSSFCell cell19 = row.createCell(19);
            cell19.setCellValue(employee.getBeginContract());
            cell19.setCellStyle(dateCellStyle);
            HSSFCell cell20 = row.createCell(20);
            cell20.setCellValue(employee.getEndContract());
            cell20.setCellStyle(dateCellStyle);
            row.createCell(21).setCellValue(employee.getContractTerm());
            row.createCell(22).setCellValue(employee.getTiptopDegree());
            row.createCell(23).setCellValue(employee.getSchool());
            row.createCell(24).setCellValue(employee.getSpecialty());
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        HttpHeaders headers=new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment",new String("员工表.xls".getBytes("UTF-8"),"ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(b.toByteArray(),headers,HttpStatus.CREATED);
    }
//
//    /**
//     * Excel解析成员工集合
//     */
//    public static List<Employee> excel2Employee(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticsstatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevels) {
//        List<Employee> list=new ArrayList<>();
//        try {
//            //1.创建一个workbook对象
//            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
//            //2.获取workbook中表单的数量
//            int num=workbook.getNumberOfSheets();
//            for(int i=0;i<num;i++){
//                //3.获取表单
//                HSSFSheet sheet=workbook.getSheetAt(i);
//                //4.获取表单中的行数
//                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//                for (int j=0;j<physicalNumberOfRows;j++){
//                    if(j==0){
//                        continue;//跳过标题行
//                    }
//                    //5.获取行
//                    HSSFRow row = sheet.getRow(j);
//                    if(row==null){
//                        continue;//防止中间空行；
//                    }
//                    //获取列数
//                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
//                    for(int k=0;k<physicalNumberOfCells;k++){
//                        HSSFCell cell=row.getCell(k);
//                        switch (cell.getCellType()){
//                            case STRING:
//
//                                default:{
//
//                                }
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
