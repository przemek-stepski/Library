package com.library;

import java.io.*;

public class AppController {
    Menu menu = new Menu();
    static String pathToFile;

    protected void startApp() {
        System.out.println("To start app please type path to books catalogue file: ");
        pathToFile = UserInputScanner.scannerString();
        if (fileIfExist()) {
            toMenu();
        } else {
            System.out.println("You typed wrong path to book catalogue file.");
            startApp();
        }
    }

    protected static boolean fileIfExist() {
        File f = new File(pathToFile);
        return f.isFile();
    }

    protected void toMenu() {
        menu.displayMenu();
        menu.executeCommand(menu.menuChoice());
    }
}

