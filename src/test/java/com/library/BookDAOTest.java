package com.library;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    Book book1 = new Book("T", "A", "1");
    Book book2 = new Book("TT", "AA", "22");
    List<Book> testBookList = new ArrayList<>();
    private static final String COMMON_TEST_FILE = "src/main/resources/katalogTestBeforeAll.json";
    private static final String TEST_FILE = "src/main/resources/katalogTest.json";

    @BeforeAll
    static void createTestFile() throws IOException {

        String fileContent = "[\n" +
                "  {\n" +
                "    \"title\": \"T\",\n" +
                "    \"author\": \"A\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"TT\",\n" +
                "    \"author\": \"AA\",\n" +
                "    \"isbn\": \"22\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(COMMON_TEST_FILE);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new FileWriter(TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void deleteTestFile() {
        File commonTestFile = new File(COMMON_TEST_FILE);
        commonTestFile.delete();

        File testFile = new File(TEST_FILE);
        testFile.delete();
    }

    @Test
    void testMakeListShouldReturnEqualListOfBooks() throws MissingFileException {
        addTwoBooksToTestBookList();

        assertEquals(testBookList, BookDAO.makeListFromJson(COMMON_TEST_FILE));
    }

    @Test
    void testMakeListShouldThrowMyExceptionIfWrongPath() {
        String path = "wrong path to file";

        assertThrows(MissingFileException.class, () -> BookDAO.makeListFromJson(path));
    }

    @Test
    void testMakeListShouldThrowMyExceptionIfPatNull() {

        assertThrows(MissingFileException.class, () -> BookDAO.makeListFromJson(null));
    }

    @Test
    void testMakeJsonShouldReturnTrueIfCreatedJsonFile() {
        addTwoBooksToTestBookList();

        assertTrue(BookDAO.makeJsonFromList((ArrayList) testBookList, TEST_FILE));
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