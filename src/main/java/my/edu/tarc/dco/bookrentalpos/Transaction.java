package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Transaction extends Entity {

    private int rentDurationInDays;
    private TransactionType type;
    private Staff staffHandled;
    private Member memberInvovled;
    private Book bookInvovled;

    // for new transaction use
    public Transaction(TransactionType type, Staff staff, Member memberInvovled, Book bookInvovled, int rentDurationInDays) {
	this.type = type;
	this.rentDurationInDays = rentDurationInDays;
	this.staffHandled = staff;
	this.memberInvovled = memberInvovled;
	this.bookInvovled = bookInvovled;
    }

    // Constructor for import use
    public Transaction(String id, String date, TransactionType type, Staff staff, Member memberInvovled, Book bookInvovled, int rentDurationInDays) {
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

    public Staff getStaffHandled() {
	return staffHandled;
    }

    public Member getMemberInvovled() {
	return memberInvovled;
    }

    public Book getBookInvovled() {
	return bookInvovled;
    }

    public void setRentDurationInDays(int rentDurationInDays) {
	this.rentDurationInDays = rentDurationInDays;
    }

    public void setType(TransactionType type) {
	this.type = type;
    }

    public void setStaffHandled(Staff staffHandled) {
	this.staffHandled = staffHandled;
    }

    public void setMemberInvovled(Member memberInvovled) {
	this.memberInvovled = memberInvovled;
    }

    public void setBookInvovled(Book bookInvovled) {
	this.bookInvovled = bookInvovled;
    }

}
