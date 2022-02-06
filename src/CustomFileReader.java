import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFileReader {
    public List<String> readFile(String file) {
        List<String> elements = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("data/" + file))) {
            String line = "";

            while((line = bufferedReader.readLine()) != null) {
                elements.add(line);
            }
        }
        catch(IOException exception) {
            System.err.println("Error while reading from file: " + exception.getMessage());
        }

        return elements;
    }

    public List<Author> readFileCSVAuthors(String file) {
        List<Author> authors = new ArrayList<>();
        List<String[]> lineValues = new ArrayList<>();

        try(CSVReader csvReader = new CSVReader(new FileReader("data/" + file))) {
            lineValues = csvReader.readAll();
        }
        catch(IOException | CsvValidationException exception) {
            System.err.println("Error while reading from csv for authors: " + exception.getMessage());
        }
        catch (CsvException e) {
            e.printStackTrace();
        }

        for(String[] line : lineValues) {
            authors.add(new Author(line[0], line[1], line[2]));
        }

        return authors;
    }

    public List<Book> readFileCSVBooks(String file) {
        List<Book> books = new ArrayList<>();
        List<String[]> lineValues = new ArrayList<>();

        try(CSVReader csvReader = new CSVReader(new FileReader("data/" + file))) {
            lineValues = csvReader.readAll();
        }
        catch(IOException | CsvValidationException exception) {
            System.err.println("Error while reading from csv for books: " + exception.getMessage());
        }
        catch(CsvException e) {
            e.printStackTrace();
        }

        for(String[] line : lineValues) {
            books.add(new Book(line[0], line[1], line[2], Integer.parseInt(line[3]), line[4], Integer.parseInt(line[5]), Double.parseDouble(line[6]), line[7], Integer.parseInt(line[8])));
        }

        return books;
    }

    public List<String[]> readFilesCSV(String file) {
        List<String[]> lineValues = new ArrayList<>();

        try(CSVReader csvReader = new CSVReader(new FileReader("data/" + file))) {
            lineValues = csvReader.readAll();
        }
        catch(IOException | CsvValidationException exception) {
            System.err.println("Error while reading from csv: " + exception.getMessage());
        }
        catch(CsvException e) {
            e.printStackTrace();
        }

        return lineValues;
    }

    public List<BookStore> readFileCSVBookStores(String file) {
        List<BookStore> bookStores = new ArrayList<>();
        List<String[]> lineValues = new ArrayList<>();

        try(CSVReader csvReader = new CSVReader(new FileReader("data/" + file))) {
            lineValues = csvReader.readAll();
        }
        catch(IOException | CsvValidationException exception) {
            System.err.println("Error while reading from csv for books to authors: " + exception.getMessage());
        }
        catch(CsvException e) {
            e.printStackTrace();
        }

        for(String[] lineValue : lineValues) {
            bookStores.add(new BookStore(lineValue[0], lineValue[1], lineValue[2], lineValue[3]));
        }

        return bookStores;
    }

    /*
    public List<String> readFileEmployeeSurnames() {
        List<String> surnames = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line = "";

            while((line= bufferedReader.readLine()) != null) {
                surnames.add(line);
            }
        }
        catch(IOException exception) {
            System.err.println("Error while reading from file for surnames: " + exception.getMessage());
        }

        this.emptyFile();

        return surnames;
    }
    */
}
