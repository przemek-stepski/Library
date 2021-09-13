package com.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Printer {
private static final Logger LOGGER = LogManager.getLogger(Printer.class);

    public static void printBookFromList(List<Book> list) {
        for (Book b : list) {
            System.out.println("Title: " + b.getTitle());
            System.out.println("Author: " + b.getAuthor());
            System.out.println("Isbn: " + b.getIsbn());
            System.out.println("Borrower: " + b.getBorrower());
            System.out.println("Last borrow date: " + b.getLastBorrowedDate() + "\n");

            LOGGER.info("info from Printer Class printBookFromList()");
        }
    }
}
