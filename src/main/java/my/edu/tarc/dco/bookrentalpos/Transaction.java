package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Transaction extends Entity {

    private int rentDurationInDays;
    private TransactionType type;
    private int staffHandled;
    private int memberInvovled;
    private int bookInvovled;

    // for new transaction use
    public Transaction(TransactionType type, int staff, int memberInvovled, int bookInvovled, int rentDurationInDays) {
	this.type = type;
	this.rentDurationInDays = rentDurationInDays;
	this.staffHandled = staff;
	this.memberInvovled = memberInvovled;
	this.bookInvovled = bookInvovled;
    }

    // Constructor for import use
    public Transaction(int id, String date, TransactionType type, int staff, int memberInvovled, int bookInvovled, int rentDurationInDays) {
	setID(id);
	setDateCreated(date);
	this.type = type;
	this.rentDurationInDays = rentDurationInDays;
	this.staffHandled = staff;
	this.memberInvovled = memberInvovled;
	this.bookInvovled = bookInvovled;
    }

    public int getRentDurationInDays() {
	return rentDurationInDays;
    }

    public TransactionType getType() {
	return type;
    }

    public void setRentDurationInDays(int rentDurationInDays) {
	this.rentDurationInDays = rentDurationInDays;
    }

    public void setType(TransactionType type) {
	this.type = type;
    }

    public int getStaffHandled() {
	return staffHandled;
    }

    public void setStaffHandled(int staffHandled) {
	this.staffHandled = staffHandled;
    }

    public int getMemberInvovled() {
	return memberInvovled;
    }

    public void setMemberInvovled(int memberInvovled) {
	this.memberInvovled = memberInvovled;
    }

    public int getBookInvovled() {
	return bookInvovled;
    }

    public void setBookInvovled(int bookInvovled) {
	this.bookInvovled = bookInvovled;
    }
    

}
