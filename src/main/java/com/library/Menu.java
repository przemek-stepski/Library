package com.library;

public class Menu {

    protected void displayMenu() {
        System.out.println("********************************************************************");
        System.out.println("Choose an option and press ENTER");
        System.out.println("1: Add a book");
        System.out.println("2: Delete a book");
        System.out.println("3: Search for a book by title");
        System.out.println("4: Search for a book by author");
        System.out.println("5: Search for a book by ISBN number");
        System.out.println("6: Search for books that have not been borrowed for the last x weeks");
        System.out.println("7: Borrow a book");
        System.out.println("8: Display a list of borrowers of any book");
        System.out.println("Q: Quit app");
        System.out.println("********************************************************************");
    }

    protected String menuChoice() {
        UserInputScanner scanningClass = new UserInputScanner();
        return scanningClass.scannerString();
    }

    protected void executeCommand(String choice) throws MyException {
        BookRepository bookRepository = new BookRepository();
        String path = AppController.pathToFile;

        switch (choice) {
            case "1":
                bookRepository.addBook(path);
                break;

            case "2":
                bookRepository.deleteBook(path);
                break;

            case "3":
                bookRepository.findBookByTitle(path);
                break;

            case "4":
                bookRepository.findBookByAuthor(path);
                break;

            case "5":
                bookRepository.findBookByIsbn(path);
                break;

            case "6":
                bookRepository.findBooksNotBorrowed(path);
                break;

            case "7":
                bookRepository.borrowBook(path);
                break;

            case "8":
                bookRepository.showBorrowers(path);
                break;

            case "Q":
                System.out.println("You have successfully closed app. Thanks for using ;-)");
                break;

        }
        if (!choice.equals("Q")) {
            toMenu();
        }
    }

    protected void toMenu() {
        displayMenu();
        try {
            executeCommand(menuChoice());
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}

