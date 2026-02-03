package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int year;

    public Book(){}

    public Long getId(){ return id; }
    public String getTitle(){ return title; }
    public String getAuthor(){ return author; }
    public int getYear(){ return year; }

    public void setTitle(String t){ this.title=t; }
    public void setAuthor(String a){ this.author=a; }
    public void setYear(int y){ this.year=y; }
}