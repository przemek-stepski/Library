package com.library;

import java.util.Objects;

public class Book implements Comparable<Book>{
    private final String title;
    private final String author;
    private final String isbn;
    private String lastBorrowedDate;
    private String borrower;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.isbn = ISBN;
        lastBorrowedDate = "0000-01-01";
        borrower = "never borrowed";
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLastBorrowedDate() {
        return lastBorrowedDate;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setLastBorrowedDate(String lastBorrowedDate) {
        this.lastBorrowedDate = lastBorrowedDate;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass() || o == null) return false;

        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", lastBorrowedDate='" + lastBorrowedDate + '\'' +
                ", borrower='" + borrower + '\'' +
                '}';
    }

    @Override
    public int compareTo(Book book) {
        return getIsbn().compareTo(book.isbn);
    }
}
