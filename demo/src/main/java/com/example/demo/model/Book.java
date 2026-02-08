package com.example.demo.model;

public class Book extends LibraryItem {
    private Long id;
    private String author;
    private int year;

    public Book() {}

    public Book(String title, String author, int year) {
        super(title);
        this.author = author;
        this.year = year;
    }

    public Book(Long id, String title, String author, int year) {
        super(title);
        this.id = id;
        this.author = author;
        this.year = year;
    }

    public Long getId() { return id; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }

    public void setId(Long id) { this.id = id; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String getInfo() {
        return getTitle() + " by " + author + " (" + year + ")";
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + getTitle() + "', author='" + author + "', year=" + year + "}";
    }
}