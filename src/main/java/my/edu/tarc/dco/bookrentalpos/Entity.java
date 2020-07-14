package my.edu.tarc.dco.bookrentalpos;


/**
 *
 * @author Looz
 */
public abstract class Entity {
    protected String id;
    protected String name;
    protected String dateCreated;
    public abstract String getID();
    public abstract String getName();
    public abstract String getDateCreated();
    public abstract void setID(String id);
    public abstract void setName(String name);
    public abstract void setDateCreated(String date);
}
