package com.library;

import java.util.Scanner;

public class UserInputScanner {
    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scannerInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
