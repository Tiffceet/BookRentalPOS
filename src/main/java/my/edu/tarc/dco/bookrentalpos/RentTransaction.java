package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store Rent Transaction
 *
 * @author Looz
 * @version 1.0
 */
public class RentTransaction extends Transaction {
    private int rentDurationInDays;

    /**
     * Blank
     */
    public RentTransaction() {

    }

    /**
     * Constructor used to create a new RentTransaction
     *
     * @param staff              staff object
     * @param memberInvolved     member object
     * @param bookInvolved       book object
     * @param rentDurationInDays integer, you may set this to 0 if this field is
     *                           not needed
     * @param cashFlow           Double, you may set it to 0 if this field is not needed
     */
    public RentTransaction(Staff staff, Member memberInvolved, Book bookInvolved, int rentDurationInDays, double cashFlow) {
        super(staff, memberInvolved, bookInvolved);
        setType(TransactionType.RENT);
        setCashFlow(cashFlow);
        this.rentDurationInDays = rentDurationInDays;
    }

    /**
     * Constructor used to import from database<br>
     * DO NOT use it to create new RentTransaction
     *
     * @param id                 Transaction ID
     * @param date               Transaction Date
     * @param type               TransactionType Enumeration
     * @param staff              staff object
     * @param memberInvolved     member object
     * @param bookInvolved       book object
     * @param cashFlow           Double, you may set it to 0 if this field is not needed
     * @param rentDurationInDays you may set this to 0 if this field is
     *                           not needed
     */
    public RentTransaction(int id, String date, TransactionType type, Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow, int rentDurationInDays) {
        super(id, date, type, staff, memberInvolved, bookInvolved, cashFlow);
        this.rentDurationInDays = rentDurationInDays;
    }

    /**
     * @param rentDurationInDays Default RentDuration is 0, use this to set rent
     *                           duration in days(int)
     */
    public void setRentDurationInDays(int rentDurationInDays) {
        this.rentDurationInDays = rentDurationInDays;
    }

    /**
     * @return Rent Duration if any, will return 0 if this transaction doesn't
     * have rentDuration
     */
    public int getRentDurationInDays() {
        return rentDurationInDays;
    }
}
