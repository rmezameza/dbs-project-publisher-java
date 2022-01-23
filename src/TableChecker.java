import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TableChecker {

    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();


    /**
     * <h1>checkNumberOfInserts</h1>
     *
     * @param tableName String
     * @return Integer
     */
    public int checkNumberOfInserts(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        int count = 0;

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            try(ResultSet rs = stmt.executeQuery()) {
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
