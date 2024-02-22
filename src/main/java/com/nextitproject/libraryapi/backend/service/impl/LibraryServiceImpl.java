package com.nextitproject.libraryapi.backend.service.impl;

import com.nextitproject.libraryapi.backend.storage.LibraryStorage;
import com.nextitproject.libraryapi.backend.entity.Book;
import com.nextitproject.libraryapi.backend.entity.Borrowed;
import com.nextitproject.libraryapi.backend.entity.BorrowerInfo;
import com.nextitproject.libraryapi.backend.service.LibraryService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final static Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
    @Autowired
    private LibraryStorage libraryStorage;



    public Optional<Book> getBookById(Long id) {
        Book book;
        book = libraryStorage.findBookById(id);
        return Optional.ofNullable(book);
    }

    public List<Book> getAllBooks() {
        return libraryStorage.getBooks();
    }
    public Book createBook(Book book) {
        logger.info("creating book:{}",book.getName());
        libraryStorage.addBook(book);
        return book;
    }

    public boolean updateBook(Long id, Book updatedBook) {
        logger.info("updating book:{}",updatedBook.getName());
        updatedBook.setId(id);
        if (libraryStorage.updateBook(id, updatedBook)) {
            return true;
        } else return false;
    }

    public boolean deleteBook(Long id) {
        if (libraryStorage.removeBook(id)) {
            return true;
        } else return false;
    }

    public List<Book> getBorrowedBooks() {
        return libraryStorage.getBooks().stream()
                .filter(book -> book.getBorrowed() != null && !StringUtils.isBlank(book.getBorrowed().getFrom()))
                .collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return libraryStorage.getBooks().stream()
                .filter(book -> book.getBorrowed() == null || StringUtils.isBlank(book.getBorrowed().getFrom()))
                .collect(Collectors.toList());
    }

    public Optional<Book> borrowBook(Long id, BorrowerInfo borrowerInfo) {
        Optional<Book> optionalBook = getBookById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if ((book.getBorrowed() == null ||
                    (book.getBorrowed().getFirstName() == null || book.getBorrowed().getFirstName().isEmpty()) ||
                    (book.getBorrowed().getLastName() == null || book.getBorrowed().getLastName().isEmpty()) ||
                    (book.getBorrowed().getFrom() == null || book.getBorrowed().getFrom().isEmpty()))) {
                Borrowed borrowed = new Borrowed();
                borrowed.setFirstName(borrowerInfo.getFirstName());
                borrowed.setLastName(borrowerInfo.getLastName());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String currentDate = dateFormat.format(new Date());
                borrowed.setFrom(currentDate);

                book.setBorrowed(borrowed);
                libraryStorage.updateBook(id, book);
                return Optional.of(book);
            }
        }
        return optionalBook;
    }

    public boolean returnBook(Long id) {
        Optional<Book> optionalBook = getBookById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (book.getBorrowed() != null) {
                book.getBorrowed().setFirstName("");
                book.getBorrowed().setLastName("");
                book.getBorrowed().setFrom("");

                return true;
            }
        }
        return false;
    }
}