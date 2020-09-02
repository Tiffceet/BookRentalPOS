package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store RentTransaction
 *
 * @author Looz
 * @version 1.0
 */
public class ReturnTransaction extends Transaction {
    /**
     * Blank
     */
    public ReturnTransaction() {
    }

    /**
     * Constructor used for Returntransaction
     *
     * @param staff          staff object
     * @param memberInvolved member object
     * @param bookInvolved   book object
     * @param cashFlow       deposit returned to customer - penalty(if any)
     */
    public ReturnTransaction(Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow) {
        super(staff, memberInvolved, bookInvolved);
        setType(TransactionType.RETURN);
        setCashFlow(cashFlow);
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
    public ReturnTransaction(int id, String date, TransactionType type, Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow) {
        super(id, date, type, staff, memberInvolved, bookInvolved, cashFlow);
    }
}
