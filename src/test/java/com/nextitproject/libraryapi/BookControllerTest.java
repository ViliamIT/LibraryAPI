package com.nextitproject.libraryapi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Príklad očakávanej odpovede
    }
    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Example Title"));
    }

    @Test
    public void testCreateBook() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book Title\", \"author\": \"Author Name\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book Title"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetBorrowedBooks() throws Exception {
        mockMvc.perform(get("/api/books/borrowed-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAvailableBooks() throws Exception {
        mockMvc.perform(get("/api/books/available-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void testBorrowBook() throws Exception {
        mockMvc.perform(post("/api/books/borrow/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"borrowerName\": \"John Doe\", \"borrowDate\": \"2024-02-05\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully"));
    }

    @Test
    public void testReturnBook() throws Exception {
        mockMvc.perform(get("/api/books/return/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully"));
    }
    // Ďalšie testovacie prípady pre ostatné metódy kontroléra...
}
