public class Author {
    private int authorID;
    private String forename;
    private String surname;
    private String bio;

    public Author(String forename, String surname, String bio) {
        this.forename = forename;
        this.surname = surname;
        this.bio = bio;
    }

    public String getForename() {
        return this.forename;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getBio() {
        return this.bio;
    }
}
