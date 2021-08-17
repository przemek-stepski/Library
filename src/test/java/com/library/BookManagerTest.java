package com.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class BookManagerTest {

    @Test
    void testCreateBookShouldReturnNewBook() {
        BookManager bookManager = new BookManager();
        Book testBook = new Book("Java", "Josh Bloch", "1234");
        String testInputTitle = "Java";
        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
        System.setIn(input);

        Assertions.assertEquals(testBook, bookManager.createBook());
    }

    @Test
    void addBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void findBookByAuthor() {
    }

    @Test
    void findBookByTitle() {
    }

    @Test
    void findBookByIsbn() {
    }

    @Test
    void findBooksNotBorrowed() {
    }

    @Test
    void borrowBook() {
    }

    @Test
    void showBorrowers() {
    }
}