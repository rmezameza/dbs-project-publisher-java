import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataManager {
    private CustomFileReader customFileReader = new CustomFileReader();
    private final DatabaseConnector DB_CONNECTOR = new DatabaseConnector();


    public List<Employee> createEmployees(int numberOfEmployees) {
        List<Employee> employees = new ArrayList<>();
        Random randomIndex = new Random();

        String forename = "";
        String surname = "";


        List<String> forenames = customFileReader.readFile("employee_forenames.txt");
        List<String> surnames = customFileReader.readFile("employee_surnames.txt");

        for(int i = 0; i < numberOfEmployees; ++i) {
            forename = forenames.get(randomIndex.nextInt(forenames.size()));
            surname = surnames.get(randomIndex.nextInt(surnames.size()));

            employees.add(new Employee(forename, surname));
        };

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

            if(month.equals("02")) {
                int day = random.nextInt((28 - 1) + 1);
                if(day < 10) {
                    socialSecurityNumber.append("0");
                    socialSecurityNumber.append(day);
                }
                else {
                    socialSecurityNumber.append(day);
                }
            }
            else {
                int day = random.nextInt((30 - 1) + 1);
                if(day < 10) {
                    socialSecurityNumber.append("0");
                    socialSecurityNumber.append(day);
                }
                else {
                    socialSecurityNumber.append(day);
                }
            }

            socialSecurityNumber.append(month);

            String year = years.get(random.nextInt(years.size()));
            year = year.substring(2);
            year = (year.equals("950")) ? year.substring(1) : year;

            System.out.println(year);
            socialSecurityNumber.append(year);

            System.out.println(socialSecurityNumber);

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

    public List<String> createEmail(int quantity, List<Employee> externalEmployees) {
        Random random = new Random();
        List<String> emails = new ArrayList<>();
        StringBuffer email = new StringBuffer();

        List<String> emailProviders = new ArrayList<>(Arrays.asList("@gmx.es", "@gmx.net", "@gmx.at", "@hotmail.com", "@outlook.com",
                                                                    "@gmail.com", "@yahoo.com", "@protonmail.com", "@zoho.com"));

        for(int i = 0; i < externalEmployees.size(); ++i) {
            email.append(externalEmployees.get(i).getForename().toLowerCase());
            email.append(".");
            email.append(externalEmployees.get(i).getSurname().toLowerCase());
            email.append(emailProviders.get(random.nextInt(emailProviders.size())));

            emails.add(email.toString());

            email.delete(0, email.length());
        }

        return emails;
    }

    public List<String> createTelNumbers(int quantity) {
        Random random = new Random();
        List<String> telNumbers = new ArrayList<>();
        StringBuffer telNumber = new StringBuffer();

        List<String> telProviders = new ArrayList<>(Arrays.asList("0664", "0650", "0677", "0660", "0676", "0699"));

        for(int i = 0; i < quantity; ++i) {
            String prefix = telProviders.get(random.nextInt(telProviders.size()));
            String postfix = "";

            switch(prefix) {
                case "0650":
                case "0676":
                case "0677":
                    postfix = Integer.toString((random.nextInt((78999999 - 11111111) + 11111111)));
                    break;
                case "0664":
                case "0660":
                case "0699":
                    postfix = Integer.toString((random.nextInt((789999999 - 111111111) + 111111111)));
                    break;
            }

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
}
