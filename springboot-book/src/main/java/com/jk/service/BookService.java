package com.jk.service;

import com.jk.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Map queryBook(Integer page, Integer rows,Book book);

    void addBook(Book book);

    void deleteBook(String ids);

    Book toUpdateBook(Integer bookid);

    void updateBook(Book book);
}
