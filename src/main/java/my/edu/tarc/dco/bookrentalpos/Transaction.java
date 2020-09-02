package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store transaction of the POS system
 *
 * @author Looz
 * @version 1.0
 */
public class Transaction extends Entity {

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
     * Constructor for every Transaction
     *
     * @param staff          staff object
     * @param memberInvovled member object
     * @param bookInvovled   book object
     */
    public Transaction(Staff staff, Member memberInvovled, Book bookInvovled) {
        this.staffHandled = staff;
        this.memberInvovled = memberInvovled;
        this.bookInvovled = bookInvovled;
        this.cashFlow = 0;
    }

    /**
     * Constructor used for importing data from database<br>
     * DO NOT use this to create new Transaction
     *
     * @param id                 Transaction ID
     * @param date               Transaction Date
     * @param type               TransactionType Enumeration
     * @param staff              staff object
     * @param memberInvolved     member object
     * @param bookInvolved       book object
     * @param cashFlow           Double, you may set it to 0 if this field is not needed
     */
    public Transaction(int id, String date, TransactionType type, Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow) {
        super(id, null, date);
        this.type = type;
        this.staffHandled = staff;
        this.memberInvovled = memberInvolved;
        this.bookInvovled = bookInvolved;
        this.cashFlow = cashFlow;
    }


    /**
     * @return return TransactionType enum - RENT, RESERVE and RETURN
     */
    public TransactionType getType() {
        return type;
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
    public Member getMemberInvolved() {
        return memberInvovled;
    }

    /**
     * @param memberInvovled MemberID of this transaction, note that this will
     *                       not validate with database whether if MemberID is valid
     */
    public void setMemberInvolved(Member memberInvovled) {
        this.memberInvovled = memberInvovled;
    }

    /**
     * @return return BookID of this transaction, can be 0 if the book was
     * previously removed
     */
    public Book getBookInvolved() {
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
     * @return cash flow of this transaction. Refer to different sub-classes of Transaction for how its being used
     * @see DiscountTransaction
     * @see RentTransaction
     * @see ReturnTransaction
     */
    public double getCashFlow() {
        return cashFlow;
    }

    /**
     * @param cashFlow cashFlow of the Transaction
     */
    public void setCashFlow(double cashFlow) {
        this.cashFlow = cashFlow;
    }
}
