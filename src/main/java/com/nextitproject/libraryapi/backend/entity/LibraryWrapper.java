package com.nextitproject.libraryapi.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextitproject.libraryapi.backend.entity.Book;

import java.util.List;

public class LibraryWrapper {

    @JsonProperty("Library")
    private List<Book> books;

    public LibraryWrapper(List<Book> books) {
        this.books = books;
    }
    public LibraryWrapper() {
        this.books = books;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}