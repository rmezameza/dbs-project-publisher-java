public class DatabaseConnector {
    private final String USERNAME = "a11848674";
    private final String PASSWORD = "dwH$KI6<hBS)#\"oy3*bg";
    private final String DATABASE = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";

    protected String getUsername() {
        return this.USERNAME;
    }

    protected String getPassword() {
        return this.PASSWORD;
    }

    protected String getDatabase() {
        return this.DATABASE;
    }
}
