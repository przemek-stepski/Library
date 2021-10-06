package com.library;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {

    private UserInputScanner userInputScanner;

    void setupInputForTest(String userInput) {
        InputStream input = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(input);
        userInputScanner = new UserInputScanner();
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
}