public class BookStore {
    private int bookStoreID;
    private String street;
    private String place;
    private String zip;
    private String country;

    public BookStore (String street, String place, String zip, String country) {
        this.street = street;
        this.place = place;
        this.zip = zip;
        this.country = country;
    }

    public int getBookStoreID() {
        return bookStoreID;
    }

    public void setBookStoreID(int bookStoreID) {
        this.bookStoreID = bookStoreID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
