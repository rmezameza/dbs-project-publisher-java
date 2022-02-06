public class Rack {
    private int rackID;
    private String rackGenreName;
    private int capacity;
    private int bookStoreID;

    public Rack(int rackID, String rackGenreName, int capacity, int bookStoreID) {
        this.rackID = rackID;
        this.rackGenreName = rackGenreName;
        this.capacity = capacity;
        this.bookStoreID = bookStoreID;
    }

    public int getRackID() {
        return rackID;
    }

    public void setRackID(int rackID) {
        this.rackID = rackID;
    }

    public String getRackGenreName() {
        return rackGenreName;
    }

    public void setRackGenreName(String rackGenreName) {
        this.rackGenreName = rackGenreName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBookStoreID() {
        return bookStoreID;
    }

    public void setBookStoreID(int bookStoreID) {
        this.bookStoreID = bookStoreID;
    }
}
