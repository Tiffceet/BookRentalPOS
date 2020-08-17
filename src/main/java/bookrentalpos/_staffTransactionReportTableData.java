package bookrentalpos;

public class _staffTransactionReportTableData {
    String date;
    String transactionID;
    String customerName;
    String bookTitle;
    String transactionType;
    String finalAmount;

    public _staffTransactionReportTableData(String date, String transactionID, String customerName, String bookTitle, String transactionType, String finalAmount) {
        this.date = date;
        this.transactionID = transactionID;
        this.customerName = customerName;
        this.bookTitle = bookTitle;
        this.transactionType = transactionType;
        this.finalAmount = finalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }
}
