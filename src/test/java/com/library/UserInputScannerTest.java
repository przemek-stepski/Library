package com.library;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserInputScannerTest {

    @Test
    void testScannerStringShouldReturnTestInputString() {
        String testInputString = "Java";
        InputStream input = new ByteArrayInputStream(testInputString.getBytes());
        System.setIn(input);

        Assertions.assertEquals("Java", UserInputScanner.scannerString());
    }

    @Test
    void testScannerStringShouldReturnNull() {
        String testInputTitle = null;
        InputStream input = new ByteArrayInputStream(testInputTitle.getBytes());
        System.setIn(input);

        Assertions.assertNull(UserInputScanner.scannerString());
    }


    @Test
    void testScannerIntShouldReturnTestInputInt() {
        int testInputInt = 12;
        InputStream input = new ByteArrayInputStream(String.valueOf(testInputInt).getBytes());
        System.setIn(input);

        Assertions.assertEquals("12", UserInputScanner.scannerString());
    }

    @Test
    void testScannerIntShouldReturnNull() {
        Object o = null;
        //int testInputInt = (int) o;
        InputStream input = new ByteArrayInputStream(String.valueOf(o).getBytes());
        System.setIn(input);

        Assertions.assertEquals(null, UserInputScanner.scannerString());
    }
}
