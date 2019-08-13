/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: BookController
 * Author:   clp
 * Date:     2019/8/6 21:07
 * Description: aa
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.controller;

import com.jk.model.Book;
import com.jk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈aa〉
 *
 * @author clp
 * @create 2019/8/6
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
    } @RequestMapping("toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    @RequestMapping("queryBook")
    @ResponseBody
    public Map queryBook(Integer page, Integer rows,Book book){

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

   //updateBook
   @RequestMapping("updateBook")
   @ResponseBody
   public void updateBook(Book book){
       bookService.updateBook(book);
   }
}
