package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Member extends Entity {

    private String phoneNo;
    private String email;
    private String icNo;    

    public Member(String icNo, String name, String phoneNo, String email) {
	this.icNo = icNo;
	setName(name);
	this.phoneNo = phoneNo;
	this.email = email;
    }

    // Constructor for import use
    
    public String getContacts(ContactType type) {
	if (type == ContactType.EMAIL) {
	    return this.email;
	} else if (type == ContactType.PHONE) {
	    return this.phoneNo;
	}
	return null;
    }
    
    public String getICNo()
    {
	return this.icNo;
    }

    @Override
    public String getID() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDateCreated() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setID(String id) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setName(String name) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDateCreated(String date) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
