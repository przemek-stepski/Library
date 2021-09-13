package com.library;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    Book book1 = new Book("1", "1", "1");
    Book book2 = new Book("2", "2", "2");
    List<Book> testBookList = new ArrayList<>();

    BookRepository bookRepository = new BookRepository();
    private static final String PATH_TO_TEST_FILE = "src/main/resources/katalogTest.json";

    @BeforeEach
    void createTestFile() throws IOException {

        String fileContent = "[\n" +
                "  {\n" +
                "    \"author\": \"1\",\n" +
                "    \"title\": \"1\",\n" +
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

    @AfterEach
    void deleteTestFile() {
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
    void testAddBookShouldResultJsonFileWithAddedBook() throws IOException, MissingFileException {
        String pathOneTest = "src/main/resources/test.json";
        String fileContent = "[]";
        FileWriter fileWriter = new FileWriter(pathOneTest);
        fileWriter.write(fileContent);
        fileWriter.flush();

        bookRepository.addBook(pathOneTest, book1);
        bookRepository.addBook(pathOneTest, book2);

        String file1ToString = new String(Files.readAllBytes(Paths.get(pathOneTest)));
        String file2ToString = new String(Files.readAllBytes(Paths.get(PATH_TO_TEST_FILE)));

        JsonElement jsonElement1 = JsonParser.parseString(file1ToString);
        JsonElement jsonElement2 = JsonParser.parseString(file2ToString);

        assertEquals(jsonElement1, jsonElement2);

        new File(pathOneTest).delete();
    }

    @Test
    void testAddBookShouldThrowExceptionIfFileEmpty() throws IOException {
        String pathTest = "src/main/resources/test.json";
        // create empty file
        new FileWriter(pathTest);

        assertThrows(NullPointerException.class, () -> bookRepository.addBook(pathTest, book1));
        File testFile = new File(pathTest);
        testFile.delete();
    }

    @Test
    void testAddBookShouldThrowExceptionIfFileNotJsonFormat() throws IOException {
        String pathTest = "src/main/resources/test.json";
        String fileContent = "not json format";
        FileWriter fileWriter = new FileWriter(pathTest);
        fileWriter.write(fileContent);
        fileWriter.flush();

        assertThrows(MissingFileException.class, () -> bookRepository.addBook(pathTest, book1));
        File testFile = new File(pathTest);
        testFile.delete();
    }

    @Test
    void testAddBookShouldThrowExceptionIfWrongPath() {
        String path = "testPathToNonexistentFile";

        assertThrows(MissingFileException.class, () -> bookRepository.addBook(path, book1));
    }

    @Test
    void testAddBookShouldThrowExceptionIfPathNull() {
        String path = null;

        assertThrows(MissingFileException.class, () -> bookRepository.addBook(path, book1));
    }

    @Test
    void testDeleteBookShouldReturnTrueIfBookDeleted() throws MissingFileException {
        String testInputIsbn = "1";
        InputStream inputIsbn = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputIsbn);

        assertTrue(bookRepository.deleteBook(PATH_TO_TEST_FILE));
    }

    @Test
    void testDeleteBookShouldReturnFalseIfBookNotInList() throws MissingFileException {
        String testInputIsbn = "7777777";
        InputStream inputIsbn = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputIsbn);

        assertFalse(bookRepository.deleteBook(PATH_TO_TEST_FILE));
    }

    @Test
    void testDeleteBookShouldReturnResultJsonWithoutDeletedBook() throws IOException {
        String pathTest = "src/main/resources/test.json";
        String fileContent = "[\n" +
                "  {\n" +
                "    \"author\": \"2\",\n" +
                "    \"title\": \"2\",\n" +
                "    \"isbn\": \"2\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(pathTest);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String testInputIsbn = "1";
        InputStream inputIsbn = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputIsbn);

        try {
            bookRepository.deleteBook(PATH_TO_TEST_FILE);
        } catch (MissingFileException e) {
            e.getMessage();
        }

        String stringFromFile1 = new String(Files.readAllBytes(Paths.get(pathTest)));
        String stringFromFile2 = new String(Files.readAllBytes(Paths.get(PATH_TO_TEST_FILE)));

        JsonElement jsonElement1 = JsonParser.parseString(stringFromFile1);
        JsonElement jsonElement2 = JsonParser.parseString(stringFromFile2);

        assertEquals(jsonElement1, jsonElement2);

        new File(pathTest).delete();
    }

    @Test
    void testFindBookByAuthorShouldReturnListOfFoundBooks() throws MissingFileException {
        addOneBookToTestBookList();

        String testInputTitle = "1";
        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
        System.setIn(input);
        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void testFindBookByAuthorShouldReturnEmptyListIfAuthorNotInCatalogue() throws MissingFileException {
        String testInputTitle = "wrongAuthor";
        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
        System.setIn(input);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void testFindBookByTitleShouldReturnListOfFoundBooks() throws MissingFileException {
        addOneBookToTestBookList();

        String testInputAuthor = "1";
        InputStream inputStream = new ByteArrayInputStream(testInputAuthor.getBytes());
        System.setIn(inputStream);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList);
    }

    @Test
    void testFindBookByTitleShouldReturnEmptyListIfTitleNotInCatalogue() throws MissingFileException {
        String testInputAuthor = "wrongTitle";
        InputStream inputStream = new ByteArrayInputStream(testInputAuthor.getBytes());
        System.setIn(inputStream);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void findBookByIsbnShouldReturnListOfBooks() throws MissingFileException {
        addOneBookToTestBookList();

        String testInputIsbn = "1";
        InputStream inputStream = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputStream);

        List testedMethodList = bookRepository.findBookByIsbn(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void findBookByIsbnShouldReturnEmptyLstIfIsbnNotInCatalogue() throws MissingFileException {
        String testInputIsbn = "wrongIsbn";
        InputStream inputStream = new ByteArrayInputStream(testInputIsbn.getBytes());
        System.setIn(inputStream);

        List testedMethodList = bookRepository.findBookByIsbn(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void findBooksNotBorrowedShouldReturnListOfBooksNeverBorrowed() throws MissingFileException {
        addTwoBooksToTestBookList();

        String testInputWeeksNumber = "1";
        InputStream inputStream = new ByteArrayInputStream(testInputWeeksNumber.getBytes());
        System.setIn(inputStream);

        List testedMethodList = bookRepository.findBooksNotBorrowed(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void findBooksNotBorrowedShouldReturnListOfNotBorrowedBooksInLastWeek() throws MissingFileException {
        String pathTest = "src/main/resources/test.json";
        String lastBorrowDate = LocalDate.now().toString();

        String fileContent = "[\n" +
                "  {\n" +
                "    \"author\": \"1\",\n" +
                "    \"title\": \"1\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"2\",\n" +
                "    \"author\": \"2\",\n" +
                "    \"isbn\": \"2\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(pathTest);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addOneBookToTestBookList();

        InputStream inputStream = new ByteArrayInputStream(("1").getBytes());
        System.setIn(inputStream);

        assertEquals(testBookList, bookRepository.findBooksNotBorrowed(pathTest));

        new File(pathTest).delete();
    }

    @Test
    void findBooksNotBorrowedShouldReturnEmptyListIfAllBooksWasBorrowedInLastWeek() throws MissingFileException {
        String pathTest = "src/main/resources/test.json";
        String lastBorrowDate = LocalDate.now().toString();

        String fileContent = "[\n" +
                "  {\n" +
                "    \"author\": \"1\",\n" +
                "    \"title\": \"1\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"2\",\n" +
                "    \"author\": \"2\",\n" +
                "    \"isbn\": \"2\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"never borrowed\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(pathTest);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream inputStream = new ByteArrayInputStream("1".getBytes());
        System.setIn(inputStream);

        assertTrue(bookRepository.findBooksNotBorrowed(pathTest).isEmpty());

        new File(pathTest).delete();
    }

//    @Test
//    void borrowBook() throws IOException, MissingFileException {
//        String pathTest = "src/main/resources/test.json";
//        String fileContent = "[\n" +
//                "  {\n" +
//                "    \"author\": \"1\",\n" +
//                "    \"title\": \"1\",\n" +
//                "    \"isbn\": \"1\",\n" +
//                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
//                "    \"borrower\": \"never borrowed\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"title\": \"2\",\n" +
//                "    \"author\": \"2\",\n" +
//                "    \"isbn\": \"2\",\n" +
//                "    \"lastBorrowedDate\": \"0000-01-01\",\n" +
//                "    \"borrower\": \"never borrowed\"\n" +
//                "  }\n" +
//                "]";
//        try {
//            FileWriter fileWriter = new FileWriter(pathTest);
//            fileWriter.write(fileContent);
//            fileWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String pathTestResultFile = "src/main/resources/testResultFile.json";
//        String borrower = "Przemek Stepski";
//        String lastBorrowDate = LocalDate.now().toString();
//        String fileContentResultFile = "[\n" +
//                "  {\n" +
//                "    \"author\": \"1\",\n" +
//                "    \"title\": \"1\",\n" +
//                "    \"isbn\": \"1\",\n" +
//                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
//                "    \"borrower\": \"" + borrower + "d\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"title\": \"2\",\n" +
//                "    \"author\": \"2\",\n" +
//                "    \"isbn\": \"2\",\n" +
//                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
//                "    \"borrower\": \"" + borrower + "d\"\n" +
//                "  }\n" +
//                "]";
//        try {
//            FileWriter fileWriter = new FileWriter(pathTestResultFile);
//            fileWriter.write(fileContentResultFile);
//            fileWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        InputStream inputStream = new ByteArrayInputStream(borrower.getBytes());
//        System.setIn(inputStream);
//
//        assertTrue(bookRepository.borrowBook(pathTest));
//
//        try {
//            bookRepository.borrowBook(pathTest);
//        } catch (MissingFileException e) {
//            e.printStackTrace();
//        }
//
//        String stringFromFile1 = new String(Files.readAllBytes(Paths.get(pathTest)));
//        String stringFromFile2 = new String(Files.readAllBytes(Paths.get(pathTestResultFile)));
//
//        JsonElement jsonElement1 = JsonParser.parseString(stringFromFile1);
//        JsonElement jsonElement2 = JsonParser.parseString(stringFromFile2);
//
//        assertEquals(jsonElement1, jsonElement2);
//
//        new File(pathTest).delete();
//        new File(pathTestResultFile).delete();
//    }

    @Test
    void testShowBorrowersShouldReturnListOfUniqueBorrowersAsSublistOfAllBorrowers() throws MissingFileException {
        List<String> testUniqueBorrowers = new ArrayList<>();
        testUniqueBorrowers.add("never borrowed");

        assertEquals(testUniqueBorrowers, bookRepository.showBorrowers(PATH_TO_TEST_FILE));
    }

    void addOneBookToTestBookList() {
        testBookList.add(book1);
    }

    void addTwoBooksToTestBookList() {
        testBookList.add(book1);
        testBookList.add(book2);
    }
}