import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInserter {
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();

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
