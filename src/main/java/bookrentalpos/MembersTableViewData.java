package bookrentalpos;

public class MembersTableViewData {
    private String id = "";
    private String date = "";
    private String name = "";
    private String phone = "";
    private String email = "";
    private String nric = "";

    public MembersTableViewData() {
        this("", "", "", "", "", "");
    }

    public MembersTableViewData(String id, String date, String name, String phone, String email, String nric) {
        setId(id);
        setDate(date);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setNric(nric);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

}
