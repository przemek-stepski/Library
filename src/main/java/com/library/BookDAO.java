package com.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookDAO.class);

    static List<Book> bookList;

    public static List<Book> makeListFromJson(String pathToFile) throws MissingFileException, NullPointerException {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(pathToFile)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            return bookList = gson.fromJson(reader, bookListType);

        } catch (IOException e) {
            System.out.println("Reading Json file error occurred: " + e.getMessage());
            throw new MissingFileException();
        } catch (NullPointerException e) {
            System.out.println("Path to file should not be null: " + e.getMessage());
            throw new MissingFileException();
        } catch (JsonSyntaxException e) {
            System.out.println("File should be json format and not empty: " + e.getMessage());
            LOGGER.info(" makeListFromJson()" + e);
            throw new MissingFileException();
        }
    }

        public static boolean makeJsonFromList (ArrayList arrayList, String pathToFile){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (AppController.fileIfExist(pathToFile)) {
                try (FileWriter writer = new FileWriter(pathToFile)) {
                    gson.toJson(arrayList, writer);
                    writer.flush();
                    return true;
                } catch (IOException e) {
                    System.out.println("Saving Json file error occurred " + e.getMessage());

                } catch (NullPointerException e) {
                    System.out.println("Path to file should not be null" + e.getMessage());
                }
                return false;
            }
            return false;
        }
    }
