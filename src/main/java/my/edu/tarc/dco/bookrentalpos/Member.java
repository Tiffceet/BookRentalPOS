package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Member extends Entity {

    private String phoneNo;
    private String email;
    private String icNo;    

    public Member(String icNo, String name)
    {
	this.icNo = icNo;
	setName(name);
	this.phoneNo = "";
	this.email = "";
    }
    
    public Member(String icNo, String name, String phoneNo, String email) {
	this.icNo = icNo;
	setName(name);
	this.phoneNo = phoneNo;
	this.email = email;
    }

    // Constructor for import use
    public Member(int id, String date, String name, String phoneNo, String email, String icNo) {
	setID(id);
	setDateCreated(date);
	setName(name);
	this.phoneNo = phoneNo;
	this.email = email;
	this.icNo = icNo;
    }
    
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

}
