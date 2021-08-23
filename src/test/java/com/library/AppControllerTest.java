package com.library;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppControllerTest {

       private static final String PATH_TO_TEST_FILE = "src/main/resources/katalogTest2.json";

    @BeforeAll
    static void createTestFile() throws IOException {
        FileWriter createFile = new FileWriter(PATH_TO_TEST_FILE);
    }

    @AfterAll
    static void deleteTestFile() {
        File testFile = new File(PATH_TO_TEST_FILE);
        testFile.delete();
    }

    @Test
    void testFileIfExistShouldReturnTrueIfFileExist() {

        assertTrue(AppController.fileIfExist(PATH_TO_TEST_FILE));
    }

    @Test
    void testFileIfExistShouldReturnFalseIfFileNotExist() {
        String path = "src/main/resources/nieistniejcyPlik";

        assertFalse(AppController.fileIfExist(path));
    }

    @Test
    void testFileIfExistShouldReturnFalseIfPathNull() {

        assertFalse(AppController.fileIfExist(null));
    }
}