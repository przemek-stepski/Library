package com.library;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    Book book1 = new Book("1", "1", "1");
    Book book2 = new Book("2", "2", "2");
    List<Book> testBookList = new ArrayList<>();

    BookRepository bookRepository = new BookRepository();
    private static final String PATH_TO_TEST_FILE = "src/main/resources/katalogTest.json";

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
            FileWriter fileWriter = new FileWriter(PATH_TO_TEST_FILE);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void deleteTestFile() {
        File testFile = new File(PATH_TO_TEST_FILE);
        testFile.delete();
    }

//    @Test
//    void testCreateBookShouldReturnNewBook() {
//        BookRepository bookRepository = new BookRepository();
//        Book testBook = new Book("Java", "Josh Bloch", "1234");
//
//        String testInputTitle = "Java";
//        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
//        System.setIn(input);
//
//        String testInputAuthor = "Josh Bloch";
//        InputStream inputAuthor = new ByteArrayInputStream(testInputAuthor.getBytes());
//        System.setIn(inputAuthor);
//
//        String testInputIsbn = "1234";
//        InputStream inputIsbn = new ByteArrayInputStream(testInputIsbn.getBytes());
//        System.setIn(inputIsbn);
//
//        assertEquals(testBook, bookRepository.createBook());
//    }

    @Test
    void testAddBookShouldThrowExceptionIfWrongPath() {
        Book testBook = new Book("testTitle", "testAuthor", "1234");
        String path = "testPathToNonexistentFile";

        assertThrows(MissingFileException.class, ()-> bookRepository.addBook(path, testBook));
    }

//    @Test
//    void testDeleteBookShouldReturnTrueIfBookDeleted() throws MissingFileException {
//
//        assertTrue(bookRepository.deleteBook(PATH_TO_TEST_FILE));
//    }

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
    void testShowBorrowersShouldReturnListOfUniqueBorrowersAsSublistOfAllBorrowers() throws MissingFileException
    {
        List<String> testUniqeBorrowers = new ArrayList<>();
        testUniqeBorrowers.add("1");
        testUniqeBorrowers.add("2");

        assertEquals(testUniqeBorrowers, bookRepository.showBorrowers(PATH_TO_TEST_FILE));

    }

    void addTwoBooksToTestBookList() {
        testBookList.add(book1);
        testBookList.add(book2);
    }
}