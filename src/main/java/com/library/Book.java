package com.library;

import java.time.LocalDate;

public class Book {
    private final String title;
    private final String author;
    private final int ISBN;
    private LocalDate lastBorrowedDate;
    private Person borrower;

    public Book(String title, String author, int ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }




}
