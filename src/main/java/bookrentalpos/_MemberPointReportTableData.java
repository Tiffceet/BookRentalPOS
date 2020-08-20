package bookrentalpos;

public class _MemberPointReportTableData {
    private String date;
    private String transactionID;
    private String point;

    public _MemberPointReportTableData(String date, String transactionID, String point) {
        this.date = date;
        this.transactionID = transactionID;
        this.point = point;
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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
