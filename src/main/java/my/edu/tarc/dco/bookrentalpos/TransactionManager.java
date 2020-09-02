package my.edu.tarc.dco.bookrentalpos;

import bookrentalpos.Dialog;
import bookrentalpos.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Used to load all transactions from the database
 *
 * @author Looz
 * @version 1.0
 */
public class TransactionManager extends Manager<Transaction> {

    private Transaction[] transactionList;
    private final int ARRAY_SIZE = 100;
    private DBManager db;
    private BookManager bm;
    private MemberManager mm;
    private int transactionCount;

    /**
     * Contains the rates for the deposit to pay for customer
     * HashMap keys:    rentalWeeks
     * HashMap values:  rates in %
     * If more than 4 weeks, max % applies
     */
    public final HashMap<Integer, Integer> DEPOSIT_RATES = new HashMap<Integer, Integer>() {
        {
            put(1, 10);
            put(2, 20);
            put(3, 30);
            put(4, 50);
        }
    };

    /**
     * This variable is storing the members points needed to apply discount
     */
    public final int MEMBER_POINTS_NEEDED_TO_CLAIM_DISCOUNT = 500;

    /**
     * Deposit deduction for each day the customer is late in returning the book
     */
    public final int PENALTY_RATES = 5;

    public final int MAXIMUM_RENT_PER_MEMBER = 10;

    public TransactionManager(DBManager db, BookManager bm, MemberManager mm) {
        this.db = db;
        this.bm = bm;
        this.mm = mm;
        transactionList = new Transaction[ARRAY_SIZE];
        reload();
    }

    /**
     * Reload all the data from database
     */
    @Override
    public void reload() {
        transactionCount = 0;
        try {
            ResultSet rs = db.resultQuery("SELECT * FROM transactions");
            while (rs.next()) {
                int transID = rs.getInt("id");
                String transDate = rs.getString("date");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                Staff stf = Main.sm.getById(rs.getInt("staffHandled"));
                Member mem = Main.mm.getById(rs.getInt("memberInvolved"));
                Book book = Main.bm.getById(rs.getInt("bookInvolved"));
                int rentDurationInDays = rs.getInt("rentDurationInDays");
                double cashFlow = rs.getDouble("cashFlow");
                Transaction t;
                switch (type) {
                    case RENT:
                        t = new RentTransaction(transID, transDate, type, stf, mem, book, cashFlow, rentDurationInDays);
                        break;
                    case RESERVE:
                        t = new ReserveTransaction(transID, transDate, type, stf, mem, book, cashFlow);
                        break;
                    case RETURN:
                        t = new ReturnTransaction(transID, transDate, type, stf, mem, book, cashFlow);
                        break;
                    case DISCOUNT:
                        t = new DiscountTransaction(transID, transDate, type, stf, mem, book, cashFlow);
                        break;
                    default:
                        continue;
                }
                transactionList[transactionCount++] = t;
            }
            updateBookReservationStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * this function accepts transaction object where there is no id and date`
     * @param trans accept Transaction object
     * @return Return true if the transaction was added into database successfully
     */
    @Override
    public boolean add(Transaction trans) {
        String sql;
        if (trans instanceof RentTransaction) {
            sql = String.format(
                    "INSERT INTO transactions(type, staffHandled, memberInvolved, bookInvolved, rentDurationInDays, cashFlow) VALUES('%s',%s,%s,%s,%d,%f)",
                    trans.getType(),
                    trans.getStaffHandled() == null ? "null" : trans.getStaffHandled().getId() + "",
                    trans.getMemberInvolved() == null ? "null" : trans.getMemberInvolved().getId() + "",
                    trans.getBookInvolved() == null ? "null" : trans.getBookInvolved().getId() + "",
                    ((RentTransaction) trans).getRentDurationInDays(),
                    trans.getCashFlow());
        } else {
            sql = String.format(
                    "INSERT INTO transactions(type, staffHandled, memberInvolved, bookInvolved, rentDurationInDays, cashFlow) VALUES('%s',%s,%s,%s,%d,%f)",
                    trans.getType(),
                    trans.getStaffHandled() == null ? "null" : trans.getStaffHandled().getId() + "",
                    trans.getMemberInvolved() == null ? "null" : trans.getMemberInvolved().getId() + "",
                    trans.getBookInvolved() == null ? "null" : trans.getBookInvolved().getId() + "",
                    0,
                    trans.getCashFlow());
        }
        if (db.updateQuery(sql) == 1) {
            try {
                // id and date is generated by sqlite, i need to make a copy of it and store it in my preloaded database
                // this query basically get the latest transactions entry inserted into database
                ResultSet rs = db.resultQuery("SELECT id, date FROM transactions WHERE id = (SELECT seq FROM sqlite_sequence WHERE name='transactions')");
                trans.setID(rs.getInt("id"));
                trans.setDateCreated(rs.getString("date"));

                // store in my preloaded database
                transactionList[transactionCount++] = trans;

                // Update respective table based on the transaction type
                Book b = trans.getBookInvolved();
                switch (trans.getType()) {
                    case RENT:
                        b.setLastRentedBy(trans.getMemberInvolved());
                        b.setRented(true);
                        b.setReserved(false);
                        bm.update(b);
                        break;
                    case RETURN:
                        b.setRented(false);
                        bm.update(b);
                        break;
                    case RESERVE:
                        b.setLastReservedBy(trans.getMemberInvolved());
                        b.setReserved(true);
                        bm.update(b);
                        break;
                    case DISCOUNT:
                        Member memberToEdit = trans.getMemberInvolved();
                        if (memberToEdit != null) {
                            memberToEdit.setMemberPoints(memberToEdit.getMemberPoints() - 500);
                            this.mm.update(memberToEdit);
                        }
                        break;
                }

            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * get Transaction by ID
     *
     * @param id Transaction ID
     * @return Transaction reference object if found, null if nothing was found
     */
    @Override
    public Transaction getById(int id) {
        for (int a = 0; a < this.transactionCount; a++) {
            if (transactionList[a].getId() == id) {
                return transactionList[a];
            }
        }
        return null;
    }

    /**
     * Get a cache of the transaction list<br>
     * Note: for the count of data you should get it from TransactionManager.getTransactionCount()
     *
     * @return an copy of Transaction list array
     * @see #getTransactionCount()
     */
    @Override
    public Transaction[] getCache() {
        return this.transactionList.clone();
    }

    /**
     * Update transaction table in database
     *
     * @param ref Transaction reference object preferably from Transaction.getById
     * @return true if update was successful, false if otherwise
     * @see #getById(int)
     */
    @Override
    public boolean update(Transaction ref) {
        String sql;
        if (ref instanceof RentTransaction) {
            sql = String.format(
                    "UPDATE transactions\n" +
                            "SET rentDurationInDays=%d, type='%s', staffHandled=%s, memberInvolved=%s, bookInvolved=%s, cashFlow=%.2f\n" +
                            "WHERE id=%d",
                    ((RentTransaction) ref).getRentDurationInDays(),
                    ref.getType(),
                    ref.getStaffHandled() == null ? "null" : ref.getStaffHandled().getId() + "",
                    ref.getMemberInvolved() == null ? "null" : ref.getMemberInvolved().getId() + "",
                    ref.getBookInvolved() == null ? "null" : ref.getBookInvolved().getId() + "",
                    ref.getCashFlow(),
                    ref.getId());
        } else {
            sql = String.format(
                    "UPDATE transactions\n" +
                            "SET rentDurationInDays=%d, type='%s', staffHandled=%s, memberInvolved=%s, bookInvolved=%s, cashFlow=%.2f\n" +
                            "WHERE id=%d",
                    0,
                    ref.getType(),
                    ref.getStaffHandled() == null ? "null" : ref.getStaffHandled().getId() + "",
                    ref.getMemberInvolved() == null ? "null" : ref.getMemberInvolved().getId() + "",
                    ref.getBookInvolved() == null ? "null" : ref.getBookInvolved().getId() + "",
                    ref.getCashFlow(),
                    ref.getId());
        }

        if (db.updateQuery(sql) != 1) {
            return false;
        }
        return true;
    }

    /**
     * Remove transaction of specific trans from database
     *
     * @param trans transaction trans
     * @return true if it was removed successfully, false if otherwise
     */
    @Override
    public boolean remove(Transaction trans) {
        String sql = "DELETE FROM transactions WHERE trans=" + trans.getId();
        if (db.updateQuery(sql) != 1) {
            return false;
        }

        // [1,23,4,5,6]
        // [1, 23, 5, 6]
        int b = 0;
        Transaction[] tmpList = new Transaction[ARRAY_SIZE];
        for (int a = 0; a < this.transactionCount; a++) {
            if (!transactionList[a].equals(trans)) {
                tmpList[b++] = transactionList[a];
            }
        }
        this.transactionCount--;
        this.transactionList = tmpList.clone();
        return true;
    }

    /**
     * This function is suppose to update the reservation status of all the books<br>
     * Criteria as stated below: <br>
     * - If the book is not reserved, dont touch it<br>
     * - If the book is reserved, check if the book is being rented at the moment<br>
     * - If the book is rented, dont touch it<br>
     * - If the book is not rented, or in other words it was returned, check if (return date - current date) &gt; 7 days<br>
     * - If &gt; 7 days, set reserved status as false<br>
     * - If !(&gt; 7 days), dont touch it
     */
    public void updateBookReservationStatus() {
        Book[] bookList = this.bm.getCache();
        for (int a = 0; a < bm.getBookCount(); a++) {
            if (bookList[a].isReserved()) {
                if (!bookList[a].isRented()) {
                    Date currentDate = new Date();

                    String sql = "SELECT * FROM transactions WHERE bookInvolved=" + bookList[a].getId() + " AND type='RETURN' ORDER BY id DESC";
                    String bookReturnedDate_str = "";
                    try {
                        ResultSet rs = db.resultQuery(sql);
                        if (rs != null) {
                            if (rs.next()) {
                                bookReturnedDate_str = rs.getString("date");
                            } else {
                                // if there are no rows next, still wondering how to deal with this
                                Dialog.alertBox("It seems like the reserved book was not rented when its reserved.\nIf you believe this is not your mistake, please contact the devs");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Dialog.alertBox("Something went wrong internally while trying to update BookReservation Status.");
                        return;
                    }

                    Date bookReturnedDate = CustomUtil.stringToDate(bookReturnedDate_str);

                    // Get the difference between current date and the date the book was returned
                    long diff = CustomUtil.daysDifference(currentDate, bookReturnedDate);

                    if (diff > 7) {
                        Book bookToModify = bm.getById(bookList[a].getId());
                        bookToModify.setReserved(false);
                        bm.update(bookToModify);
                    }

                }
            }
        }
    }

    /**
     * This function returns transaction of the last renting record of a specific book
     *
     * @param bk book object to be checked
     * @return Transaction reference object to the last renting record, return null if book is null or the book is not currently rented
     */
    public RentTransaction getBookLastRentTransaction(Book bk) {
        if (bk == null) {
            return null;
        }
        if (!bk.isRented()) return null;
        for (int a = this.transactionCount - 1; a != -1; a--) {
            if (transactionList[a].getType() == TransactionType.RENT && transactionList[a].getBookInvolved().equals(bk)) {
                return (RentTransaction) transactionList[a];
            }
        }
        return null;
    }

    /**
     * This function returns Transaction of the last reserve record of a specific book
     *
     * @param bk book object to be checked
     * @return Transaction reference object to the last reserve record, return null if book is null or the book is not reserved
     */
    public Transaction getBookLastReservedTransaction(Book bk) {

        if (bk == null) {
            return null;
        }
        if (!bk.isReserved()) return null;

        for (int a = this.transactionCount - 1; a != -1; a--) {
            if (transactionList[a].getType() == TransactionType.RESERVE && transactionList[a].getBookInvolved().equals(bk)) {
                return transactionList[a];
            }
        }
        return null;
    }

    /**
     * This function return an array list of books currently reserved by this member
     *
     * @param mem member object
     * @return an arraylist of book reference from BookManager
     */
    public ArrayList<Book> getMemberActiveReservations(Member mem) {
        ArrayList<Book> books = new ArrayList<Book>();

        String sql = "SELECT * FROM book WHERE isReserved=1 AND lastReservedBy=" + mem.getId();
        ResultSet rs = db.resultQuery(sql);
        try {
            while (rs.next()) {
                books.add(bm.getById(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * This function returns an array list of transactions for specific member id
     *
     * @param memID member id
     * @return an array list of reference to transactions
     */
    public ArrayList<Transaction> getTransactionsByMember(Member memID) {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        for (int a = 0; a < this.transactionCount; a++) {
            if (transactionList[a].getMemberInvolved() == memID) {
                // if (!CustomUtil.stringToDate(transactionList[a].getDateCreated()).before(startDate) && !CustomUtil.stringToDate(transactionList[a].getDateCreated()).after(endDate))
                trans.add(transactionList[a]);
            }
        }
        return trans;
    }

    /**
     * This function have the same behavior as getTransactionsByMemberID(int) except it accepts startDate and endDate filter
     *
     * @param mem       member object
     * @param startDate date filter to filter out record before this date
     * @param endDate   date filter to filter out record after this date
     * @return an array list of reference to transactions
     * @see #getTransactionsByMember(Member)
     */
    public ArrayList<Transaction> getTransactionsByMember(Member mem, Date startDate, Date endDate) {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        for (int a = 0; a < this.transactionCount; a++) {
            if (transactionList[a].getMemberInvolved().equals(mem)) {
                if (!CustomUtil.stringToDate(transactionList[a].getDateCreated()).before(startDate) && !CustomUtil.stringToDate(transactionList[a].getDateCreated()).after(endDate))
                    trans.add(transactionList[a]);
            }
        }
        return trans;
    }

    /**
     * This function returns an array list of transactions for specific staff ID
     *
     * @param staff     staff Object
     * @param startDate add filter to the records by specifying start date
     * @param endDate   add filter to the records by specifying end date
     * @return an array list of transactions
     */
    public ArrayList<Transaction> getTransactionByStaff(Staff staff, Date startDate, Date endDate) {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        for (int a = 0; a < this.transactionCount; a++) {
            if (transactionList[a].getStaffHandled().equals(staff)) {
                if (!CustomUtil.stringToDate(transactionList[a].getDateCreated()).before(startDate) && !CustomUtil.stringToDate(transactionList[a].getDateCreated()).after(endDate))
                    trans.add(transactionList[a]);
            }
        }
        return trans;
    }

    /**
     * This function returns an array list of transactions between specified date range
     *
     * @param startDate add filter to the records by specifying start date
     * @param endDate   add filter to the records by specifying end date
     * @return an arraylist of transaction
     */
    public ArrayList<Transaction> getTransactionByDate(Date startDate, Date endDate) {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        for (int a = 0; a < this.transactionCount; a++) {
            if (!CustomUtil.stringToDate(transactionList[a].getDateCreated()).before(startDate) && !CustomUtil.stringToDate(transactionList[a].getDateCreated()).after(endDate))
                trans.add(transactionList[a]);

        }
        return trans;
    }

    /**
     * @return transaction count loaded from database
     */
    public int getTransactionCount() {
        return this.transactionCount;
    }

    // ================================================================================================================
    // Unimplemented Method
    // ================================================================================================================

    /**
     * Not implemented<br>
     * Reason: Transaction do not use the name field
     *
     * @param name Not implemented
     * @return null
     */
    @Override
    public Transaction getByName(String name) {
        return null;
    }
}
