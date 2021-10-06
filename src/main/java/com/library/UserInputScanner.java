package com.library;

import java.util.Scanner;

public class UserInputScanner {

    Scanner scanner = new Scanner(System.in);

    public String scannerString() {
        return scanner.nextLine();
    }

    public Integer scannerInt() {
        return scanner.nextInt();
    }
}
