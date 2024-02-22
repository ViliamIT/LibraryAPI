package com.nextitproject.libraryapi.backend.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nextitproject.libraryapi.backend.entity.Book;
import com.nextitproject.libraryapi.backend.entity.LibraryWrapper;
import com.nextitproject.libraryapi.config.AppProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Component
public class LibraryStorage {
    private String filePath;

    LibraryWrapper libraryWrapper;

    private AppProperties appProperties;

    public LibraryStorage(AppProperties appProperties) {
        this.libraryWrapper = new LibraryWrapper();
        this.appProperties = appProperties;
        this.filePath = appProperties.getLibraryFilePath();
    }
    @PostConstruct
    public void postConstruct(){
        initializeLibrary();
    }
    public List<Book> getBooks() {
        return libraryWrapper.getBooks();
    }

    public void setBooks(List<Book> books) {
        this.libraryWrapper.setBooks(books);
    }

    public void addBook(Book book) {

            this.libraryWrapper.getBooks().add(book);
            saveToJsonFile();
    }

    public boolean removeBook(Long id) {
        for (int i = 0; i < this.libraryWrapper.getBooks().size(); i++) {
            Book existingBook = this.libraryWrapper.getBooks().get(i);

            if (existingBook.getId().equals(id)) {
                this.libraryWrapper.getBooks().remove(existingBook);
                saveToJsonFile();
                return true;
            }
        }
        return false;
    }

    public Book findBookById(Long id) {
        for (Book book : this.libraryWrapper.getBooks()) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public boolean updateBook(Long id, Book updatedBook) {
        for (int i = 0; i < this.libraryWrapper.getBooks().size(); i++) {
            Book existingBook = this.libraryWrapper.getBooks().get(i);

            if (existingBook.getId().equals(id)) {
                existingBook.setName(updatedBook.getName());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setBorrowed(updatedBook.getBorrowed());
                saveToJsonFile();
                return true;
            }
        }
        return false;
    }

    public void saveToJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Táto riadka zabezpečí pekné formátovanie

        try {
            objectMapper.writeValue(new File(filePath), libraryWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeLibrary() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] inputStream = Files.readAllBytes(Paths.get(filePath));
            libraryWrapper=objectMapper.readValue(inputStream, LibraryWrapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
