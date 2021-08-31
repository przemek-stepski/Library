package com.library;

import java.sql.SQLOutput;
import java.util.Scanner;

public class UserInputScanner {
    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Integer scannerInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    //todo move getInput to Executor class

    public static String getInput(String input) {
        switch (input) {
            case "title":
                System.out.println("Type a book title and press Enter");
                scannerString();
                break;
            case "author":
                System.out.println("Type a book author and press Enter");
                scannerString();
                break;
            case "isbn":
                System.out.println("Type a book ISBN and press Enter");
                scannerString();
                break;
            case "borrower":
                System.out.println("Type borrower name and lastname and press Enter");
                scannerString();
                break;
        }
        return scannerString();
    }


}
