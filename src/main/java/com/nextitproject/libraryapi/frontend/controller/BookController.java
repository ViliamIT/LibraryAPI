package com.nextitproject.libraryapi.frontend.controller;

import com.nextitproject.libraryapi.backend.entity.Book;
import com.nextitproject.libraryapi.backend.entity.BorrowerInfo;
import com.nextitproject.libraryapi.backend.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {


  @Autowired
  LibraryService libraryService;

    //@CrossOrigin(origins = "*",allowedHeaders = "*")
    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = libraryService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = libraryService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
            if(libraryService.updateBook(id,updatedBook)){
                return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
            }else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (libraryService.deleteBook(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else return ResponseEntity.notFound().build();

    }

    @GetMapping("/borrowed-books")
    public ResponseEntity<List<Book>> getBorrowedBooks() {
        List<Book> borrowedBooks = libraryService.getBorrowedBooks();
        return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
    }

    @GetMapping("/available-books")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> availableBooks = libraryService.getAvailableBooks();
        return new ResponseEntity<>(availableBooks, HttpStatus.OK);
    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<String> borrowBook(@PathVariable Long id,  @RequestBody BorrowerInfo borrowerInfo) {
        Optional<Book> optionalBook = libraryService.borrowBook(id, borrowerInfo);


        if (optionalBook.isPresent()) {
            return ResponseEntity.ok("Book borrowed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping ("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable Long id) {
        boolean returned = libraryService.returnBook(id);
        if (returned) {
            return ResponseEntity.ok("Book returned successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid book ID or book is not borrowed");
        }
    }
}