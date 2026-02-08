package com.example.demo.model;

public abstract class LibraryItem {
    private String title;

    public LibraryItem() {}

    public LibraryItem(String title) { this.title = title; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public abstract String getInfo();
}