import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataManager {
    private final CustomFileReader customFileReader = new CustomFileReader();
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();


    public List<Employee> createEmployees(int numberOfEmployees) {
        List<Employee> employees = new ArrayList<>();
        Random randomIndex = new Random();

        List<String> forenames = customFileReader.readFile("employee_forenames.txt");
        List<String> surnames = customFileReader.readFile("employee_surnames.txt");

        for(int i = 0; i < numberOfEmployees; ++i) {
            String forename = forenames.get(randomIndex.nextInt(forenames.size()));
            String surname = surnames.get(randomIndex.nextInt(surnames.size()));

            employees.add(new Employee(forename, surname));
        }

        return employees;
    }

    public List<Employee> getEmployeesWithID() {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM mitarbeiter";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
        }
        catch(Exception exception) {
            System.err.println("Fehler bei Versuch die Mitarbeiter ID zu bekommen: " + exception.getMessage());
        }

        return employees;
    }

    public List<String> createSocialSecurityNumbers(int quantity) {
        Random random = new Random();
        List<String> socialSecurityNumbers = new ArrayList<>();
        StringBuffer socialSecurityNumber = new StringBuffer();

        List<String> years = this.customFileReader.readFile("years.csv");
        List<String> months = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));

        for(int i = 0; i < quantity; ++i) {

            int proofNumber = random.nextInt(9999 - 1000) + 1000;
            socialSecurityNumber.append(proofNumber);
            String month = months.get(random.nextInt(months.size()));

            int day;

            if(month.equals("02")) {
                day = random.nextInt((28 - 1) + 1);
            }
            else {
                day = random.nextInt((30 - 1) + 1);
            }
            if(day < 10) {
                socialSecurityNumber.append("0");
                socialSecurityNumber.append(day);
            }
            else {
                socialSecurityNumber.append(day);
            }

            socialSecurityNumber.append(month);

            String year = years.get(random.nextInt(years.size()));
            year = year.substring(2);
            year = (year.equals("950")) ? year.substring(1) : year;

            socialSecurityNumber.append(year);

            if(socialSecurityNumbers.contains(socialSecurityNumber.toString())) {
                ++quantity;
                socialSecurityNumber.delete(0, socialSecurityNumber.length());
                continue;
            }

            socialSecurityNumbers.add(socialSecurityNumber.toString());
            socialSecurityNumber.delete(0, socialSecurityNumber.length());
        }

        return socialSecurityNumbers;
    }

    public List<Double> createWages(int quantity) {
        Random random = new Random();
        List<Double> wages = new ArrayList<>();

        for(int i = 0; i < quantity; ++i) {
            double wage = 1501 + (3999 - 1501) * random.nextDouble();

            wages.add(wage);
        }

        return wages;
    }

    public List<String> createEmail(List<Employee> externalEmployees) {
        Random random = new Random();
        List<String> emails = new ArrayList<>();
        StringBuilder email = new StringBuilder();

        List<String> emailProviders = new ArrayList<>(Arrays.asList("@gmx.es", "@gmx.net", "@gmx.at", "@hotmail.com", "@outlook.com",
                                                                    "@gmail.com", "@yahoo.com", "@protonmail.com", "@zoho.com"));

        for (Employee externalEmployee : externalEmployees) {
            email.append(externalEmployee.getForename().toLowerCase());
            email.append(".");
            email.append(externalEmployee.getSurname().toLowerCase());
            email.append(emailProviders.get(random.nextInt(emailProviders.size())));

            emails.add(email.toString());

            email.delete(0, email.length());
        }

        return emails;
    }

    public List<String> createTelNumbers(int quantity) {
        Random random = new Random();
        List<String> telNumbers = new ArrayList<>();
        StringBuilder telNumber = new StringBuilder();

        List<String> telProviders = new ArrayList<>(Arrays.asList("0664", "0650", "0677", "0660", "0676", "0699"));

        for(int i = 0; i < quantity; ++i) {
            String prefix = telProviders.get(random.nextInt(telProviders.size()));
            String postfix = switch (prefix) {
                case "0650", "0676", "0677" -> Integer.toString((random.nextInt((78999999 - 11111111) + 11111111)));
                case "0664", "0660", "0699" -> Integer.toString((random.nextInt((789999999 - 111111111) + 111111111)));
                default -> "";
            };

            telNumber.append(prefix).append(postfix);
            telNumbers.add(telNumber.toString());

            telNumber.delete(0, telNumber.length());
        }

        return telNumbers;
    }

    public List<String> createActivity(int quantity) {
        Random random = new Random();
        List<String> activitiesToChoose = new ArrayList<>(Arrays.asList("Übersetzung", "Steuerberatung", "Graphik Design",
                                                                        "Korrekturlesen", "Vertriebsberatung"));
        List<String> activities = new ArrayList<>();

        for(int i = 0; i < quantity; ++i) {
            activities.add(activitiesToChoose.get(random.nextInt(activitiesToChoose.size())));
        }

        return activities;
    }

    public List<Author> createAuthors() {
        return customFileReader.readFileCSVAuthors("authors.csv");
    }

    public List<Book> createBooks() {
        return customFileReader.readFileCSVBooks("books.csv");
    }

    public List<String[]> createListBooksToAuthors() {
        return customFileReader.readFilesCSV("bookauthor.csv");
    }

    public List<BookStore> createBookStores() {
        return customFileReader.readFileCSVBookStores("bookstores.csv");
    }

    public List<Rack> createRacks() {
        List<Rack> racks = new ArrayList<>();
        List<String> genres = new ArrayList<>(Arrays.asList("politics", "theory", "kids", "literature", "art-books", "comics", "history"));
        List<Integer> bookStoreIDs = new ArrayList<>();
        Random random = new Random();

        String sql = "SELECT lag_id FROM buchlager";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookStoreIDs.add(rs.getInt(1));
                }
            }
        }
        catch(Exception exception) {
            System.err.println("Fehler bei Versuch die Mitarbeiter ID zu bekommen: " + exception.getMessage());
        }


        for(int i = 0; i < bookStoreIDs.size(); ++i) {
            int count = 1;
            for(String genre : genres) {
                int capacity = random.nextInt((3999 - 401)) + 401;
                racks.add(new Rack(count, genre, capacity, bookStoreIDs.get(i)));
                ++count;
            }
        }

        for(int i = 0; i < bookStoreIDs.size(); ++i) {
            int count = 8;
            for(String genre : genres) {
                int capacity = random.nextInt((3999 - 401)) + 401;
                racks.add(new Rack(count, genre, capacity, bookStoreIDs.get(i)));
                ++count;
            }
        }

        return racks;
    }

    public List<Integer[]> fillRacks() {
        List<Integer[]> storedBooks = new ArrayList<>();
        List<Rack> racks = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        Random random = new Random();

        String sqlRacks = "SELECT * FROM regal";
        String sqlBooks = "SELECT buch_id, genre FROM buch";

        try(Connection con = DriverManager.getConnection(
                DB_CONNECTOR.getDatabase(), DB_CONNECTOR.getUsername(), DB_CONNECTOR.getPassword());
            PreparedStatement stmtRacks = con.prepareStatement(sqlRacks);
            PreparedStatement stmtBooks = con.prepareStatement(sqlBooks)
        ) {
            try(ResultSet rsRacks = stmtRacks.executeQuery()) {
                while (rsRacks.next()) {
                    racks.add(new Rack(rsRacks.getInt(1), rsRacks.getString(2), rsRacks.getInt(3), rsRacks.getInt(4)));
                }
            }

            try(ResultSet rsBooks = stmtBooks.executeQuery()) {
                while(rsBooks.next()) {
                    books.add(new Book(rsBooks.getInt(1), rsBooks.getString(2)));
                }
            }
        }
        catch(Exception exception) {
            System.err.println("Fehler bei Versuch die Mitarbeiter ID zu bekommen: " + exception.getMessage());
        }


        for(int i = 0; i < books.size(); ++i) {
            int totalBooksPerRack = 0;
            int amountOfBooks = random.nextInt(200 - 50) + 50;
            List<Rack> rackOfOneGenre = new ArrayList<>();

            for(Rack rackGenre : racks) {
                if(rackGenre.getRackGenreName().equals(books.get(i).getGenre())) {
                    rackOfOneGenre.add(rackGenre);
                }
            }

            Rack rack = rackOfOneGenre.get(random.nextInt(rackOfOneGenre.size()));

            for(Integer[] storedBook : storedBooks) {
                if(storedBook[0] == rack.getRackID() && storedBook[2] == rack.getBookStoreID()) {
                    totalBooksPerRack += storedBook[3];
                }
            }

            if((amountOfBooks + totalBooksPerRack) >= rack.getCapacity()) {
                continue;
            }

            storedBooks.add(new Integer[]{books.get(i).getBookID(), rack.getRackID(), rack.getBookStoreID(), amountOfBooks});
        }


        return storedBooks;
    }

    public List<String[]> getBookReviewer() {
        return this.customFileReader.readFilesCSV("book_reviewer.csv");
    }

    public List<String[]> getBookReviews() {
        return this.customFileReader.readFilesCSV("recensions.csv");
    }

    public List<String[]> getRelationReviewerReviews() {
        return this.customFileReader.readFilesCSV("reviewer_review.csv");
    }
}
