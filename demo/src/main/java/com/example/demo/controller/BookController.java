package com.example.demo.controller;

import com.example.demo.dao.BookDao;
import com.example.demo.dao.JdbcBookDao;
import com.example.demo.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookDao dao = new JdbcBookDao();

    @GetMapping
    public List<Book> all() { return dao.getAll(); }

    @GetMapping("/{id}")
    public Book byId(@PathVariable long id) { return dao.getById(id); }

    @PostMapping
    public Book create(@RequestBody Book book) { return dao.create(book); }

    @PutMapping("/{id}")
    public Book update(@PathVariable long id, @RequestBody Book book) { return dao.update(id, book); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) { dao.delete(id); }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String title) { return dao.findByTitle(title); }

    @GetMapping("/filter")
    public List<Book> filter(@RequestParam int year) { return dao.filterByYear(year); }

    @GetMapping("/sort")
    public List<Book> sort() { return dao.sortByYear(); }
}
