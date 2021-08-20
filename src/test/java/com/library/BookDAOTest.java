package com.library;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    Book book1 = new Book("1", "1", "1");
    Book book2 = new Book("2", "2", "2");
    List<Book> testBookList = new ArrayList<>();
    private static final String PATH = "src/main/resources/katalogTest1.json";

    @BeforeAll
    static void createTestFile() {

        String fileContent = "[\n" +
                "  {\n" +
                "    \"title\": \"1\",\n" +
                "    \"author\": \"1\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"2\",\n" +
                "    \"author\": \"2\",\n" +
                "    \"isbn\": \"2\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(PATH);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMakeListShouldReturnListOfBooks() throws MyException {
        addTwoBooksToTestBookList();

        assertEquals(testBookList, BookDAO.makeListFromJson(PATH));
    }

    @Test
    void testMakeListShouldThrowMyExceptionIfWrongPath() throws MyException {
        String path = "wrong path to file";

        assertThrows(MyException.class, () -> BookDAO.makeListFromJson(path));
    }

    @Test
    void testMakeListShouldThrowMyExceptionIfPatNull() throws MyException {

        assertThrows(MyException.class,() -> BookDAO.makeListFromJson(null));
    }

    @Test
    void testMakeJsonShouldReturnTrueIfCreatedJsonFile() {
        addTwoBooksToTestBookList();

        assertTrue(BookDAO.makeJsonFromList((ArrayList) testBookList, PATH));
    }

    @Test
    void testMakeJsonShouldReturnFalseIfWrongPath() {
        String path = "wrong path to file";

        assertFalse(BookDAO.makeJsonFromList((ArrayList) testBookList, path));
    }

    @Test
    void testMakeJsonShouldReturnFalseIfPathNull() {

        assertFalse(BookDAO.makeJsonFromList((ArrayList) testBookList, null));
    }

    void addTwoBooksToTestBookList() {
        testBookList.add(book1);
        testBookList.add(book2);
    }
}