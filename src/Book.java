public class Book {
    private int bookID;
    private String isbn;
    private String title;
    private String description;
    private int pageNumber;
    private String cover;
    private int releaseYear;
    private double price;
    private String genre;
    private int noveltyStatus;

    public Book(String isbn, String title, String description, int pageNumber, String cover, int releaseYear, double price, String genre, int noveltyStatus) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.pageNumber = pageNumber;
        this.cover = cover;
        this.releaseYear = releaseYear;
        this.price = price;
        this.genre = genre;
        this.noveltyStatus = noveltyStatus;
    }

    public Book(int bookID, String genre) {
        this.bookID = bookID;
        this.genre = genre;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNoveltyStatus() {
        return noveltyStatus;
    }

    public void setNoveltyStatus(int noveltyStatus) {
        this.noveltyStatus = noveltyStatus;
    }

}
