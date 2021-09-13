package com.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {

    private static final Logger LOGER = LogManager.getLogger(Menu.class);

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

    UserInputScanner userInputScanner = new UserInputScanner();
    Executor executor = new Executor();
    MenuOptions menuOptions;


    protected String menuChoice() {
        return userInputScanner.scannerString();
    }

    //todo Map z enumami i Stringami jako executor instance;
    protected void executeCommand(String choice) throws MissingFileException {
        BookRepository bookRepository = new BookRepository();
        String path = AppController.pathToFile;

        Map<String, Enum> choiceMap = new HashMap<>();
        choiceMap.put("1", menuOptions.ADD);
        choiceMap.put("2", menuOptions.DELETE);
        choiceMap.put("3", menuOptions.FIND_BY_TITLE);
        choiceMap.put("4", menuOptions.FIND_BY_AUTHOR);
        choiceMap.put("5", menuOptions.FIND_BY_ISBN);
        choiceMap.put("6", menuOptions.FIND_NOT_BORROWED_LAST_WEEKS);
        choiceMap.put("7", menuOptions.BORROW);
        choiceMap.put("8", menuOptions.DISPLAY_LIST_OF_BORROWERS);
        choiceMap.put("Q", menuOptions.QUIT);

        Map<String, Enum> choiceMapImmutable = Collections.unmodifiableMap(new LinkedHashMap<>(choiceMap));

        switch (menuOptions = (MenuOptions) choiceMapImmutable.get(choice)) {
            case ADD:
                bookRepository.addBook(path, executor.createBook());
                break;

            case DELETE:
                bookRepository.deleteBook(path);
                break;

            case FIND_BY_TITLE:
                bookRepository.findBookByTitle(path);
                break;

            case FIND_BY_AUTHOR:
                bookRepository.findBookByAuthor(path);
                break;

            case FIND_BY_ISBN:
                bookRepository.findBookByIsbn(path);
                break;

            case FIND_NOT_BORROWED_LAST_WEEKS:
                bookRepository.findBooksNotBorrowed(path);
                break;

            case BORROW:
                bookRepository.borrowBook(path);
                break;

            case DISPLAY_LIST_OF_BORROWERS:
                bookRepository.showBorrowers(path);
                break;

            case QUIT:
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
        } catch (MissingFileException e) {
            LOGER.info("inform from toMenu" + e);
            e.printStackTrace();
        }
    }

}

