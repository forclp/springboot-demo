/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: BookController
 * Author:   clp
 * Date:     2019/8/7 17:41
 * Description: 控制层-消费者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.controller;

import com.jk.model.Book;
import com.jk.service.BookService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jk.util.ExportExcel;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
/**
 * 〈一句话功能简述〉<br> 
 * 〈控制层-消费者〉
 *
 * @author clp
 * @create 2019/8/7
 * @since 1.0.0
 */

@RequestMapping("/book")
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("toShowBook")
    public String toShowBook(){
        return "showBook";
    }
 @RequestMapping("toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    @RequestMapping("queryBook")
    @ResponseBody
    public Map queryBook(Integer page, Integer rows, Book book){
        return bookService.queryBook(page,rows,book);
    }


    @RequestMapping("addBook")
    @ResponseBody
    public void addBook(Book book){
        bookService.addBook(book);
    }
    @RequestMapping("deleteBook")
    @ResponseBody
    public void deleteBook(String ids){
        bookService.deleteBook(ids);
    }

    @RequestMapping("toUpdateBook")
    public String toUpdateBook(Integer bookid, Model model){
        Book book=bookService.toUpdateBook(bookid);
        model.addAttribute("book",book);
        return "updateBook";
    }

    @RequestMapping("updateBook")
    @ResponseBody
    public void updateBook(Book book){
        bookService.updateBook(book);
    }


    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response){
        //导出的excel的标题
        String title = "书籍管理";
        //导出excel的列名
        String[] rowName = {"书籍编号","书籍名称","书籍价格","书籍类型","书籍描述","书籍日期"};
        //导出的excel数据
        List<Object[]>  dataList = new ArrayList<Object[]>();
        //查询的数据库的书籍信息
        List<Book> list=   bookService.query();
        //循环书籍信息
        for(Book book:list){
            Object[] obj =new Object[rowName.length];
            obj[0]=book.getBookid();
            obj[1]=book.getBookname();
            obj[2]=book.getPrice();
            if(book.getBooktype()==1){
                obj[3]="小说";
            }else{
                obj[3]="名著";
            }
            obj[4]=book.getDes();
            obj[5]=book.getBookdate();
            dataList.add(obj);
        }
        ExportExcel exportExcel =new ExportExcel(title,rowName,dataList,response);
        try {
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //exportExcelCheck
    @RequestMapping("exportExcelCheck")
    @ResponseBody
    public void exportExcelCheck(HttpServletResponse response,String check){
        //导出的excel的标题
        String title = "书籍管理";
        //导出excel的列名
        String[] rowName = check.split(",");
        //导出的excel数据
        List<Object[]>  dataList = new ArrayList<Object[]>();
        //查询的数据库的书籍信息
        List<Book> list=   bookService.query();
        //循环书籍信息
        for (Book book:list) {
            Object[] obj =new Object[rowName.length];
            for (int i=0;i<obj.length;i++){

                if(rowName[i].equals("书籍编号")){
                  obj[i]=book.getBookid();
                }
                if(rowName[i].equals("书籍名称")){
                  obj[i]=book.getBookname();
                }
                if(rowName[i].equals("书籍价格")){
                  obj[i]=book.getPrice();
                }
                if(rowName[i].equals("书籍类型")){
                    if(book.getBooktype()==1){
                        obj[i]="小说";
                    }else{
                        obj[i]="名著";
                    }
                }
                if(rowName[i].equals("书籍描述")){
                  obj[i]=book.getDes();
                }
                if(rowName[i].equals("书籍日期")){
                  obj[i]=book.getBookdate();
                }
            }
            dataList.add(obj);
        }
        ExportExcel exportExcel =new ExportExcel(title,rowName,dataList,response);
        try {
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }











  @RequestMapping("importExcel")
  public String importExcel(MultipartFile file, HttpServletResponse response){
      //获得上传文件上传的类型
      String contentType = file.getContentType();
      //上传文件的名称
      String fileName = file.getOriginalFilename();
      //获得文件的后缀名
      String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
      //上传文件的新的路径
      //生成新的文件名称
      String filePath = "./src/main/resources/templates/fileupload/";
      //创建list集合接收excel中读取的数据
      List<Book> list =new ArrayList<Book>();
      try {
          uploadFile(file.getBytes(), filePath, fileName);
          SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
          //通过文件忽的WorkBook
          FileInputStream fileInputStream = new FileInputStream(filePath+fileName);
          Workbook workbook = WorkbookFactory.create(fileInputStream);
          //通过workbook对象获得sheet页 有可能不止一个sheet
          for(int i=0 ;i<workbook.getNumberOfSheets();i++){
              //获得里面的每一个sheet对象
              Sheet sheetAt = workbook.getSheetAt(i);
              //通过sheet对象获得每一行
              for(int j=3;j<sheetAt.getPhysicalNumberOfRows();j++){
                  //创建一个book对象接收excel的数据
                  Book book=new Book();
                  //获得每一行的数据
                  Row row = sheetAt.getRow(j);

                  //获得每一个单元格的数据
                  if(row.getCell(1)!=null && !"".equals(row.getCell(1))){
                      book.setBookname(this.getCellValue(row.getCell(1)));
                  }
                  if(row.getCell(2)!=null && !"".equals(row.getCell(2))){
                      book.setPrice(Double.parseDouble(this.getCellValue(row.getCell(2))));
                  }
                  if(row.getCell(3)!=null && !"".equals(row.getCell(3))){
                      String cellValue = this.getCellValue(row.getCell(3));
                      if("小说".equals(cellValue)){
                          book.setBooktype(1);
                      }else{
                          book.setBooktype(2);
                      }
                  }
                  if(row.getCell(4)!=null && !"".equals(row.getCell(4))){
                      book.setDes(this.getCellValue(row.getCell(4)));
                  }
                  if(row.getCell(5)!=null && !"".equals(row.getCell(5))){
                      book.setBookdate(this.getCellValue(row.getCell(5)));
                  }

                  list.add(book);
              }
          }
          bookService.add(list);

      } catch (Exception e) {
          e.printStackTrace();
      }
   return "showBook";
  }

    //上传文件的方法
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(Cell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }
    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(XSSFCell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case HSSFCell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }


}
