import java.sql.*;
import java.util.List;
import java.util.Random;

public class DatabaseInserter {
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();
    private CustomFileReader customFileReader = new CustomFileReader();

    public int insertEmployees() {
        this.customFileReader.setFile("data/employee_forenames.txt");
        List<String> forenames = customFileReader.readFileEmployeeForenames();

        this.customFileReader.setFile("data/employee_surnames.txt");
        List<String> surnames = customFileReader.readFileEmployeeSurnames();

        int countAddedLines = 0;
        String sql = "Insert INTO mitarbeiter (ma_vorname, ma_nachname) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            Random randomIndex = new Random();

            for(int i = 0; i < 3000; ++i) {
                stmt.setString(1, forenames.get(randomIndex.nextInt(forenames.size())));
                stmt.setString(2, surnames.get(randomIndex.nextInt(surnames.size())));

                stmt.executeUpdate();
                ++countAddedLines;
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting Employees: " + exception.getMessage());
        }

        return countAddedLines;
    }


    public int testConnection() {
        String sql = "SELECT COUNT(*) FROM buch";
        int count = 0;

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            Statement stmt = con.createStatement()
        ) {
            try(ResultSet rs = stmt.executeQuery(sql)) {
                if(rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return count;
    }
}
