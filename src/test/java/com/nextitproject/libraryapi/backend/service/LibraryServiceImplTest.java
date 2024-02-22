package com.nextitproject.libraryapi.backend.service;

import com.nextitproject.libraryapi.backend.entity.Book;
import com.nextitproject.libraryapi.backend.entity.Borrowed;
import com.nextitproject.libraryapi.backend.entity.BorrowerInfo;
import com.nextitproject.libraryapi.backend.service.impl.LibraryServiceImpl;
import com.nextitproject.libraryapi.backend.storage.LibraryStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceImplTest {

    @Mock
    private LibraryStorage libraryStorage;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Test
    public void testUpdateBook() {
        // Pripraviť fiktívne dáta
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setName("Existing Book");

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setName("Updated Book");

        when(libraryStorage.updateBook(1L, updatedBook)).thenReturn(true);

        // Zavolať testovanú metódu
        boolean result = libraryService.updateBook(1L, updatedBook);

        // Overiť výsledok
        assertTrue(result);
        verify(libraryStorage, times(1)).updateBook(1L, updatedBook);
    }

    @Test
    public void testDeleteBook() {
        // Pripraviť fiktívne dáta
        when(libraryStorage.removeBook(1L)).thenReturn(true);

        // Zavolať testovanú metódu
        boolean result = libraryService.deleteBook(1L);

        // Overiť výsledok
        assertTrue(result);
        verify(libraryStorage, times(1)).removeBook(1L);
    }

    @Test
    public void testGetBorrowedBooks() {
        // Pripraviť fiktívne dáta
        Book book1 = new Book();   //(1L, "Borrowed Book 1""John", "Doe", "2024-01-01"
        book1.setId(1L);
        book1.setName("Borrowed Book 1");
        book1.setAuthor("Author 1");
        Borrowed borrowed1 = new Borrowed();
        borrowed1.setFirstName("John");
        borrowed1.setLastName("Doe");
        borrowed1.setFrom("2024-01-01");
        book1.setBorrowed(borrowed1);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Borrowed Book 2");
        book2.setAuthor("Author 2");
        Borrowed borrowed2 = new Borrowed();
        borrowed2.setFirstName("Bill");
        borrowed2.setLastName("Belford");
        borrowed2.setFrom("2024-02-01");
        book2.setBorrowed(borrowed2);
        //new Book(, "Borrowed Book 2","Author 2", new Borrowed())
        List<Book> borrowedBooks = Arrays.asList(
                book1,book2
        );
        when(libraryStorage.getBooks()).thenReturn(borrowedBooks);

        // Zavolať testovanú metódu
        List<Book> result = libraryService.getBorrowedBooks();

        // Overiť výsledok
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(book -> book.getBorrowed() != null));
    }

    @Test
    public void testGetAvailableBooks() {
        // Pripraviť fiktívne dáta
        Book book1 = new Book();   //(1L, "Borrowed Book 1""John", "Doe", "2024-01-01"
        book1.setId(1L);
        book1.setName("Borrowed Book 1");
        book1.setAuthor("Author 1");
        Borrowed borrowed1 = new Borrowed();
        borrowed1.setFirstName("John");
        borrowed1.setLastName("Doe");
        borrowed1.setFrom("2024-01-01");
        book1.setBorrowed(borrowed1);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Borrowed Book 2");
        book2.setAuthor("Author 2");
        Borrowed borrowed2 = new Borrowed();
        borrowed2.setFirstName("Bill");
        borrowed2.setLastName("Belford");
        borrowed2.setFrom("2024-02-01");
        book2.setBorrowed(borrowed2);
        List<Book> availableBooks = Arrays.asList(
                book1,book2
        );
        when(libraryStorage.getBooks()).thenReturn(availableBooks);

        // Zavolať testovanú metódu
        List<Book> result = libraryService.getAvailableBooks();

        // Overiť výsledok
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(book -> book.getBorrowed() == null));
    }

    @Test
    public void testBorrowBook() {
        // Pripraviť fiktívne dáta
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setName("Existing Book");

        BorrowerInfo borrowerInfo = new BorrowerInfo();
        borrowerInfo.setFirstName("John");
        borrowerInfo.setLastName("Doe");

        when(libraryStorage.updateBook(1L, existingBook)).thenReturn(true);
        when(libraryStorage.findBookById(1L)).thenReturn(existingBook);

        // Zavolať testovanú metódu
        Optional<Book> result = libraryService.borrowBook(1L, borrowerInfo);

        // Overiť výsledok
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getBorrowed().getFirstName());
        assertEquals("Doe", result.get().getBorrowed().getLastName());
    }

    @Test
    public void testReturnBook() {
        // Pripraviť fiktívne dáta
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setName("Existing Book");
        existingBook.setBorrowed(new Borrowed("John", "Doe", "2024-02-05"));

        when(libraryStorage.findBookById(1L)).thenReturn(existingBook);

        // Zavolať testovanú metódu
        boolean result = libraryService.returnBook(1L);

        // Overiť výsledok
        assertTrue(result);
        assertNull(existingBook.getBorrowed());
    }
}