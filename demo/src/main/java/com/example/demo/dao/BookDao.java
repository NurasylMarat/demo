package com.example.demo.dao;

import com.example.demo.model.Book;
import java.util.List;

public interface BookDao {
    List<Book> getAll();
    Book getById(long id);
    Book create(Book book);
    Book update(long id, Book book);
    void delete(long id);

    List<Book> findByTitle(String title);
    List<Book> filterByYear(int fromYear);
    List<Book> sortByYear();
}
