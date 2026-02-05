package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }


    @GetMapping
    public List<Book> getAll() {
        return repo.findAll();
    }


    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return repo.save(book);
    }


    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book newBook) {
        Book book = repo.findById(id).orElseThrow();
        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setYear(newBook.getYear());
        return repo.save(book);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }


    @GetMapping("/search")
    public List<Book> search(@RequestParam String title) {
        return repo.findByTitleContainingIgnoreCase(title);
    }
}

