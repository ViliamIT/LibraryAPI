package com.nextitproject.libraryapi.backend.service;

import com.nextitproject.libraryapi.backend.entity.Book;
import com.nextitproject.libraryapi.backend.entity.BorrowerInfo;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book createBook(Book book);
    boolean updateBook(Long id, Book updatedBook);
    boolean deleteBook(Long id);
    List<Book> getBorrowedBooks();
    List<Book> getAvailableBooks();

    Optional<Book> borrowBook(Long id, BorrowerInfo borrowerInfo);

    boolean returnBook(Long id);
}
