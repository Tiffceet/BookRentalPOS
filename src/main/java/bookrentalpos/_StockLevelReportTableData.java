package bookrentalpos;

public class _StockLevelReportTableData {
    private String bookTitle;
    private String author;
    private String amountInSystem;
    private String amountInStore;
    private String finalAmount;

    public _StockLevelReportTableData(String bookTitle, String author, String amountInSystem, String amountInStore, String finalAmount) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.amountInSystem = amountInSystem;
        this.amountInStore = amountInStore;
        this.finalAmount = finalAmount;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAmountInSystem() {
        return amountInSystem;
    }

    public void setAmountInSystem(String amountInSystem) {
        this.amountInSystem = amountInSystem;
    }

    public String getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(String amountInStore) {
        this.amountInStore = amountInStore;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }
}
