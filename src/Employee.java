public class Employee {
    private int employeeID;
    private String forename;
    private String surname;

    public Employee(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    public Employee(int employeeID, String forename, String surname) {
        this.employeeID = employeeID;
        this.forename = forename;
        this.surname = surname;
    }

    public String getForename() {
        return this.forename;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getEmployeeID() {
        return this.employeeID;
    }
}
