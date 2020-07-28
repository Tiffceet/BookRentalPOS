package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store transaction of the POS system
 *
 * @author Looz
 * @version 1.0
 */
public class Transaction extends Entity {

    private int rentDurationInDays;
    private TransactionType type;
    private int staffHandled;
    private int memberInvovled;
    private int bookInvovled;

    /**
     * Constructor used to create a new Transaction where ID is not required
     *
     * @param type TransactionType Enum - RENT, RETURN and RESERVE
     * @param staff staffID
     * @param memberInvovled memberID
     * @param bookInvovled bookID
     * @param rentDurationInDays integer, you may set this to 0 if this field is
     * not needed
     */
    public Transaction(TransactionType type, int staff, int memberInvovled, int bookInvovled, int rentDurationInDays) {
	this.type = type;
	this.rentDurationInDays = rentDurationInDays;
	this.staffHandled = staff;
	this.memberInvovled = memberInvovled;
	this.bookInvovled = bookInvovled;
    }

    /**
     * Constructor used for importing data from database\n DO NOT use this to
     * create new Transaction
     *
     * @see #Transaction(TransactionType, int, int, int, int)
     */
    public Transaction(int id, String date, TransactionType type, int staff, int memberInvovled, int bookInvovled, int rentDurationInDays) {
	setID(id);
	setDateCreated(date);
	this.type = type;
	this.rentDurationInDays = rentDurationInDays;
	this.staffHandled = staff;
	this.memberInvovled = memberInvovled;
	this.bookInvovled = bookInvovled;
    }

    /**
     * @return Rent Duration if any, will return 0 if this transaction doesnt
     * have rentDuration
     */
    public int getRentDurationInDays() {
	return rentDurationInDays;
    }

    /**
     *
     * @return return TransactionType enum - RENT, RESERVE and RETURN
     */
    public TransactionType getType() {
	return type;
    }

    /**
     *
     * @param rentDurationInDays Default RentDuration is 0, use this to set rent
     * duration in days(int)
     */
    public void setRentDurationInDays(int rentDurationInDays) {
	this.rentDurationInDays = rentDurationInDays;
    }

    /**
     *
     * @param type set Transaction type - RENT, RESERVE, RETURN
     */
    public void setType(TransactionType type) {
	this.type = type;
    }

    /**
     *
     * @return StaffID will be returned
     */
    public int getStaffHandled() {
	return staffHandled;
    }

    /**
     *
     * @param staffHandled staffID of this transaction, note that this will not
     * validate with database whether if staffID is valid
     */
    public void setStaffHandled(int staffHandled) {
	this.staffHandled = staffHandled;
    }

    /**
     *
     * @return return MemberID, can be 0 if the member was removed previously
     */
    public int getMemberInvovled() {
	return memberInvovled;
    }

    /**
     *
     * @param memberInvovled MemberID of this transaction, note that this will
     * not validate with database whether if MemberID is valid
     */
    public void setMemberInvovled(int memberInvovled) {
	this.memberInvovled = memberInvovled;
    }

    /**
     *
     * @return return BookID of this transaction, can be 0 if the book was
     * previously removed
     */
    public int getBookInvovled() {
	return bookInvovled;
    }

    /**
     *
     * @param bookInvovled bookID of this transaction, note that this will not
     * validate with database whether if BookID is valid
     */
    public void setBookInvovled(int bookInvovled) {
	this.bookInvovled = bookInvovled;
    }

}
