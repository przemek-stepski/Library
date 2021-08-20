package com.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class BookRepositoryTest {
    BookRepository bookRepository = new BookRepository();

    @Test
    void testCreateBookShouldReturnNewBook() {
        BookRepository bookRepository = new BookRepository();
        Book testBook = new Book("Java", "Josh Bloch", "1234");

        String testInputTitle = "Java";
        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
        System.setIn(input);

        String testInputAuthor = "Josh Bloch";
        InputStream inputAuthor = new ByteArrayInputStream(testInputAuthor.getBytes());
        System.setIn(inputAuthor);

        String testInputIsbn = "1234";
        InputStream inputIsbn = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputIsbn);

        Assertions.assertEquals(testBook, bookRepository.createBook());
    }

    @Test
    void addBook() {
    }

    @Test
    void testDeleteBookShouldReturnTrueIfBookDeleted() {
        String path = "src/main/resources/katalogTest1.json";



    }

    @Test
    void testFindBookByAuthorShouldReturnListOfFoundBooks() {
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
    void testShowBorrowersShouldReturnListOfUniqueBorrowersAsSublistOfAllBorrowers() throws MyException {
        String path = "src/main/resources/katalogTest1.json";

        List<String> testUniqeBorrowers = new ArrayList<>();
        testUniqeBorrowers.add("1");
        testUniqeBorrowers.add("2");

        Assertions.assertEquals( testUniqeBorrowers, bookRepository.showBorrowers(path) );

    }
}