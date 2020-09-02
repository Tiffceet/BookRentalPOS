package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store reserve Transaction
 *
 * @author Looz
 * @version 1.0
 */
public class ReserveTransaction extends Transaction {


    /**
     * Blank
     */
    public ReserveTransaction() {

    }

    /**
     * Constructor for Reserve Transaction
     *
     * @param staff          staff object
     * @param memberInvolved member object
     * @param bookInvolved   book object
     */
    public ReserveTransaction(Staff staff, Member memberInvolved, Book bookInvolved) {
        super(staff, memberInvolved, bookInvolved);
        setCashFlow(0);
    }

    /**
     * Constructor used for importing data from database<br>
     * DO NOT use this to create new Transaction
     *
     * @param id             Transaction ID
     * @param date           Transaction Date
     * @param type           TransactionType Enumeration
     * @param staff          staff object
     * @param memberInvolved member object
     * @param bookInvolved   book object
     * @param cashFlow       Double, you may set it to 0 if this field is not needed
     */
    public ReserveTransaction(int id, String date, TransactionType type, Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow) {
        super(id, date, type, staff, memberInvolved, bookInvolved, cashFlow);
    }
}
