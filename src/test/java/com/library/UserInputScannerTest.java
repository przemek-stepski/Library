package com.library;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputScannerTest {
        private UserInputScanner userInputScanner;

    void setUpTestForInput(String userInput) {
        InputStream input = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(input);
        userInputScanner = new UserInputScanner();
    }

    @Test
    void testScannerStringShouldReturnTestInputString() {
        String testInputString = "Java";
        setUpTestForInput(testInputString);

        assertEquals("Java", userInputScanner.scannerString());
    }

    @Test
    void testScannerIntShouldReturnTestInputInt() {
        String testInputInt = "12";
        setUpTestForInput(testInputInt);

        assertEquals(12, userInputScanner.scannerInt());
    }

}
