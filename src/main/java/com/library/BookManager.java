package com.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class BookManager {
    protected Book createBook() {
        System.out.println("Type a book title and press Enter");
        String title = UserInputScanner.scannerString();

        System.out.println("Type a book author and press Enter");
        String author = UserInputScanner.scannerString();

        System.out.println("Type ISBN and press Enter");
        String ISBN = UserInputScanner.scannerString();

        Book newBook = new Book(title, author, ISBN);

        return newBook;
    }

    protected void addBook() {
        List<Book> listToAddBook = DataHandler.makeListFromJson();
        listToAddBook.add(createBook());

        DataHandler.makeJsonFromList((ArrayList) listToAddBook);
        System.out.println("You have successfully added a book!");
    }

    protected boolean deleteBook() {
        System.out.println("We will delete the book with typed ISBN if we have one");
        List<Book> listToDeleteBook = DataHandler.makeListFromJson();
        List<Book> booksToDelete = findBookByIsbn();
        for (Book b : listToDeleteBook) {
            if (booksToDelete.size() != 0) {
                if (b.equals(booksToDelete.get(0))) {
                    listToDeleteBook.remove(b);
                    DataHandler.makeJsonFromList((ArrayList) listToDeleteBook);
                    System.out.println("We have deleted: " + booksToDelete.get(0).toString());
                    return true;
                }

            } else {
                System.out.println("There is no book to delete");
                return false;
            }
        }
        return false;
    }

    protected List<Book> findBookByAuthor() {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book author and press Enter");
        String author = UserInputScanner.scannerString();
        List<Book> listToFindBook = DataHandler.makeListFromJson();
        for (Book b : listToFindBook)
            if (b.getAuthor().equals(author)) {
                listOfFoundBooks.add(b);
            }
        if (listOfFoundBooks.size() > 0) {
            System.out.println("We have such a books from author " + author);
            System.out.println(listOfFoundBooks);
        } else {
            System.out.println("We do not have books by this author in our collection");
        }
        return listOfFoundBooks;
    }

    protected List<Book> findBookByTitle() {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book title and press Enter");
        String title = UserInputScanner.scannerString();
        List<Book> listToFindBook = DataHandler.makeListFromJson();
        for (Book b : listToFindBook)
            if (b.getTitle().equals(title)) {
                listOfFoundBooks.add(b);
            }
        if (listOfFoundBooks.size() > 0) {
            System.out.println("We have such a books with title \"" + title + "\"");
            System.out.println(listOfFoundBooks);
        } else {
            System.out.println("We do not have books with this title in our collection");
        }
        return listOfFoundBooks;
    }

    protected List<Book> findBookByIsbn() {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book ISBN and press Enter");
        String isbn = UserInputScanner.scannerString();
        List<Book> listToFindBook = DataHandler.makeListFromJson();
        for (Book b : listToFindBook)
            if ((b.getIsbn().replaceAll("[- ]", "")).equals(isbn.replaceAll("[- ]", ""))) {
                listOfFoundBooks.add(b);
            }
        if (listOfFoundBooks.size() > 0) {
            System.out.println("We have such a books with ISBN \"" + isbn + "\"");
            System.out.println(listOfFoundBooks);
        } else {
            System.out.println("We do not have books with this ISBN in our collection");
        }
        return listOfFoundBooks;
    }

    protected boolean borrowBook() {
        System.out.println("Type borrower name and lastname");
        String borrower = UserInputScanner.scannerString();

        List<Book> booksList = DataHandler.makeListFromJson();

        List<Book> booksToBorrow = findBookByIsbn();
        if (booksToBorrow.size() > 0) {
            for (Book b : booksList) {
                if (booksToBorrow.get(0).equals(b)) {
                    b.setBorrower(borrower);
                    b.setLastBorrowedDate(now().toString());
                    DataHandler.makeJsonFromList((ArrayList) booksList);
                    System.out.println("You have successfully borrowed a book: " + booksToBorrow.get(0));
                    return true;
                }
            }
        }
        return false;
    }

    protected void showBorrowers() {
        List<Book> booksList = DataHandler.makeListFromJson();
        List<String> allBorrowers = new ArrayList<>();

        for (Book b: booksList) {
            allBorrowers.add(b.getBorrower());
        }
        List<String> uniqueBorrowers = allBorrowers.stream()
                .distinct()
                .collect(Collectors.toList());

        for (String borrower: uniqueBorrowers) {
            int bookNumber = Collections.frequency(allBorrowers, borrower);
        System.out.println("Borrower " + borrower + " has " + bookNumber + " books borrowed");
        }
    }
}
