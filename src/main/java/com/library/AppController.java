package com.library;

import java.io.*;

public class AppController {
    Menu menu = new Menu();
    static String pathToFile;

    protected void startApp() {
        System.out.println("To start app please type path to books catalogue file: ");
        pathToFile = UserInputScanner.scannerString();
        if (fileIfExist(pathToFile)) {
            menu.toMenu();

        } else {
            System.out.println("You typed wrong path to books catalogue file.");
            startApp();
        }
    }

    protected static boolean fileIfExist(String path) {
        try {
            File f = new File(path);
            return f.isFile();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
