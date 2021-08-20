package com.library;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputScannerTest {

    @Test
    void testScannerStringShouldReturnTestInputString() {
        String testInputString = "Java";
        InputStream input = new ByteArrayInputStream(testInputString.getBytes());
        System.setIn(input);

        assertEquals("Java", UserInputScanner.scannerString());
    }

    @Test
    void testScannerIntShouldReturnTestInputInt() {
        int testInputInt = 12;
        InputStream input = new ByteArrayInputStream(String.valueOf(testInputInt).getBytes());
        System.setIn(input);

        assertEquals("12", UserInputScanner.scannerString());
    }

}
