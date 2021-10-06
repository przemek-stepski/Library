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

    private Book book1 = new Book("1", "1", "1");
    private Book book2 = new Book("2", "2", "2");
    private List<Book> testBookList = new ArrayList<>();

    private UserInputScanner userInputScanner;

    private BookRepository bookRepository;
    private static final String PATH_TO_TEST_FILE = "src/main/resources/katalogTest.json";

    void setupInputForTest(String userInput) {
        InputStream input = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(input);
        userInputScanner = new UserInputScanner();
        bookRepository = new BookRepository(userInputScanner);
    }

    @BeforeEach
    void setUp() {
        userInputScanner = new UserInputScanner();
        bookRepository = new BookRepository(userInputScanner);
    }

    @BeforeEach
    void createTestFile() {

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

    @Test
    void testCreateBookShouldReturnNewBook() {
        Book testBook = new Book("Java", "Josh Bloch", "1234");

        String testInputTitle = "Java";
        String testInputAuthor = "Josh Bloch";
        String testInputIsbn = "1234";
        setupInputForTest(testInputTitle + "\n" + testInputAuthor + "\n" + testInputIsbn);

        Executor executor = new Executor(userInputScanner);

        assertEquals(testBook, executor.createBook());
    }

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

        new File(pathTest).delete();
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
        setupInputForTest(testInputIsbn);

        assertTrue(bookRepository.deleteBook(PATH_TO_TEST_FILE));
    }

    @Test
    void testDeleteBookShouldReturnFalseIfBookNotInList() throws MissingFileException {
        String testInputIsbn = "7777777";
        setupInputForTest(testInputIsbn);

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
        setupInputForTest(testInputIsbn);

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
        setupInputForTest(testInputTitle);
        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void testFindBookByAuthorShouldReturnEmptyListIfAuthorNotInCatalogue() throws MissingFileException {
        String testInputTitle = "wrongAuthor";
        setupInputForTest(testInputTitle);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void testFindBookByTitleShouldReturnListOfFoundBooks() throws MissingFileException {
        addOneBookToTestBookList();

        String testInputAuthor = "1";
        setupInputForTest(testInputAuthor);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList);
    }

    @Test
    void testFindBookByTitleShouldReturnEmptyListIfTitleNotInCatalogue() throws MissingFileException {
        String testInputAuthor = "wrongTitle";
        setupInputForTest(testInputAuthor);

        List testedMethodList = bookRepository.findBookByAuthor(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void testFindBookByIsbnShouldReturnListOfBooks() throws MissingFileException {
        addOneBookToTestBookList();

        String testInputIsbn = "1";
        setupInputForTest(testInputIsbn);

        List testedMethodList = bookRepository.findBookByIsbn(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void testFindBookByIsbnShouldReturnEmptyLstIfIsbnNotInCatalogue() throws MissingFileException {
        String testInputIsbn = "wrongIsbn";
        setupInputForTest(testInputIsbn);

        List testedMethodList = bookRepository.findBookByIsbn(PATH_TO_TEST_FILE);

        assertTrue(testedMethodList.isEmpty());
    }

    @Test
    void testFindBooksNotBorrowedShouldReturnListOfBooksNeverBorrowed() throws MissingFileException {
        addTwoBooksToTestBookList();

        String testInputWeeksNumber = "1";
        setupInputForTest(testInputWeeksNumber);

        List testedMethodList = bookRepository.findBooksNotBorrowed(PATH_TO_TEST_FILE);

        assertEquals(testBookList.stream().sorted().collect(Collectors.toList()), testedMethodList.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    void testFindBooksNotBorrowedShouldReturnListOfNotBorrowedBooksInLastWeek() throws MissingFileException {
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

        String testInputWeeksNumber = "1";
        setupInputForTest(testInputWeeksNumber);

        assertEquals(testBookList, bookRepository.findBooksNotBorrowed(pathTest));

        new File(pathTest).delete();
    }

    @Test
    void testFindBooksNotBorrowedShouldReturnEmptyListIfAllBooksWasBorrowedInLastWeek() throws MissingFileException {
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


        String testInputWeeksNumber = "1";
        setupInputForTest(testInputWeeksNumber);

        assertTrue(bookRepository.findBooksNotBorrowed(pathTest).isEmpty());

        new File(pathTest).delete();
    }

    @Test
    void testBorrowBookShouldReturnTruIfBookSuccessfullyBorrowed() throws MissingFileException {
        String pathTest = "src/main/resources/test.json";
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
            FileWriter fileWriter = new FileWriter(pathTest);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pathTestResultFile = "src/main/resources/testResultFile.json";
        String borrower = "Przemek Stepski";
        String isbn = "1";
        String lastBorrowDate = LocalDate.now().toString();
        String fileContentResultFile = "[\n" +
                "  {\n" +
                "    \"author\": \"1\",\n" +
                "    \"title\": \"1\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"" + borrower + "d\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"2\",\n" +
                "    \"author\": \"2\",\n" +
                "    \"isbn\": \"2\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"" + borrower + "d\"\n" +
                "  }\n" +
                "]";
        try {
            FileWriter fileWriter = new FileWriter(pathTestResultFile);
            fileWriter.write(fileContentResultFile);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userTestInput = borrower + "\n" + isbn;
        setupInputForTest(userTestInput);

        assertTrue(bookRepository.borrowBook(pathTest));

        new File(pathTest).delete();
        new File(pathTestResultFile).delete();
    }

    @Test
    void testBorrowBookShouldReturnJsonWithBorrowedBook() throws IOException {
        String pathTest = "src/main/resources/test.json";
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
            FileWriter fileWriter = new FileWriter(pathTest);
            fileWriter.write(fileContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pathTestResultFile = "src/main/resources/testResultFile.json";
        String borrower = "Przemek Stepski";
        String isbn = "1";
        String lastBorrowDate = LocalDate.now().toString();
        String fileContentResultFile = "[\n" +
                "  {\n" +
                "    \"author\": \"1\",\n" +
                "    \"title\": \"1\",\n" +
                "    \"isbn\": \"1\",\n" +
                "    \"lastBorrowedDate\": \"" + lastBorrowDate + "\",\n" +
                "    \"borrower\": \"" + borrower + "\"\n" +
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
            FileWriter fileWriter = new FileWriter(pathTestResultFile);
            fileWriter.write(fileContentResultFile);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setupInputForTest(borrower + "\n" + isbn);
            bookRepository.borrowBook(pathTest);
        } catch (MissingFileException e) {
            e.printStackTrace();
        }

        String stringFromFile1 = new String(Files.readAllBytes(Paths.get(pathTest)));
        String stringFromFile2 = new String(Files.readAllBytes(Paths.get(pathTestResultFile)));

        JsonElement jsonElement1 = JsonParser.parseString(stringFromFile1);
        JsonElement jsonElement2 = JsonParser.parseString(stringFromFile2);

        assertEquals(jsonElement1, jsonElement2);

        new File(pathTest).delete();
        new File(pathTestResultFile).delete();
    }

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