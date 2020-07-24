package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */

public class Main {

    public static void main(String[] args) {

	DBManager db = new DBManager();
	StaffManager stf = new StaffManager(db);
	BookManager book = new BookManager(db);
	book.addBook(new Book("Book B",10.1));
 }
}
