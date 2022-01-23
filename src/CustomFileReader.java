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
        int count = 0;
        try(CSVReader csvReader = new CSVReader(new FileReader("data/" + file))) {
            lineValues = csvReader.readAll();
        }
        catch(IOException | CsvValidationException exception) {
            System.err.println("Error while reading from csv for authors: " + exception.getMessage());
        } catch (CsvException e) {
            e.printStackTrace();
        }

        for(String[] line : lineValues) {
            System.out.println(line[0] + " " + line[1] + " " + line[2]);
        }

        return authors;
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
