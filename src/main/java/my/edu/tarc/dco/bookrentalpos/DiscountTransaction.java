package my.edu.tarc.dco.bookrentalpos;

public class DiscountTransaction extends Transaction{

    /**
     * Constructor used to create a Transaction for discount deduction
     *
     * @param memberInvolved member object
     * @param cashFlow       amount of discount amount given
     */
    public DiscountTransaction(Member memberInvolved, double cashFlow) {
        super(null, memberInvolved, null);
        setType(TransactionType.DISCOUNT);
        setCashFlow(cashFlow);
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
    public DiscountTransaction(int id, String date, TransactionType type, Staff staff, Member memberInvolved, Book bookInvolved, double cashFlow) {
        super(staff, memberInvolved, bookInvolved);
        setType(type);
        setCashFlow(cashFlow);
    }
}
