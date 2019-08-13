/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: BookService
 * Author:   clp
 * Date:     2019/8/7 16:51
 * Description: a
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.service;

import com.jk.model.Book;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈a〉
 *
 * @author clp
 * @create 2019/8/7
 * @since 1.0.0
 */
public interface BookService {
   Map queryBook(Integer page, Integer rows, Book book);

   void addBook(Book book);

   void deleteBook(String ids);

   Book toUpdateBook(Integer bookid);

   void updateBook(Book book);
}
