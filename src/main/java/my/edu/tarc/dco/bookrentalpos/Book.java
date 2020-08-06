package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store Book data for POS system
 *
 * @author Looz
 * @version 1.0
 */
public class Book extends Entity {

    private boolean rented;
    private boolean reserved;
    private double rentalPrice;
    private int lastRentedBy;
    private int lastReservedBy;
    private String author;

    /**
     * Constructor used to create a new Book entry
     *
     * @param name        Book Title
     * @param author      Book Author
     * @param rentalPrice Book's rental price per day
     */
    public Book(String name, String author, double rentalPrice) {
        setName(name);
        setAuthor(author);
        setRentalPrice(rentalPrice);
        this.rented = false;
        this.reserved = false;
    }

    /**
     * Constructor used to import Book data from Database
     *
     * @param id             Book ID
     * @param date           Date the book was added into database
     * @param lastRentedBy   MemberID of whoever last rented the book
     * @param lastReservedBy MemberID of whoever last reserved the book
     * @param isRented       Although int is used in database, it is stored as boolean
     *                       in the system
     * @param isReserved     Although int is used in database, it is stored as
     *                       boolean in the system
     * @see #Book(String, String, double)
     */
    public Book(int id, String date, String name, String author, double rentalPrice, int lastRentedBy, int lastReservedBy, boolean isRented, boolean isReserved) {
        setID(id);
        setDateCreated(date);
        setName(name);
        setAuthor(author);
        setRentalPrice(rentalPrice);
        setLastRentedBy(lastRentedBy);
        setLastReservedBy(lastReservedBy);
        this.rented = false;
        this.reserved = false;
    }

    /**
     * @return MemberID of whoever last rented this book. Can be 0 if the member
     * was previously removed
     */
    public int getLastRentedBy() {
        return this.lastRentedBy;
    }

    /**
     * @param lastRentedBy MemberID of the person rented this book
     */
    public void setLastRentedBy(int lastRentedBy) {
        this.lastRentedBy = lastRentedBy;
    }

    /**
     * @return Member ID of whoever reserved this book
     */
    public int getLastReservedBy() {
        return this.lastReservedBy;
    }

    /**
     * @param lastReservedBy MemberID of the person who reserved this book
     */
    public void setLastReservedBy(int lastReservedBy) {
        this.lastReservedBy = lastReservedBy;
    }

    /**
     * @return Author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author Author of the book in String
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param rentState boolean to show if the book is currently rented
     */
    public void setRented(boolean rentState) {
        this.rented = rentState;
    }

    /**
     * Check whether the book is currently rented
     *
     * @return True if book is currently rented
     */
    public Boolean isRented() {
        return rented;
    }

    /**
     * @param reserveState boolean to show if the book is currently reserved
     */
    public void setReserved(boolean reserveState) {
        this.reserved = reserveState;
    }

    /**
     * Check whether the book is currently reserved. NOTE: Reserved book cant be
     * rented by the other member other than the one rented it
     *
     * @return True if book is currently reserved
     */
    public Boolean isReserved() {
        return reserved;
    }

    /**
     * @param rentalPrice Rental price of the book (per day)
     */
    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    /**
     * @return rental Price of the book (per day)
     */
    public double getRentalPrice() {
        return rentalPrice;
    }

}
