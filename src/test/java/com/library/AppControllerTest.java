package com.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppControllerTest {

    @Test
    void testFileIfExistShouldReturnTrueIfFileExist() {
        String path = "src/main/resources/katalogTest1.json";

        assertTrue(AppController.fileIfExist(path));
    }

    @Test
    void testFileIfExistShouldReturnFalseIfFileNotExist() {
        String path = "src/main/resources/katalogTest2";

        assertFalse(AppController.fileIfExist(path));
    }

    @Test
    void testFileIfExistShouldReturnFalseIfPathNull() {

        assertFalse(AppController.fileIfExist(null));
    }
}