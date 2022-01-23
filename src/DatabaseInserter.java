import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseInserter {
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();
    private CustomFileReader customFileReader = new CustomFileReader();
    private DataManager dataManager = new DataManager();


    /**
     * <h1>insertEmployees</h1>
     * <p>
     * Inserts employees to the Oracle DB Table "Mitarbeiter". Number of Employees are defined by the user.<br>
     * Before the insert, it creates a list of employees by using the DataManagers' method "createEmployees"
     * </p>
     *
     * @param numberOfEmployees Integer
     * @return Void
     */
    public void insertEmployees(int numberOfEmployees) {

        String sql = "Insert INTO mitarbeiter (ma_vorname, ma_nachname) VALUES (?, ?)";
        List<Employee> employees = dataManager.createEmployees(numberOfEmployees);

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(int i = 0; i < employees.size(); ++i) {
                stmt.setString(1, employees.get(i).getForename());
                stmt.setString(2, employees.get(i).getSurname());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting Employees: " + exception.getMessage());
        }

        return;
    }


    public void insertInternalEmployees(int numberOfInternalEmployees) {
        Random random = new Random();

        String sql = "INSERT INTO intern (sv_nr, gehalt, ma_id) VALUES (?, ?, ?)";

        List<Employee> employees = dataManager.getEmployeesWithID();

        List<Employee> permanentEmployees = new ArrayList<>();
        List<Employee> externalEmployees = new ArrayList<>();

        List<String> socialSecurityNumbers = dataManager.createSocialSecurityNumbers(numberOfInternalEmployees);
        List<Double> wages = dataManager.createWages(numberOfInternalEmployees);


        for(int i = 0; i < numberOfInternalEmployees; ++i) {
            permanentEmployees.add(employees.get(i));
        }

        for(int i = numberOfInternalEmployees; i < employees.size(); ++i) {
            externalEmployees.add(employees.get(i));
        }

        int chefID = permanentEmployees.get(random.nextInt(permanentEmployees.size())).getEmployeeID();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(int i = 0; i < permanentEmployees.size(); ++i) {
                stmt.setString(1, socialSecurityNumbers.get(i));
                stmt.setDouble(2, wages.get(i));
                stmt.setInt(3, permanentEmployees.get(i).getEmployeeID());
                //stmt.setInt(4, chefID);

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting full employees: " + exception.getMessage());
        }

        this.updateChefID(permanentEmployees, chefID);
        this.insertExternalEmployees(externalEmployees);

        return;
    }

    private void updateChefID(List<Employee> permanentEmployees, int chefID) {

        String sql = "UPDATE intern SET chef_id = ? WHERE ma_id = ?";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(final Employee emp : permanentEmployees) {
                stmt.setInt(1, chefID);
                stmt.setInt(2, emp.getEmployeeID());
                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while updating chef id: " + exception.getMessage());
        }
    }

    private void insertExternalEmployees(List<Employee> externalEmployees) {

        String sql = "INSERT INTO extern (ext_email, ext_tel, taetigkeit, ma_id) VALUES (?, ?, ?, ?)";

        List<String> emails = dataManager.createEmail(externalEmployees.size(), externalEmployees);
        List<String> telNumbers = dataManager.createTelNumbers(externalEmployees.size());
        List<String> activities = dataManager.createActivity(externalEmployees.size());

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(int i = 0; i < externalEmployees.size(); ++i) {
                stmt.setString(1, emails.get(i));
                stmt.setString(2, telNumbers.get(i));
                stmt.setString(3, activities.get(i));
                stmt.setInt(4, externalEmployees.get(i).getEmployeeID());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting external employees: " + exception.getMessage());
        }

        return;
    }

    public List<Author> insertAuthors() {
        return dataManager.createAuthors();
    }
}
