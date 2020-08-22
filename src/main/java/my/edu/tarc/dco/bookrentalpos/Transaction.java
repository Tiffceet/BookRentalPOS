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
    private Staff staffHandled;
    private Member memberInvovled;
    private Book bookInvovled;
    private double cashFlow;

    /**
     * Blank
     */
    public Transaction() {

    }

    /**
     * Constructor to create a new ReserveTransaction
     *
     * @param staff          staffID
     * @param memberInvovled memberID
     * @param bookInvovled   bookID
     */
    public Transaction(Staff staff, Member memberInvovled, Book bookInvovled) {
        this.type = TransactionType.RESERVE;
        this.staffHandled = staff;
        this.memberInvovled = memberInvovled;
        this.bookInvovled = bookInvovled;
        this.cashFlow = 0;
    }

    /**
     * Constructor used for Returntransaction
     *
     * @param staff          staffID
     * @param memberInvovled memberID
     * @param bookInvovled   bookID
     * @param cashFlow       deposit returned to customer - penalty(if any)
     * @see #Transaction(Staff, Member, Book)
     */
    public Transaction(Staff staff, Member memberInvovled, Book bookInvovled, double cashFlow) {
        this.type = TransactionType.RETURN;
        this.staffHandled = staff;
        this.memberInvovled = memberInvovled;
        this.bookInvovled = bookInvovled;
        this.cashFlow = cashFlow;
    }

    /**
     * Constructor used to create a new RentTransaction
     *
     * @param staff              staffID
     * @param memberInvovled     memberID
     * @param bookInvovled       bookID
     * @param rentDurationInDays integer, you may set this to 0 if this field is
     *                           not needed
     * @param cashFlow           Double, you may set it to 0 if this field is not needed
     * @see #Transaction(Staff, Member, Book, int, double)
     * @see #Transaction(Staff, Member, Book, double)
     */
    public Transaction(Staff staff, Member memberInvovled, Book bookInvovled, int rentDurationInDays, double cashFlow) {
        this.type = TransactionType.RENT;
        this.rentDurationInDays = rentDurationInDays;
        this.staffHandled = staff;
        this.memberInvovled = memberInvovled;
        this.bookInvovled = bookInvovled;
        this.cashFlow = cashFlow;
    }

    /**
     * Constructor used to create a Transaction for discount deduction
     *
     * @param memberInvovled memberID
     * @param cashFlow       amount of discount amount given
     */
    public Transaction(Member memberInvovled, double cashFlow) {
        this.memberInvovled = memberInvovled;
        this.type = TransactionType.DISCOUNT;
        this.cashFlow = cashFlow;
    }

    /**
     * Constructor used for importing data from database<br>
     * DO NOT use this to create new Transaction
     *
     * @param id                 Transaction ID
     * @param date               Transaction Date
     * @param type               TransactionType Enumeration
     * @param staff              staffID
     * @param memberInvovled     memberID
     * @param bookInvovled       bookID
     * @param rentDurationInDays integer, you may set this to 0 if this field is
     *                           not needed
     * @param cashFlow           Double, you may set it to 0 if this field is not needed
     * @see #Transaction(Staff, Member, Book, int, double)
     * @see #Transaction(Staff, Member, Book, double)
     */
    public Transaction(int id, String date, TransactionType type, Staff staff, Member memberInvovled, Book bookInvovled, int rentDurationInDays, double cashFlow) {
        super(id, null, date);
        this.type = type;
        this.rentDurationInDays = rentDurationInDays;
        this.staffHandled = staff;
        this.memberInvovled = memberInvovled;
        this.bookInvovled = bookInvovled;
        this.cashFlow = cashFlow;
    }

    /**
     * @return Rent Duration if any, will return 0 if this transaction doesnt
     * have rentDuration
     */
    public int getRentDurationInDays() {
        return rentDurationInDays;
    }

    /**
     * @return return TransactionType enum - RENT, RESERVE and RETURN
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param rentDurationInDays Default RentDuration is 0, use this to set rent
     *                           duration in days(int)
     */
    public void setRentDurationInDays(int rentDurationInDays) {
        this.rentDurationInDays = rentDurationInDays;
    }

    /**
     * @param type set Transaction type - RENT, RESERVE, RETURN
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * @return StaffID will be returned
     */
    public Staff getStaffHandled() {
        return staffHandled;
    }

    /**
     * @param staffHandled staffID of this transaction, note that this will not
     *                     validate with database whether if staffID is valid
     */
    public void setStaffHandled(Staff staffHandled) {
        this.staffHandled = staffHandled;
    }

    /**
     * @return return MemberID, can be 0 if the member was removed previously
     */
    public Member getMemberInvovled() {
        return memberInvovled;
    }

    /**
     * @param memberInvovled MemberID of this transaction, note that this will
     *                       not validate with database whether if MemberID is valid
     */
    public void setMemberInvovled(Member memberInvovled) {
        this.memberInvovled = memberInvovled;
    }

    /**
     * @return return BookID of this transaction, can be 0 if the book was
     * previously removed
     */
    public Book getBookInvovled() {
        return bookInvovled;
    }

    /**
     * @param bookInvovled bookID of this transaction, note that this will not
     *                     validate with database whether if BookID is valid
     */
    public void setBookInvovled(Book bookInvovled) {
        this.bookInvovled = bookInvovled;
    }

    /**
     * @return cash flow of this transaction. Refer to constructor for how its being used
     * @see #Transaction(Staff, Member, Book, int, double)
     * @see #Transaction(Staff, Member, Book, double)
     */
    public double getCashFlow() {
        return cashFlow;
    }

    /**
     * @param cashFlow cashFlow of the Return / Rent Transaction
     */
    public void setCashFlow(double cashFlow) {
        this.cashFlow = cashFlow;
    }
}
