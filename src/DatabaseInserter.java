import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseInserter {
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();
    private final DataManager dataManager = new DataManager();


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

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting full employees: " + exception.getMessage());
        }

        this.updateChefID(permanentEmployees, chefID);
        this.insertExternalEmployees(externalEmployees);
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

        List<String> emails = dataManager.createEmail(externalEmployees);
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
    }

    public void insertAuthors() {

        String sql = "INSERT INTO autor (au_vorname, au_nachname, bio) VALUES (?, ?, ?)";

        List<Author> authors = dataManager.createAuthors();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(Author author : authors) {
                stmt.setString(1, author.getForename());
                stmt.setString(2, author.getSurname());
                stmt.setString(3, author.getBio());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting authors: " + exception.getMessage());
        }
    }

    public void insertBooks() {

        String sql = "INSERT INTO buch (isbn, titel, kurz_beschr, seiten_anz, cover, ersch_jahr, preis, genre, neu_ersch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Book> books = dataManager.createBooks();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(Book book : books) {
                stmt.setString(1, book.getIsbn().replace("-", ""));
                stmt.setString(2, book.getTitle());
                stmt.setString(3, book.getDescription());
                stmt.setInt(4, book.getPageNumber());
                stmt.setString(5, book.getCover());
                stmt.setInt(6, book.getReleaseYear());
                stmt.setDouble(7, book.getPrice());
                stmt.setString(8, book.getGenre());
                stmt.setInt(9, book.getNoveltyStatus());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books: " + exception.getMessage());
        }
    }

    public void insertBooksToAuthors() {

        String sql = "INSERT INTO schreibt (autor_id, buch_id) VALUES (?, ?)";

        List<String[]> lineValues = dataManager.createListBooksToAuthors();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(String[] lv : lineValues) {
                stmt.setInt(1, Integer.parseInt(lv[1]));
                stmt.setInt(2, Integer.parseInt(lv[0]));

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books: " + exception.getMessage());
        }
    }

    public void insertBookstores() {
        String sql = "INSERT INTO buchlager (lag_strasse, lag_plz, lag_ort, lag_land) VALUES (?, ?, ?, ?)";

        List<BookStore> bookStores = dataManager.createBookStores();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(BookStore bookStore : bookStores) {
                stmt.setString(1, bookStore.getStreet());
                stmt.setString(2, bookStore.getZip());
                stmt.setString(3, bookStore.getPlace());
                stmt.setString(4,bookStore.getCountry());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books: " + exception.getMessage());
        }
    }

    public void insertRacks() {
        String sql = "INSERT INTO regal (regal_nr, regal_name, regal_kapazitaet, lag_id) VALUES (?, ?, ?, ?)";
        List<Rack> racks = dataManager.createRacks();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(Rack rack : racks) {
                stmt.setInt(1, rack.getRackID());
                stmt.setString(2, rack.getRackGenreName());
                stmt.setInt(3, rack.getCapacity());
                stmt.setInt(4, rack.getBookStoreID());

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books: " + exception.getMessage());
        }
    }

    public void insertBooksInRacks() {
        String sql = "INSERT INTO gelagert (buch_id, regal_nr, lag_id, stueck) VALUES (?, ?, ?, ?)";
        List<Integer[]> storedBooks = dataManager.fillRacks();

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(Integer[] storedBook : storedBooks) {
                stmt.setInt(1, storedBook[0]);
                stmt.setInt(2, storedBook[1]);
                stmt.setInt(3, storedBook[2]);
                stmt.setInt(4, storedBook[3]);

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books: " + exception.getMessage());
        }
    }

    public void insertBookReviewers() {
        List<String[]> reviewers = this.dataManager.getBookReviewer();

        String sql = "INSERT INTO buchkritiker (kr_vorname, kr_nachname) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(String[] reviewer : reviewers) {
                stmt.setString(1, reviewer[0]);
                stmt.setString(2, reviewer[1]);
                stmt.executeUpdate();

            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books reviewers: " + exception.getMessage());
        }
    }

    public void insertBookReviews() {
        List<String[]> reviews = this.dataManager.getBookReviews();

        String sql = "INSERT INTO rezension (inhalt, quelle, datum) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'))";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(String[] review : reviews) {
                stmt.setString(1, review[0]);
                stmt.setString(2, review[1]);
                stmt.setString(3, review[2]);
                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books reviewers: " + exception.getMessage());
        }
    }

    public void insertTernaryRelation() {
        List<String[]> data = this.dataManager.getRelationReviewerReviews();
        Random random = new Random();

        String sql = "INSERT INTO rezensieren (kritiker_id, rez_id, buch_id) VALUES (?, ?, ?)";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            for(String[] element : data) {
                int bookID = random.nextInt(156 - 1) + 1;
                stmt.setInt(1, Integer.parseInt(element[0]));
                stmt.setInt(2, Integer.parseInt(element[1]));
                stmt.setInt(3, bookID);

                stmt.executeUpdate();
            }
        }
        catch(Exception exception) {
            System.err.println("Error while inserting books reviewers: " + exception.getMessage());
        }
    }
}
