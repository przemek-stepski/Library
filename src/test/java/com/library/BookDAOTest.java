package com.library;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDAOTest {


    Book book1 = new Book("1", "1", "1");
    Book book2 = new Book("2", "2", "2");
    List<Book> testBookList = new ArrayList<>();

    @Test
    void shouldReturnListOfBooks() {
        testBookList.add(book1);
        testBookList.add(book2);
        String path = "src/main/resources/katalogTest1.json";

        assertEquals(testBookList, BookDAO.makeListFromJson(path));
    }

    @Test
    void shouldReturnNullIfWrongPath() {
        String path = "wrong path to file";

        assertNull(BookDAO.makeListFromJson(path));
    }

    @Test
    void shouldReturnTrueIfCreatedJsonFile() {
        testBookList.add(book1);
        testBookList.add(book2);
        String path = "src/main/resources/katalogTest2.json";

        assertTrue(BookDAO.makeJsonFromList((ArrayList) testBookList, path));
    }

    @Test
    void shouldReturnFalseIfWrongPath() {
        String path = "wrong path to file2";

        assertTrue(BookDAO.makeJsonFromList((ArrayList) testBookList, path));
    }
}