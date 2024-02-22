package com.nextitproject.libraryapi.backend.entity;

public class Book {

    private Long id;


    private String name;
    private String author;

    private Borrowed borrowed;

    public Book(Borrowed borrowed){
        this.borrowed = borrowed;
    }

    public Book() {

    }


    // Getters and setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Borrowed getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Borrowed borrowed) {
        this.borrowed = borrowed;
    }


}