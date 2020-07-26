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
    public Staff(int id, String dateCreated, String usrname, String hashedPW) {
	setID(id);
	setName(usrname);
	setDateCreated(dateCreated);
	this.pwHash = hashedPW;
    }

    public boolean hasAdminPrivillage() {
	return this.isAdmin;
    }

    public String getPW() {
	return this.pwHash;
    }
}
