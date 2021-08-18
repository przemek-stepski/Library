package com.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    static List<Book> bookList;

//    public static List<Book> makeListFromJson() {
//        Gson gson = new Gson();
//
//        try (Reader reader = new FileReader(pathToFile)) {
//            Type bookListType = new TypeToken<ArrayList<Book>>() {
//            }.getType();
//            bookList = gson.fromJson(reader, bookListType);
//        } catch (IOException e) {
//            System.out.println("Reading Json file error occurred " + e.getMessage());
//        }
//        return bookList;
//    }

    public static List<Book> makeListFromJson(String pathToFile) {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(pathToFile)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            bookList = gson.fromJson(reader, bookListType);
        } catch (IOException e) {
            System.out.println("Reading Json file error occurred " + e.getMessage());
            return null;
        }
        return bookList;
    }

    public static boolean makeJsonFromList(ArrayList arrayList, String pathToFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(pathToFile)) {
            gson.toJson(arrayList, writer);
            writer.flush();
            return true;

        } catch (IOException e) {
            System.out.println("Saving Json file error occurred " + e.getMessage());
        }
        return false;
    }
}