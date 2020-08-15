package bookrentalpos;

public class ReserveTransactionTableData {
    private String dateReserved;
    private String bookID;
    private String bookTitle;
    private String bookAuthor;

    public ReserveTransactionTableData() {
    }

    public ReserveTransactionTableData(String dateReserved, String bookID, String bookTitle, String bookAuthor) {
        this.dateReserved = dateReserved;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public String getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(String dateReserved) {
        this.dateReserved = dateReserved;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
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
}
