package bookrentalpos;

public class _ReserveTransactionBookTableData {
    private String id;
    private String title;
    private String author;
    private String erDate;

    public _ReserveTransactionBookTableData(String id, String title, String author, String erDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.erDate = erDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getErDate() {
        return erDate;
    }

    public void setErDate(String erDate) {
        this.erDate = erDate;
    }
}
