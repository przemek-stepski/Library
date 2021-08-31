package com.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;

public class BookRepository {

    protected Book createBook(String title, String author, String isbn) {
        return new Book(title, author, isbn);
    }

    protected void addBook(String path, Book bookToAdd) throws MissingFileException {
        List<Book> listToAddBook = BookDAO.makeListFromJson(path);
        listToAddBook.add(bookToAdd);

        BookDAO.makeJsonFromList((ArrayList) listToAddBook, path);
        //todo
        // logger że się dodała książka do tego pliku
        System.out.println("You have successfully added a book!");

    }

    protected boolean deleteBook(String path) throws MissingFileException {
        System.out.println("We will delete the book with typed ISBN if we have one");
        List<Book> listToDeleteBook = BookDAO.makeListFromJson(path);
        List<Book> booksToDelete = findBookByIsbn(path);
        for (Book b : listToDeleteBook) {
            if (booksToDelete.size() != 0) {
                if (b.equals(booksToDelete.get(0))) {
                    listToDeleteBook.remove(b);
                    BookDAO.makeJsonFromList((ArrayList) listToDeleteBook, path);
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

    protected List<Book> findBookByAuthor(String path) throws MissingFileException {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book author and press Enter");
        String author = UserInputScanner.scannerString();
        List<Book> listToFindBook = BookDAO.makeListFromJson(path);
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

    protected List<Book> findBookByTitle(String path) throws MissingFileException {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book title and press Enter");
        String title = UserInputScanner.scannerString();
        List<Book> listToFindBook = BookDAO.makeListFromJson(path);
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

    protected List<Book> findBookByIsbn(String path) throws MissingFileException {
        List<Book> listOfFoundBooks = new ArrayList<>();

        System.out.println("Type a book ISBN and press Enter");
        String isbn = UserInputScanner.scannerString();
        List<Book> listToFindBook = BookDAO.makeListFromJson(path);
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

    protected List<Book> findBooksNotBorrowed(String path) throws MissingFileException {
        List<Book> listOfFoundBooks = new ArrayList<>();
        System.out.println("Type number of weeks (to find books not borrowed during that period)");

        int numberOfWeeksGiven = UserInputScanner.scannerInt();
        int numberOfDaysGiven = numberOfWeeksGiven * 7;

        List<Book> listToFindBook = BookDAO.makeListFromJson(path);
        for (Book b : listToFindBook) {
            LocalDate borrowDate = LocalDate.parse(b.getLastBorrowedDate());
            long periodInDays = DAYS.between(borrowDate, now());
            if (periodInDays >= numberOfDaysGiven) {
                listOfFoundBooks.add(b);
            }
        }
        if (listOfFoundBooks.size() > 0) {
            System.out.println("We have such a books not borrowed within last " + numberOfWeeksGiven + " weeks");
            System.out.println(listOfFoundBooks);
        } else {
            System.out.println("We do not have books not borrowed within last " + numberOfWeeksGiven + " weeks");
        }
        return listOfFoundBooks;
    }


    protected boolean borrowBook(String path) throws MissingFileException {
        String borrower = UserInputScanner.getInput("borrower");

        List<Book> booksList = BookDAO.makeListFromJson(path);

        List<Book> booksToBorrow = findBookByIsbn(path);
        if (booksToBorrow.size() > 0) {
            for (Book b : booksList) {
                if (booksToBorrow.get(0).equals(b)) {
                    b.setBorrower(borrower);
                    b.setLastBorrowedDate(now().toString());
                    BookDAO.makeJsonFromList((ArrayList) booksList, path);
                    System.out.println("You have successfully borrowed a book: " + booksToBorrow.get(0));
                    return true;
                }
            }
        }
        return false;
    }

    protected List<String> showBorrowers(String path) throws MissingFileException {
        List<Book> booksList = BookDAO.makeListFromJson(path);
        List<String> allBorrowers = new ArrayList<>();

        for (Book b : booksList) {
            allBorrowers.add(b.getBorrower());
        }
        List<String> uniqueBorrowers = allBorrowers.stream()
                .distinct()
                .collect(Collectors.toList());

        for (String borrower : uniqueBorrowers) {
            int bookNumber = Collections.frequency(allBorrowers, borrower);
            if (bookNumber == 1) {
                System.out.println("Borrower " + borrower + " borrowed " + bookNumber + " book");
            } else {
                System.out.println("Borrower " + borrower + " borrowed " + bookNumber + " books");
            }
        }
        return uniqueBorrowers;
    }
}
