package com.library;

public class Main {
    public static void main(String[] args) {
        AppControler appControler = new AppControler();
        System.out.println("To start app please type path to books catalogue file: ");
        AppControler.pathToFile = UserInputScanner.scannerString();
        appControler.startApp(AppControler.pathToFile);
    }
}