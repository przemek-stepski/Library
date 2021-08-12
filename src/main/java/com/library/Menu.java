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

    protected void executeCommand(String choice) {
        AppControler appControler = new AppControler();
        BookManager bookManager = new BookManager();

        switch (choice) {
            case "1":
                bookManager.addBook();
                appControler.toMenu();
                break;

            case "2":
                bookManager.deleteBook();
                appControler.toMenu();
                break;

            case "3":
                bookManager.findBookByTitle();
                appControler.toMenu();
                break;

            case "4":
                bookManager.findBookByAuthor();
                appControler.toMenu();
                break;

            case "5":
                bookManager.findBookByIsbn();
                appControler.toMenu();
                break;

//            case "6":
//                bookManager.findBooksNotBorrowed();
//                appControler.toMenu();
//                break;

            case "7":
                bookManager.borrowBook();
                appControler.toMenu();
                break;

//            case "8":
//                bookManager.findListOfBorrowers();
//                appControler.toMenu();
//                break;

            case "Q":
                System.out.println("You have successfully closed app. Thanks for using ;-)");
                break;

            default:
                appControler.toMenu();

        }
    }
}

