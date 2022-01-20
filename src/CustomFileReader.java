import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReader {
    private String file;


    public void setFile(String file) {
        this.file = file;
    }

    public List<String> readFileEmployeeForenames() {
        List<String> forenames = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line = "";

            while((line = bufferedReader.readLine()) != null) {
                forenames.add(line);
            }
        }
        catch(IOException exception) {
            System.err.println("Error while reading from file for forenames: " + exception.getMessage());
        }

        return forenames;
    }

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

        return surnames;
    }
}
