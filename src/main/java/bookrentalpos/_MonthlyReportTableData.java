package bookrentalpos;

public class _MonthlyReportTableData {
    private String date;
    private String memberName;
    private String type;
    private String bookTitle;
    private String finalAmount;

    public _MonthlyReportTableData(String date, String memberName, String type, String bookTitle, String finalAmount) {
        this.date = date;
        this.memberName = memberName;
        this.type = type;
        this.bookTitle = bookTitle;
        this.finalAmount = finalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }
}
