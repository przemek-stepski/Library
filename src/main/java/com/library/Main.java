package com.library;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AppControler appControler = new AppControler();
        System.out.println("To start app please type path to books catalogue file: ");
        AppControler.pathToFile = UserInputScanner.scannerString();
        appControler.startApp(AppControler.pathToFile);


//        DataHandler.makeListFromJson(appControler.pat);
//        ArrayList booksList = (ArrayList) DataHandler.bookList;
//
//        Book book1 = new Book("Potop", "Sienkiewicz", "12");
//        booksList.add(book1);
//        System.out.println(booksList);
//        DataHandler.makeJsonFromList(booksList);
//        System.out.println(booksList);
    }
}