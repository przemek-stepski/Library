package com.library;


public class Executor {

    private final UserInputScanner userInputScanner;

    public Executor(UserInputScanner userInputScanner) {
        this.userInputScanner = userInputScanner;
    }

    public String getInput(InputOptions input) {

        switch (input) {
            case TITLE:
                System.out.println("Type a book title and press Enter");
                return userInputScanner.scannerString();
            case AUTHOR:
                System.out.println("Type a book author and press Enter");
                return userInputScanner.scannerString();
            case ISBN:
                System.out.println("Type a book ISBN and press Enter");
                return userInputScanner.scannerString();
            case BORROWER:
                System.out.println("Type borrower name and lastname and press Enter");
                return userInputScanner.scannerString();
        }
        return userInputScanner.scannerString();
    }

    public Book createBook() {
        String title = getInput(InputOptions.TITLE);
        String author = getInput(InputOptions.AUTHOR);
        String isbn = getInput(InputOptions.ISBN);
        return new Book(title, author, isbn);
    }
}
