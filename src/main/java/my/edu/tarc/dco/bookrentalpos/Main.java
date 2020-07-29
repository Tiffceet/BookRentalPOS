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
	MemberManager mm = new MemberManager(db);
	TransactionManager tm = new TransactionManager(db, book);
	stf.removeStaff(1);
	mm.removeMember(1);
//	stf.registerStaff(new Staff("looz", "010802"));
//	book.addBook(new Book("Book1", 3.4));
//	book.addBook(new Book("Book2", 6.4));
//	book.addBook(new Book("Book3", 7.4));
//	mm.registerMember(new Member("013746579", "ali"));
//	mm.registerMember(new Member("213746579", "ahmad"));
//	mm.registerMember(new Member("913746579", "sarah"));
    }
}
