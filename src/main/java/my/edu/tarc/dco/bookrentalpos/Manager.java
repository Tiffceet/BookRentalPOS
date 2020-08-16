package my.edu.tarc.dco.bookrentalpos;

public abstract class Manager<T> {
    public abstract T getById(int id);
    public abstract T[] getCache();
    public abstract boolean add(T entry);
    public abstract boolean update(T ref);
    public abstract boolean remove(T ref);

}
