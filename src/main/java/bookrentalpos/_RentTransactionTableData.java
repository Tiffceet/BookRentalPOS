package bookrentalpos;

public class _RentTransactionTableData {
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String deposit;
    private String charges;
    private String rentDuration;
    private String totalPrice;

    public _RentTransactionTableData() {
    }

    public _RentTransactionTableData(int bookId, String bookTitle, String bookAuthor, String deposit, String charges, String rentDuration, String totalPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.deposit = deposit;
        this.charges = charges;
        this.rentDuration = rentDuration;
        this.totalPrice = totalPrice;
    }

    public String getCharges() { return charges; }

    public void setCharges(String charges) { this.charges = charges; }

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

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
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
