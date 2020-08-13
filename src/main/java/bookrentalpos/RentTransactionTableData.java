package bookrentalpos;

public class RentTransactionTableData {
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private double retailPrice;
    private String rentDuration;
    private String totalPrice;

    public RentTransactionTableData() {
    }

    public RentTransactionTableData(int bookId, String bookTitle, String bookAuthor, double retailPrice, String rentDuration, String totalPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.retailPrice = retailPrice;
        this.rentDuration = rentDuration;
        this.totalPrice = totalPrice;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getRentDuration() {
        return rentDuration;
    }

    public void setRentDuration(String rentDuration) {
        this.rentDuration = rentDuration;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
