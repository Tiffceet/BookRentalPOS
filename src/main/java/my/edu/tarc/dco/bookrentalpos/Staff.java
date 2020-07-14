package my.edu.tarc.dco.bookrentalpos;


/**
 *
 * @author Looz
 */
public class Staff extends Entity {

    private boolean isAdmin = false;
    private String pwHash;

    // Constructor used for - new Staff creation
    public Staff(String usrname, String password) {
	setName(usrname);
	this.pwHash = CustomUtil.md5Hash(password);
    }

    // Constructor used for - staff data import
    public Staff(String id, String dateCreated, String usrname, String hashedPW) {
	setID(id);
	setName(usrname);
	setDateCreated(dateCreated);
	this.pwHash = hashedPW;
    }

    public boolean hasAdminPrivillage() {
	return this.isAdmin;
    }

    @Override
    public String getID() {
	return this.id;
    }

    @Override
    public String getName() {
	return this.name;
    }

    @Override
    public String getDateCreated() {
	return this.dateCreated;
    }

    public String getPW() {
	return this.pwHash;
    }

    @Override
    public void setID(String id) {
	this.id = id;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public void setDateCreated(String date) {
	this.dateCreated = date;
    }
}
