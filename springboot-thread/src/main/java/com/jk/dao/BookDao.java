/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BookDao
 * Author:   zyl
 * Date:     2019/8/8 14:28
 * History:
 */
package com.jk.dao;


import com.jk.model.Book;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zyl
 * @create 2019/8/8
 * @since 1.0.0
 */
public interface BookDao {
    List<Book> query();

    void addBook(List<Book> list);

    void addBook1(Book book);
}
