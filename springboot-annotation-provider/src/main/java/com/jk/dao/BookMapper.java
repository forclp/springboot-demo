package com.jk.dao;

import com.jk.model.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Integer bookid);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bookid);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> queryBook(@Param("sta") int sta, @Param("rows") Integer rows, @Param("book") Book book);

    long queryCount();

    void deleteBook(@Param("ids") String ids);

    Book toUpdateBook(@Param("bookid") Integer bookid);
}