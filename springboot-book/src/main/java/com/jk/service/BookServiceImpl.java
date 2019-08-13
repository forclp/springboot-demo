/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: BookServiceImpl
 * Author:   clp
 * Date:     2019/8/6 22:54
 * Description: a
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.service;

import com.jk.dao.BookMapper;
import com.jk.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈a〉
 *
 * @author clp
 * @create 2019/8/6
 * @since 1.0.0
 */
@Service
public class BookServiceImpl implements  BookService{

    @Autowired
    private BookMapper bookMapper;


    @Override
    public Map queryBook(Integer page, Integer rows,Book book) {
        int sta=(page-1)*rows;
        List<Book> list=bookMapper.queryBook(sta,rows,book);
        long count=bookMapper.queryCount();
        Map map=new HashMap();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }

    @Override
    public void addBook(Book book) {
        bookMapper.insertSelective(book);
    }

    @Override
    public void deleteBook(String ids) {
        bookMapper.deleteBook(ids);
    }

    @Override
    public  Book  toUpdateBook(Integer bookid) {
        return bookMapper.toUpdateBook(bookid);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }


}
