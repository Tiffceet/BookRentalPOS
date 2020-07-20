package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Entity {

    private String id;
    private String name;
    private String dateCreated;

    public final String getID() {
	return this.id;
    }

    public final int getIntID() {
	return Integer.parseInt(this.id);
    }
    
    public final String getName() {
	return this.name;
    }

    public final String getDateCreated() {
	return this.dateCreated;
    }

    protected final void setID(String id) {
	this.id = id;
    }

    protected final void setName(String name) {
	this.name = name;
    }

    protected final void setDateCreated(String date) {
	this.dateCreated = date;
    }
}
