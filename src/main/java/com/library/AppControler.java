package com.library;

public class AppControler {
    Menu menu = new Menu();
    public static String pathToFile;

    protected void startApp(String path) {
        toMenu();
    }

    protected void toMenu() {
        menu.displayMenu();
        String menuChoice = menu.menuChoice();
        menu.executeCommand(menuChoice);

    }
}

