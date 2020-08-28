package my.edu.tarc.dco.bookrentalpos;

/**
 * Genric abstract class for all Managers
 * @param <T> type of the Manager
 * @author Looz
 * @version 1.0
 */
public abstract class Manager<T> {
    public abstract T getById(int id);
    public abstract T getByName(String name);
    public abstract T[] getCache();
    public abstract boolean add(T entry);
    public abstract boolean update(T ref);
    public abstract boolean remove(T ref);
    public abstract void reload();
}
